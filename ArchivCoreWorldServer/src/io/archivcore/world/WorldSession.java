/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.world;

import io.archivcore.networking.client.TCPConnection;
import io.archivcore.networking.client.TCPConnectionDelegate;
import io.archivcore.networking.packet.Packet;
import io.archivcore.world.packet.WorldSessionPacketIO;
import io.archivcore.world.packet.WorldPacket;
import io.archivcore.world.protocol.WorldOpcode;
import io.archivcore.world.protocol.WorldOpcodeTable;
import java.security.SecureRandom;
import io.archivcore.player.Player;

/**
 *
 * @author Dusko
 */
public class WorldSession implements TCPConnectionDelegate {

    private final TCPConnection connection;
    public final byte[] authSeed = generateSeed();

    private final WorldOpcodeTable opcodeTable = new WorldOpcodeTable();

    public final Player player = new Player();

    public WorldSession(TCPConnection connection) {
        this.connection = connection;
        this.connection.packetIO = new WorldSessionPacketIO(connection);

        verifyConnectivity();
    }

    public final void start() {
        connection.setDelegate(this);
        connection.start();
    }

    public final void stop() {
        connection.setDelegate(null);
        connection.stop();
    }

    public final void send(WorldPacket packet) {
        System.out.println(">> Sending " + packet.worldOpcode.name());
        connection.sendPacket(packet);
    }

    public final void initCrypt(byte[] sessionKey) {
        WorldSessionPacketIO packetIO = (WorldSessionPacketIO)connection.packetIO;
        packetIO.initCrypt(sessionKey);
    }

    @Override public void didReceivePacket(TCPConnection connection, Packet packet) {
        WorldOpcode opcode = WorldOpcode.get(packet.rawOpcode);
        if (opcode == null) {
            System.out.println(">> Received unknown opcode: " + Integer.toHexString(packet.rawOpcode).toUpperCase());
            return;
        }

        opcodeTable.handle(opcode, this, packet);
    }

    private void verifyConnectivity() {
        WorldPacket packet = new WorldPacket(WorldOpcode.MsgVerifyConnectivity, 200);
        packet.body.putString("RLD OF WARCRAFT CONNECTION - SERVER TO CLIENT", true);
        send(packet);
    }

    private static byte[] generateSeed() {
        byte[] seed = new SecureRandom().generateSeed(4);

        for (int i = 0; i < seed.length; i++) {
            seed[i] = (byte)Math.abs(seed[i]);
        }

        return seed;
    }
}
