/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import wowemulator.networking.client.TCPConnection;
import wowemulator.networking.server.TCPServer;
import wowemulator.networking.server.TCPServerDelegate;

/**
 *
 * @author Dusko
 */
public class WorldServer implements TCPServerDelegate {

    private final TCPServer server;
    
    private final List<WorldSession> sessions = new LinkedList<>();
    
    public WorldServer(int port) {
        server = new TCPServer(port, this);
    }
    
    public final void start() {
        server.start();
    }
    
    @Override public void didAcceptConnection(TCPConnection connection) {
        WorldSession session = new WorldSession(connection);
        session.start();
        
        sessions.add(session);
    }

    public List<WorldSession> getSessions() {
        return Collections.unmodifiableList(sessions);
    }
}
