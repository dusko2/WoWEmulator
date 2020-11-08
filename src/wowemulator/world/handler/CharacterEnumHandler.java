/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.handler;

import wowemulator.networking.packet.Packet;
import wowemulator.world.protocol.WorldOpcode;
import wowemulator.world.packet.WorldPacket;
import wowemulator.world.WorldSession;

/**
 *
 * @author Dusko
 */
public class CharacterEnumHandler implements WorldOpcodeHandler {

    @Override
    public void handle(WorldSession session, Packet packet) {
        int characterCount = 0;
        // create(Opcodes.SMSG_CHAR_ENUM, 350 * characterCount + 10);
        
        WorldPacket response = new WorldPacket(WorldOpcode.SmsgCharEnum, 1);
        response.putByte((byte)characterCount);

        for (int atChar = 0; atChar < characterCount; atChar++) {
//            Char currentChar = client.getCharacters().get(atChar);
//            packet.order(ByteOrder.BIG_ENDIAN);
//            putLong(currentChar.getGUID()); // PlayerGuid;
//            packet.order(ByteOrder.LITTLE_ENDIAN);
//            putString(currentChar.getCharName()); // name
//            put((byte) currentChar.getCharRace()); // Race;
//            put((byte) currentChar.getCharClass()); // Class;
//            put((byte) 1); // Gender;
//            put((byte) 1); // Skin;
//            put((byte) 4); // Face;
//            put((byte) 5); // Hair Style;
//            put((byte) 0); // Hair Color;
//            put((byte) 0); // Facial Hair;
//            put((byte) 60); // Level;
//            putInt(0); // Zone ID;
//            putInt(currentChar.getMapID());
//            putFloat(currentChar.getPosition().getX()); // X
//            putFloat(currentChar.getPosition().getY()); // Y
//            putFloat(currentChar.getPosition().getZ()); // Z
//            // Guild ID
//            if (client.getVersion() != ClientVersion.VERSION_CATA && client.getVersion() != ClientVersion.VERSION_MOP)
//                    putInt(0);
//            else
//                putLong(0);
//            putInt(0); // Character Flags;
//            if (client.getVersion() == ClientVersion.VERSION_BC)
//                put((byte) 0); // Login Flags;
//            else {
//                putInt(0); // Login Flags;
//                put((byte) 0); // Is Customize Pending?;
//            }
//
//            putInt(0); // Pet DisplayID;
//            putInt(0); // Pet Level;
//            putInt(0); // Pet FamilyID;
//
//            int EQUIPMENT_SLOT_END = 19;
//            if (client.getVersion() == ClientVersion.VERSION_WOTLK)
//                EQUIPMENT_SLOT_END++;
//
//            for (int itemSlot = 0; itemSlot < EQUIPMENT_SLOT_END; ++itemSlot) {
//                putInt(0); // Item DisplayID;
//                put((byte) 0); // Item Inventory Type;
//                if (client.getVersion() != ClientVersion.VERSION_BC && client.getVersion() != ClientVersion.VERSION_VANILLA)
//                    putInt(1); // Item EnchantID;
//            }
//
//            if (client.getVersion() != ClientVersion.VERSION_BC && client.getVersion() != ClientVersion.VERSION_VANILLA) {
//                int bagCount = 3;
//                if (client.getVersion() != ClientVersion.VERSION_CATA)
//                    bagCount++;
//                for (int c = 0; c < bagCount; c++) { // In 3.3.3 they added 3x new uint32 uint8 uint32
//                    putInt(0); // bag;
//                    put((byte) 18); // slot;
//                    putInt(1); // enchant?;
//                }
//            } else {
//                putInt(0); // first bag display id
//                put((byte) 0); // first bag inventory type
//            }
//
//            if (client.getVersion() == ClientVersion.VERSION_BC)
//                putInt(0); // enchant?
        }

        session.send(response);
    }
}
