/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.logon;

import wowemulator.logon.auth.AuthHandler;
import wowemulator.client.Client;
import wowemulator.logon.auth.AuthPacketIO;
import wowemulator.networking.client.TCPConnectionDelegate;
import wowemulator.networking.client.TCPConnection;
import wowemulator.networking.packet.Packet;

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

    @Override
    public void didReceivePacket(TCPConnection client, Packet packet) {
        authHandler.handlePacket(this, packet);
    }
}
