/*
 * LogDisconnectHandler.java
 * WoWEmulator
 *
 * Created on May 8, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package wowemulator.world.handler;

import wowemulator.networking.packet.Packet;
import wowemulator.world.WorldSession;
import wowemulator.world.protocol.WorldOpcodeHandler;

/**
 *
 * @author Dusko Mirkovic
 */
public class LogDisconnectHandler implements WorldOpcodeHandler {

    @Override public void handle(WorldSession session, Packet packet) {
        int disconnectReason = packet.body.getInt();
        System.out.println(">> Disconnect reason " + disconnectReason);

        session.stop();
    }
}
