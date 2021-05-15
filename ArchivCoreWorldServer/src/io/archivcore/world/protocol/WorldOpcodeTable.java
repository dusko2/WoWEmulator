/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.world.protocol;

import io.archivcore.networking.packet.Packet;
import java.util.HashMap;
import java.util.Map;
import io.archivcore.world.WorldSession;
import io.archivcore.world.handler.AuthSessionHandler;
import io.archivcore.world.handler.CharacterCreateHandler;
import io.archivcore.world.handler.EnumCharactersHandler;
import io.archivcore.world.handler.LogDisconnectHandler;
import io.archivcore.world.handler.PingHandler;
import io.archivcore.world.handler.RandomizeCharacterNameHandler;
import io.archivcore.world.handler.ReadyForAccountDataTimesHandler;
import io.archivcore.world.handler.UpdateAccountDataHandler;
import io.archivcore.world.handler.VerifyConnectivityHandler;

/**
 *
 * @author Dusko
 */
public class WorldOpcodeTable {

    private final Map<WorldOpcode, WorldOpcodeHandler> opcodeTable = new HashMap<>();

    public WorldOpcodeTable() {
        opcodeTable.put(WorldOpcode.MsgVerifyConnectivity, new VerifyConnectivityHandler());

        opcodeTable.put(WorldOpcode.CmsgLogDisconnect,            new LogDisconnectHandler());
        opcodeTable.put(WorldOpcode.CmsgAuthSession,              new AuthSessionHandler());
        opcodeTable.put(WorldOpcode.CmsgReadyForAccountDataTimes, new ReadyForAccountDataTimesHandler());
        opcodeTable.put(WorldOpcode.CmsgEnumCharacters,           new EnumCharactersHandler());
        opcodeTable.put(WorldOpcode.CmsgPing,                     new PingHandler());
        opcodeTable.put(WorldOpcode.CmsgUpdateAccountData,        new UpdateAccountDataHandler());
        opcodeTable.put(WorldOpcode.CmsgRandomizeCharacterName,   new RandomizeCharacterNameHandler());
        opcodeTable.put(WorldOpcode.CmsgCharacterCreate,          new CharacterCreateHandler());
    }

    public final void handle(WorldOpcode opcode, WorldSession session, Packet packet) {
        if (opcode.status == WorldOpcodeStatus.Inactive) {
            System.out.println(">> Tried to handle inactive opcode: " + opcode.name());
            return;
        }

        WorldOpcodeHandler handler = opcodeTable.get(opcode);
        if (handler == null) {
            System.out.println(">> Received unhandled opcode: " + opcode.name());
            return;
        }

        System.out.println(">> Handling -> [" + opcode.name() + "]");
        handler.handle(session, packet);
    }
}
