/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.networking.server;

import io.archivcore.networking.client.TCPConnection;

/**
 *
 * @author Dusko
 */
public interface TCPServerDelegate {

    public void didAcceptConnection(TCPConnection connection);
}
