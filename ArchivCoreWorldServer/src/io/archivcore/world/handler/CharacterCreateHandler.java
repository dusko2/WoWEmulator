/*
 * CharacterCreateHandler.java
 * WoWEmulator
 *
 * Created on May 11, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package io.archivcore.world.handler;

import io.archivcore.networking.packet.Packet;
import io.archivcore.player.character.CharacterCreateInfo;
import io.archivcore.player.character.PlayerCharacter;
import io.archivcore.world.WorldSession;
import io.archivcore.world.packet.WorldPacket;
import io.archivcore.world.protocol.WorldOpcode;
import io.archivcore.world.protocol.WorldOpcodeHandler;

/**
 *
 * @author Dusko Mirkovic
 */
public class CharacterCreateHandler implements WorldOpcodeHandler {

    private enum CharacterCreateResult {

        InProgress(46),
        Success   (47),
        Error     (48),
        Failed    (49),
        Disabled  (51);

        public final byte rawValue;

        private CharacterCreateResult(int rawValue) {
            this.rawValue = (byte)rawValue;
        }
    }

    @Override public void handle(WorldSession session, Packet packet) {
        CharacterCreateInfo characterCreateInfo = new CharacterCreateInfo(packet);

        // TODO: Check the data sent by the client

        PlayerCharacter character = PlayerCharacter.createNewCharacter(characterCreateInfo);
        session.player.addNewCharacter(character);

        sendCharacterCreateResponse(session, CharacterCreateResult.Success);
    }

    private void sendCharacterCreateResponse(WorldSession session, CharacterCreateResult result) {
        WorldPacket packet = new WorldPacket(WorldOpcode.SmsgCharacterCreate, 1);
        packet.body.putByte(result.rawValue);
        session.send(packet);
    }
}
