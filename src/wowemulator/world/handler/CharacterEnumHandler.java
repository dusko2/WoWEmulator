/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.handler;

import java.util.List;
import wowemulator.world.protocol.WorldOpcodeHandler;
import wowemulator.player.character.PlayerCharacter;
import wowemulator.world.protocol.WorldOpcode;
import wowemulator.world.packet.WorldPacket;
import wowemulator.world.WorldSession;
import wowlib.networking.packet.Packet;

/**
 *
 * @author Dusko
 */
public class CharacterEnumHandler implements WorldOpcodeHandler {

    @Override
    public void handle(WorldSession session, Packet packet) {
        List<PlayerCharacter> characters = session.player.getCharacters();
        int size = characters.size() * 269 + characters.stream().mapToInt(character -> character.name.length() + 1).sum();
        
        WorldPacket response = new WorldPacket(WorldOpcode.SmsgCharEnum, 1 + size);
        response.putByte((byte)characters.size());
        
        for (PlayerCharacter character : characters) {
            response.putLong(character.guid);
            response.putString(character.name);
            response.putByte(character.race);
            response.putByte(character.cClass);
            response.putByte(character.gender);
            response.putByte(character.skinColor);
            response.putByte(character.faceStyle);
            response.putByte(character.hairStyle);
            response.putByte(character.hairColor);
            response.putByte(character.facialHair);
            response.putByte(character.getLevel());
            
            response.putInt(character.getZoneID()); // Zone ID
            response.putInt(character.getMapID()); // Map ID
            response.putFloat(character.position.x); // x
            response.putFloat(character.position.y); // y
            response.putFloat(character.position.z); // z
            
            response.putInt(character.getGuildID()); // Guild ID
            
            response.putInt(0); // Character flags
            response.putInt(0); // Customize flags
            response.putByte((byte)0); // First login?, move to database
            
            // Only for hunters and warlocks
            response.putInt(0); // Pet displayID
            response.putInt(0); // Pet level
            response.putInt(0); // Pet familiyID
            
            int equipmentSlotEnd = 23;
            for (int equipmentSlot = 0; equipmentSlot < equipmentSlotEnd; equipmentSlot++) {
                response.putInt(0); // Item displayID
                response.putByte((byte)0); // Item inventory type
                response.putInt(1); // Item enchant ID
            }
        }
        
        session.send(response);
    }
}
