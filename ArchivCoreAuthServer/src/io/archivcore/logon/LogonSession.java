/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.logon;

import io.archivcore.logon.auth.AuthHandler;
import io.archivcore.client.Client;
import io.archivcore.logon.auth.AuthPacketIO;
import io.archivcore.networking.client.TCPConnection;
import io.archivcore.networking.client.TCPConnectionDelegate;
import io.archivcore.networking.packet.Packet;

/**
 *
 * @author Dusko
 */
public class LogonSession implements TCPConnectionDelegate {

    protected final TCPConnection connection;
    protected final AuthHandler authHandler = new AuthHandler();

    public Client client;

    public LogonSession(TCPConnection connection) {
        this.connection = connection;
    }

    public final void start() {
        connection.packetIO = new AuthPacketIO(connection);
        connection.setDelegate(this);
        connection.start();
    }

    public final void send(Packet packet) {
        connection.sendPacket(packet);
    }

    @Override public void didReceivePacket(TCPConnection client, Packet packet) {
        authHandler.handlePacket(this, packet);
    }
}
