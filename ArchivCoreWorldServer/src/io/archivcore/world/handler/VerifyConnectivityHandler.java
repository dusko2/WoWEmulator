/*
 * VerifyConnectivityHandler.java
 * WoWEmulator
 *
 * Created on May 8, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package io.archivcore.world.handler;

import io.archivcore.networking.packet.Packet;
import io.archivcore.world.WorldSession;
import io.archivcore.world.packet.AuthChallengePacket;
import io.archivcore.world.protocol.WorldOpcodeHandler;

/**
 *
 * @author Dusko Mirkovic
 */
public class VerifyConnectivityHandler implements WorldOpcodeHandler {

    @Override public void handle(WorldSession session, Packet packet) {
        String expectedMessage = "D OF WARCRAFT CONNECTION - CLIENT TO SERVER";
        String received = packet.body.getString();

        if (expectedMessage.equals(received)) {
            AuthChallengePacket responsePacket = new AuthChallengePacket(session.authSeed);
            session.send(responsePacket);
        } else {
            // TODO: close session
        }
    }
}
