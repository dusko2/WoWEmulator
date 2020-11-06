/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.networking.client;

import java.net.Socket;
import wowemulator.networking.packet.Packet;

/**
 *
 * @author Dusko
 */
public class TCPConnection implements Runnable {

    protected final Socket socket;
    public PacketIO packetIO;
    
    private TCPConnectionDelegate delegate;
    private boolean running = false;
    
    public TCPConnection(Socket socket) {
        this.socket = socket;
    }
    
    public final void start() {
        if (running) {
            throw new RuntimeException("TCPClient is already started");
        }
        
        running = true;
        
        new Thread(this).start();
    }
    
    public final void stop() {
        if (!running) {
            throw new RuntimeException("TCPClient is already stopped");
        }
        
        running = false;
    }

    @Override public void run() {
        while (running) {
            Packet packet = packetIO.readPacket();
            if (packet == null) {
                continue;
            }
            
            if (delegate != null) {
                delegate.didReceivePacket(this, packet);
            }
        }
    }
    
    public void sendPacket(Packet packet) {
        packetIO.sendPacket(packet);
    }

    public void setDelegate(TCPConnectionDelegate delegate) {
        this.delegate = delegate;
    }
}
