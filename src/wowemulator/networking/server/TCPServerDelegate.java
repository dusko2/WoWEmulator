/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.networking.server;

import wowemulator.networking.client.TCPConnection;

/**
 *
 * @author Dusko
 */
public interface TCPServerDelegate {

    public void didAcceptConnection(TCPConnection connection);
}
