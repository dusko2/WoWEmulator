/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.networking.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Dusko
 */
public class ServerSocketBuilder {

    private int port = -1;
    
    private ServerSocketBuilder() { }
    
    public final ServerSocketBuilder withPort(int port) {
        this.port = port;
        
        return this;
    }
    
    public final ServerSocket build() {
        if (port == -1) {
            throw new RuntimeException("Port value must be set");
        }
        
        try {
            return new ServerSocket(port);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static ServerSocketBuilder get() {
        return new ServerSocketBuilder();
    }
}
