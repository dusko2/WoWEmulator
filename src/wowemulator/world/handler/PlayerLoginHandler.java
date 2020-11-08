/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.handler;

import wowemulator.player.character.PlayerCharacter;
import wowemulator.world.WorldSession;
import wowemulator.world.packet.WorldPacket;
import wowemulator.world.protocol.WorldOpcode;
import wowemulator.world.protocol.WorldOpcodeHandler;
import wowlib.networking.packet.Packet;
import wowlib.utils.Vec4;

/**
 *
 * @author Dusko
 */
public class PlayerLoginHandler implements WorldOpcodeHandler {

    @Override
    public void handle(WorldSession session, Packet packet) {
        long guid = packet.getLong();
        
        PlayerCharacter character = session.player.find(guid);
        if (character == null) {
            // TODO: Handle this properly
            return;
        }
        
        sendLoginVerifyWorldPacket(session, character);
    }
    
    private void sendLoginVerifyWorldPacket(WorldSession session, PlayerCharacter character) {
        WorldPacket packet = new WorldPacket(WorldOpcode.SmsgLoginVerifyWorld, 4 + Vec4.BYTES);
        packet.putInt(character.getMapID());
        packet.putPosition(character.position);
        session.send(packet);
    }
}
