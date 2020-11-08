/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.handler;

import wowemulator.world.protocol.WorldOpcodeHandler;
import wowemulator.world.WorldSession;
import wowlib.networking.packet.Packet;

/**
 *
 * @author Dusko
 */
public class ReadyForAccountDataTimesHandler implements WorldOpcodeHandler {

    @Override public void handle(WorldSession session, Packet packet) {
        
    }
}
