/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.networking.client;

import java.io.InputStream;
import java.io.OutputStream;
import io.archivcore.networking.packet.Packet;
import io.archivcore.utils.SocketUtils;

/**
 *
 * @author Dusko
 */
public abstract class PacketIO {

    protected final InputStream inputStream;
    protected final OutputStream outputStream;

    public PacketIO(TCPConnection connection) {
        this.inputStream = SocketUtils.inputStream(connection.socket);
        this.outputStream = SocketUtils.outputStream(connection.socket);
    }
    
    public abstract Packet readPacket();
    public abstract void sendPacket(Packet packet);
}
