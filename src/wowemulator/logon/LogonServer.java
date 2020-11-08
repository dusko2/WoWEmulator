/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.logon;

import java.util.LinkedList;
import java.util.List;
import wowemulator.client.Client;
import wowlib.networking.client.TCPConnection;
import wowlib.networking.server.TCPServer;
import wowlib.networking.server.TCPServerDelegate;

/**
 *
 * @author Dusko
 */
public class LogonServer implements TCPServerDelegate {

    private final TCPServer server = new TCPServer(3724, this);
    
    private final List<LogonSession> sessions = new LinkedList<>();

    private LogonServer() {
        
    }
    
    public final void start() {
        server.start();
    }
    
    @Override public void didAcceptConnection(TCPConnection connection) {
        LogonSession session = new LogonSession(connection);
        session.start();
        
        sessions.add(session);
    }
    
    public final Client getClient(String username) {
        for (LogonSession session : sessions) {
            if (session.client.username.equals(username)) {
                return session.client;
            }
        }
        
        return null;
    }
    
    private static class LogonServerInstance {
        
        public static final LogonServer instance = new LogonServer();
    }
    
    public static LogonServer getInstance() {
        return LogonServerInstance.instance;
    }
}
