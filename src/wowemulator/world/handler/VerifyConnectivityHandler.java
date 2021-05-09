/*
 * VerifyConnectivityHandler.java
 * WoWEmulator
 *
 * Created on May 8, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package wowemulator.world.handler;

import wowemulator.networking.packet.Packet;
import wowemulator.world.WorldSession;
import wowemulator.world.packet.AuthChallengePacket;
import wowemulator.world.protocol.WorldOpcodeHandler;

/**
 *
 * @author Dusko Mirkovic
 */
public class VerifyConnectivityHandler implements WorldOpcodeHandler {

    @Override public void handle(WorldSession session, Packet packet) {
        String expectedMessage = "D OF WARCRAFT CONNECTION - CLIENT TO SERVER";
        String received = packet.getString();

        if (expectedMessage.equals(received)) {
            AuthChallengePacket responsePacket = new AuthChallengePacket(session.authSeed);
            session.send(responsePacket);
        } else {
            // TODO: close session
        }
    }
}
