/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world;

import java.nio.ByteOrder;
import java.security.SecureRandom;
import wowemulator.networking.client.TCPConnection;
import wowemulator.networking.client.TCPConnectionDelegate;
import wowemulator.networking.packet.Packet;
import wowemulator.utils.BigNumber;

/**
 *
 * @author Dusko
 */
public class WorldSession implements TCPConnectionDelegate {

    private final TCPConnection connection;
    private byte[] authSeed;
    
    private final WorldOpcodeTable opcodeTable = new WorldOpcodeTable();

    public WorldSession(TCPConnection connection) {
        this.connection = connection;
        
        connection.packetIO = new WorldSessionPacketIO(connection);
        sendAuthChallenge();
    }
    
    public final void start() {
        connection.setDelegate(this);
        connection.start();
    }
    
    public final void send(Packet packet) {
        connection.sendPacket(packet);
    }
    
    public final void initCrypt(byte[] sessionKey) {
        WorldSessionPacketIO packetIO = (WorldSessionPacketIO)connection.packetIO;
        packetIO.initCrypt(sessionKey);
    }
    
    @Override
    public void didReceivePacket(TCPConnection connection, Packet packet) {
        WorldOpcode opcode = WorldOpcode.get(packet.rawOpcode);
        if (opcode == null) {
            System.out.println("Received unknown opcode: " + packet.rawOpcode);
            return;
        }
        
        opcodeTable.handle(opcode, this, packet);
    }
    
    public final void sendAuthChallenge() {
        WorldPacket packet = new WorldPacket(WorldOpcode.SmsgAuthChallenge, 50);
        packet.body.order(ByteOrder.LITTLE_ENDIAN);
        authSeed = new SecureRandom().generateSeed(4);
        
        packet.putInt(1);
        packet.putBytes(authSeed);
        packet.putBytes(new BigNumber().setRand(32).asByteArray(32));
        connection.sendPacket(packet.wrap());
    }

    public byte[] getAuthSeed() {
        return authSeed;
    }
}
