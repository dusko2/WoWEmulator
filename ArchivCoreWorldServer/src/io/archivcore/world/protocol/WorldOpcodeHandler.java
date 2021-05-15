/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.world.protocol;

import io.archivcore.networking.packet.Packet;
import io.archivcore.world.WorldSession;

/**
 *
 * @author Dusko
 */
public interface WorldOpcodeHandler {

    public void handle(WorldSession session, Packet packet);
}
