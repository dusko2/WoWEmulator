/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.world.handler;

import io.archivcore.networking.packet.Packet;
import java.util.Collection;
import io.archivcore.world.protocol.WorldOpcodeHandler;
import io.archivcore.player.character.PlayerCharacter;
import io.archivcore.world.WorldSession;
import io.archivcore.world.packet.WorldPacket;
import io.archivcore.world.protocol.WorldOpcode;

/**
 *
 * @author Dusko
 */
public class EnumCharactersHandler implements WorldOpcodeHandler {

    @Override public void handle(WorldSession session, Packet packet) {
        WorldPacket response = new WorldPacket(WorldOpcode.SmsgEnumCharactersResult, 4096);

        Collection<PlayerCharacter> characters = session.player.getAllCharacters();
        if (characters.isEmpty()) {
            response.body.putBit(0, 21);
            response.body.putBit(characters.size(), 16);
            response.body.putBit(1);
            response.body.flush();
            session.send((WorldPacket)response.wrap());

            return;
        }

        response.body.putBit(0, 21);
        response.body.putBit(characters.size(), 16);

        for (PlayerCharacter character : characters) {
            response.body.putBit(character.guildGuid.bytes[4]);
            response.body.putBit(character.objectGuid.bytes[0]);
            response.body.putBit(character.guildGuid.bytes[3]);
            response.body.putBit(character.objectGuid.bytes[3]);
            response.body.putBit(character.objectGuid.bytes[7]);
            response.body.putBit(false); // boost
            response.body.putBit(0); // First login
            response.body.putBit(character.objectGuid.bytes[6]);
            response.body.putBit(character.guildGuid.bytes[6]);
            response.body.putBit(character.info.name.length(), 6);
            response.body.putBit(character.objectGuid.bytes[1]);
            response.body.putBit(character.guildGuid.bytes[1]);
            response.body.putBit(character.guildGuid.bytes[0]);
            response.body.putBit(character.objectGuid.bytes[4]);
            response.body.putBit(character.guildGuid.bytes[7]);
            response.body.putBit(character.objectGuid.bytes[2]);
            response.body.putBit(character.objectGuid.bytes[5]);
            response.body.putBit(character.guildGuid.bytes[2]);
            response.body.putBit(character.guildGuid.bytes[5]);
        }

        response.body.putBit(1);
        response.body.flush();

        int slot = 1;
        for (PlayerCharacter character : characters) {
            response.body.putInt(0);

            response.body.putByteSeq(character.objectGuid.bytes[1]);

            response.body.putByte((byte)slot);
            response.body.putByte(character.info.hairStyle);

            response.body.putByteSeq(character.guildGuid.bytes[2]);
            response.body.putByteSeq(character.guildGuid.bytes[0]);
            response.body.putByteSeq(character.guildGuid.bytes[6]);

            response.body.putString(character.info.name, false);

            response.body.putByteSeq(character.guildGuid.bytes[3]);

            response.body.putFloat(character.position.x);
            response.body.putInt(0);
            response.body.putByte(character.info.face);
            response.body.putByte(character.info.clazz);

            response.body.putByteSeq(character.guildGuid.bytes[3]);

            for (int i = 0; i < 23; i++) {
                response.body.putByte((byte)0);
                response.body.putInt(0);
                response.body.putInt(0);
            }

            int customizationFlags = 0;
            response.body.putInt(customizationFlags);

            response.body.putByteSeq(character.objectGuid.bytes[3]);
            response.body.putByteSeq(character.objectGuid.bytes[5]);

            response.body.putInt(0); // pet family

            response.body.putByteSeq(character.guildGuid.bytes[4]);

            response.body.putInt(0); // map ID
            response.body.putByte(character.info.race);
            response.body.putByte(character.info.skin);

            response.body.putByteSeq(character.guildGuid.bytes[1]);

            response.body.putByte((byte)1); // level

            response.body.putByteSeq(character.objectGuid.bytes[0]);
            response.body.putByteSeq(character.objectGuid.bytes[2]);

            response.body.putByte(character.info.hairColor);
            response.body.putByte(character.info.gender);
            response.body.putByte(character.info.facialHair);

            response.body.putInt(0); // pet level

            response.body.putByteSeq(character.objectGuid.bytes[4]);
            response.body.putByteSeq(character.objectGuid.bytes[7]);

            response.body.putFloat(character.position.y);
            response.body.putInt(0); // pet display ID
            response.body.putInt(0);

            response.body.putByteSeq(character.objectGuid.bytes[6]);

            response.body.putInt(0); // character flags
            response.body.putInt(0); // zone ID

            response.body.putByteSeq(character.guildGuid.bytes[7]);

            response.body.putFloat(character.position.z);

            slot++;
        }

        session.send((WorldPacket)response.wrap());
    }
}
