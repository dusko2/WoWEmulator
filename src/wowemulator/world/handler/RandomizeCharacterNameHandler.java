/*
 * RandomizeCharacterNameHandler.java
 * WoWEmulator
 *
 * Created on May 11, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package wowemulator.world.handler;

import wowemulator.networking.packet.Packet;
import wowemulator.world.WorldSession;
import wowemulator.world.packet.WorldPacket;
import wowemulator.world.protocol.WorldOpcode;
import wowemulator.world.protocol.WorldOpcodeHandler;

/**
 *
 * @author Dusko Mirkovic
 */
public class RandomizeCharacterNameHandler implements WorldOpcodeHandler {

    @Override public void handle(WorldSession session, Packet packet) {
        byte gender = packet.body.getByte();
        byte race = packet.body.getByte();

        String name = getRandomName(gender, race);
        sendRandomName(session, name);
    }

    private String getRandomName(byte gender, byte race) {
        return "Dusko";
    }

    private void sendRandomName(WorldSession session, String name) {
        WorldPacket packet = new WorldPacket(WorldOpcode.SmsgRandomizeCharacterName, 1 + name.length());

        packet.body.putBit(0);
        packet.body.putBit(name.length(), 6);
        packet.body.flush();

        packet.body.putString(name, false);
        session.send(packet);
    }
}
