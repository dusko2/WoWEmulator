/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.handler;

import wowemulator.networking.packet.Packet;
import wowemulator.world.protocol.WorldOpcode;
import wowemulator.world.packet.WorldPacket;
import wowemulator.world.WorldSession;

/**
 *
 * @author Dusko
 */
public class PingHandler implements WorldOpcodeHandler {

    private int pingAmount = 0;

    @Override
    public void handle(WorldSession session, Packet packet) {
        pingAmount++;
        
        WorldPacket response = new WorldPacket(WorldOpcode.SmsgPong, 4);
        response.putByte((byte)pingAmount);
        session.send(response);
    }
}
