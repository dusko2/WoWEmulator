/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.logon;

import java.util.LinkedList;
import java.util.List;
import io.archivcore.client.Client;
import io.archivcore.networking.client.TCPConnection;
import io.archivcore.networking.server.TCPServer;
import io.archivcore.networking.server.TCPServerDelegate;

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
            if (session.client == null) {
                continue;
            }

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
