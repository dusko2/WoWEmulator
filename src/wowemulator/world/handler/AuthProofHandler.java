/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.handler;

import wowemulator.world.protocol.WorldOpcodeHandler;
import java.security.MessageDigest;
import wowemulator.client.Client;
import wowemulator.logon.LogonServer;
import wowemulator.world.protocol.WorldOpcode;
import wowemulator.world.packet.WorldPacket;
import wowemulator.world.WorldSession;
import wowlib.networking.packet.Packet;
import wowlib.utils.BigNumber;
import wowlib.utils.HashUtils;

/**
 *
 * @author Dusko
 */
public class AuthProofHandler implements WorldOpcodeHandler {

    @Override public void handle(WorldSession session, Packet packet) {
        AuthProof authProof = new AuthProof(packet);
        
        Client client = LogonServer.getInstance().getClient(authProof.accountName);
        if (client == null) {
            // TODO: Close session
            System.out.println("Client = null");
            return;
        }
        
        MessageDigest digest = HashUtils.getSHA1();
        digest.update(client.username.toUpperCase().getBytes());
        digest.update(new byte[] { 0, 0, 0, 0 });
        digest.update(authProof.clientSeed);
        digest.update(session.getAuthSeed());
        digest.update(client.getSessionKey());
        
        BigNumber authProofDigest = new BigNumber(authProof.digest);
        BigNumber result = new BigNumber(digest.digest());
        
        if (!authProofDigest.equals(result)) {
            // TODO: Close connection
            System.out.println("digest not equal");
            return;
        }
        
        session.initCrypt(client.getSessionKey());
        
        WorldPacket response = new WorldPacket(WorldOpcode.SmsgAuthResponse, 1 + 1 + 1 + 8);
        response.putByte((byte)0x0C);
        response.putByte((byte)0x30);
        response.putByte((byte)0x78);
        response.putLong(0x02);
        session.send(response);
    }
    
    private class AuthProof {
        
        public final String accountName;
        public final byte[] clientSeed = new byte[4];
        public final byte[] digest = new byte[20];
        
        public AuthProof(Packet packet) {
            packet.getInt(); // Client build
            packet.getInt(); // Unknown
            accountName = packet.getString();
            packet.getInt(); // Unknown
            packet.getBytes(clientSeed);
            packet.getLong();
            packet.getInt();
            packet.getInt();
            packet.getInt();
            packet.getBytes(digest);
        }
    }
}
