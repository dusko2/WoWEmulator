/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world;

import wowemulator.world.packet.WorldSessionPacketIO;
import wowemulator.world.packet.WorldPacket;
import wowemulator.world.protocol.WorldOpcode;
import wowemulator.world.protocol.WorldOpcodeTable;
import java.security.SecureRandom;
import wowemulator.player.Player;
import wowemulator.world.packet.AuthChallengePacket;
import wowlib.networking.client.TCPConnection;
import wowlib.networking.client.TCPConnectionDelegate;
import wowlib.networking.packet.Packet;

/**
 *
 * @author Dusko
 */
public class WorldSession implements TCPConnectionDelegate {

    private final TCPConnection connection;
    private final byte[] authSeed = new SecureRandom().generateSeed(4);
    
    private final WorldOpcodeTable opcodeTable = new WorldOpcodeTable();
    
    public final Player player = new Player();

    public WorldSession(TCPConnection connection) {
        this.connection = connection;
        this.connection.packetIO = new WorldSessionPacketIO(connection);
        
        sendAuthChallenge();
    }
    
    public final void start() {
        connection.setDelegate(this);
        connection.start();
    }
    
    public final void send(WorldPacket packet) {
        connection.sendPacket(packet);
    }
    
    public final void initCrypt(byte[] sessionKey) {
        WorldSessionPacketIO packetIO = (WorldSessionPacketIO)connection.packetIO;
        packetIO.initCrypt(sessionKey);
    }
    
    @Override public void didReceivePacket(TCPConnection connection, Packet packet) {
        WorldOpcode opcode = WorldOpcode.get(packet.rawOpcode);
        if (opcode == null) {
            System.out.println(">> Received unknown opcode: " + packet.rawOpcode);
            return;
        }
        
        opcodeTable.handle(opcode, this, packet);
    }
    
    private void sendAuthChallenge() {
        AuthChallengePacket packet = new AuthChallengePacket(authSeed);
        send(packet);
    }

    public byte[] getAuthSeed() {
        return authSeed;
    }
}
