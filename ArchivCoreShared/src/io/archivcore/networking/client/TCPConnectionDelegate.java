/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.networking.client;

import io.archivcore.networking.packet.Packet;

/**
 *
 * @author Dusko
 */
public interface TCPConnectionDelegate {

    public void didReceivePacket(TCPConnection connection, Packet packet);
}
