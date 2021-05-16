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
import io.archivcore.world.packet.WorldPacket;
import io.archivcore.world.protocol.WorldOpcode;
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
            sendAuthChallenge(session);
        } else {
            // TODO: close session
        }
    }

    private void sendAuthChallenge(WorldSession session) {
        WorldPacket packet = new WorldPacket(WorldOpcode.SmsgAuthChallenge, 39);
        packet.body.putShort((short)0);

        for (int i = 0; i < 8; i++) {
            packet.body.putInt(0);
        }

        packet.body.putByte((byte)1);
        packet.body.putBytes(session.authSeed);

        session.send(packet);
    }
}
