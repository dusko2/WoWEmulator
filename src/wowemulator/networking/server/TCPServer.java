/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.networking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import wowemulator.networking.client.TCPConnection;

/**
 *
 * @author Dusko
 */
public class TCPServer {

    private static class Constants {
        
        public static final int TIMEOUT = 5000;
    }
    
    private final ServerSocket serverSocket;
    
    private boolean isRunning = false;
    
    private final TCPServerDelegate delegate;
    
    public TCPServer(int port, TCPServerDelegate delegate) {
        this.serverSocket = ServerSocketBuilder.get().withPort(port).build();
        this.delegate = delegate;
        
        configure();
    }
    
    private void configure() {
        try {
            serverSocket.setSoTimeout(Constants.TIMEOUT);
        } catch (SocketException ex) {
            throw new RuntimeException("Couldn't set socket timeout", ex);
        }
    }
    
    public final void start() {
        if (isRunning) {
            throw new RuntimeException("start called more than once");
        }
        
        isRunning = true;
        new Thread(this::listen).start();
    }
    
    private void listen() {
        while (isRunning) {
            Socket socket = accept();
            if (socket == null) {
                continue;
            }
            
            TCPConnection connection = new TCPConnection(socket);
            delegate.didAcceptConnection(connection);
        }
    }
    
    private Socket accept() {
        try {
            return serverSocket.accept();
        } catch (IOException ex) {
            return null; // When accept times out exception is thrown, no need to handle it
        }
    }
}
