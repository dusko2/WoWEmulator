/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.protocol;

import java.util.HashMap;
import java.util.Map;
import wowemulator.networking.packet.Packet;
import wowemulator.world.WorldSession;
import wowemulator.world.handler.AuthSessionHandler;
import wowemulator.world.handler.EnumCharactersHandler;
import wowemulator.world.handler.LogDisconnectHandler;
import wowemulator.world.handler.PingHandler;
import wowemulator.world.handler.ReadyForAccountDataTimesHandler;
import wowemulator.world.handler.UpdateAccountDataHandler;
import wowemulator.world.handler.VerifyConnectivityHandler;

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
