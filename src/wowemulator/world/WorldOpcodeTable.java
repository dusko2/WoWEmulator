/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world;

import java.util.HashMap;
import java.util.Map;
import wowemulator.networking.packet.Packet;
import wowemulator.world.handler.AuthProofHandler;
import wowemulator.world.handler.CharacterEnumHandler;
import wowemulator.world.handler.RealmSplitHandler;
import wowemulator.world.handler.WorldOpcodeHandler;

/**
 *
 * @author Dusko
 */
public class WorldOpcodeTable {

    private final Map<WorldOpcode, WorldOpcodeHandler> opcodeTable = new HashMap<>();

    public WorldOpcodeTable() {
        opcodeTable.put(WorldOpcode.CmsgAuthProof,       new AuthProofHandler());
        opcodeTable.put(WorldOpcode.CmsgCharEnum,        new CharacterEnumHandler());
        opcodeTable.put(WorldOpcode.CmsgRealmSplit,      new RealmSplitHandler());
    }
    
    public final void handle(WorldOpcode opcode, WorldSession session, Packet packet) {
        System.out.println(opcode.name());
        opcodeTable.get(opcode).handle(session, packet);
    }
}
