/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.protocol;

import java.util.HashMap;
import java.util.Map;
import wowemulator.world.WorldSession;
import wowemulator.world.handler.AuthProofHandler;
import wowemulator.world.handler.CharacterCreateHandler;
import wowemulator.world.handler.CharacterEnumHandler;
import wowemulator.world.handler.PingHandler;
import wowemulator.world.handler.PlayerLoginHandler;
import wowemulator.world.handler.ReadyForAccountDataTimesHandler;
import wowemulator.world.handler.RealmSplitHandler;
import wowlib.networking.packet.Packet;

/**
 *
 * @author Dusko
 */
public class WorldOpcodeTable {

    private final Map<WorldOpcode, WorldOpcodeHandler> opcodeTable = new HashMap<>();

    public WorldOpcodeTable() {
        opcodeTable.put(WorldOpcode.CmsgAuthProof,                new AuthProofHandler());
        opcodeTable.put(WorldOpcode.CmsgCharEnum,                 new CharacterEnumHandler());
        opcodeTable.put(WorldOpcode.CmsgRealmSplit,               new RealmSplitHandler());
        opcodeTable.put(WorldOpcode.CmsgPing,                     new PingHandler());
        opcodeTable.put(WorldOpcode.CmsgReadyForAccountDataTimes, new ReadyForAccountDataTimesHandler());
        opcodeTable.put(WorldOpcode.CmsgCharCreate,               new CharacterCreateHandler());
        opcodeTable.put(WorldOpcode.CmsgPlayerLogin,              new PlayerLoginHandler());
    }
    
    public final void handle(WorldOpcode opcode, WorldSession session, Packet packet) {
        if (opcode.status == WorldOpcodeStatus.Inactive) {
            return;
        }
        
        System.out.println(">> Handling -> [" + opcode.name() + "]");
        opcodeTable.get(opcode).handle(session, packet);
    }
}
