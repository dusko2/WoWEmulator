/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.handler;

import wowemulator.logon.auth.AuthCodes;
import wowemulator.player.character.CharacterCreateInfo;
import wowemulator.player.character.PlayerCharacter;
import wowemulator.world.WorldSession;
import wowemulator.world.packet.WorldPacket;
import wowemulator.world.protocol.WorldOpcode;
import wowemulator.world.protocol.WorldOpcodeHandler;
import wowlib.networking.packet.Packet;

/**
 *
 * @author Dusko
 */
public class CharacterCreateHandler implements WorldOpcodeHandler {

    @Override
    public void handle(WorldSession session, Packet packet) {
        CharacterCreateInfo characterInfo = new CharacterCreateInfo(packet);
        
        PlayerCharacter character = new PlayerCharacter(10000, characterInfo);
        session.player.createdNewCharacter(character);
        
        WorldPacket pkt = new WorldPacket(WorldOpcode.SmsgCharCreate, 1);
        pkt.putByte(AuthCodes.CharacterCreateSuccess.rawValue);
        session.send(pkt);
    }
}
