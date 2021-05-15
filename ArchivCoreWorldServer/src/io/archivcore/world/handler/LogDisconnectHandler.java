/*
 * LogDisconnectHandler.java
 * WoWEmulator
 *
 * Created on May 8, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package io.archivcore.world.handler;

import io.archivcore.networking.packet.Packet;
import io.archivcore.world.WorldSession;
import io.archivcore.world.protocol.WorldOpcodeHandler;

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
