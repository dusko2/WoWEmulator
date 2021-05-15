/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.world.handler;

import io.archivcore.networking.packet.Packet;
import io.archivcore.world.protocol.WorldOpcodeHandler;
import io.archivcore.world.protocol.WorldOpcode;
import io.archivcore.world.packet.WorldPacket;
import io.archivcore.world.WorldSession;

/**
 *
 * @author Dusko
 */
public class PingHandler implements WorldOpcodeHandler {

    @Override public void handle(WorldSession session, Packet packet) {
        int ping = packet.body.getInt();
        int latency = packet.body.getInt();

        WorldPacket response = new WorldPacket(WorldOpcode.SmsgPong, 4);
        response.body.putInt(ping);
        session.send(response);
    }
}
