/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.protocol;

import wowemulator.world.WorldSession;
import wowlib.networking.packet.Packet;

/**
 *
 * @author Dusko
 */
public interface WorldOpcodeHandler {

    public void handle(WorldSession session, Packet packet);
}
