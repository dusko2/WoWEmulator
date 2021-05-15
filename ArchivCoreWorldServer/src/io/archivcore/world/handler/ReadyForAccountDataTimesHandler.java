/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.world.handler;

import io.archivcore.networking.packet.Packet;
import io.archivcore.world.protocol.WorldOpcodeHandler;
import io.archivcore.player.AccountDataType;
import io.archivcore.world.WorldSession;
import io.archivcore.world.packet.WorldPacket;
import io.archivcore.world.protocol.WorldOpcode;

/**
 *
 * @author Dusko
 */
public class ReadyForAccountDataTimesHandler implements WorldOpcodeHandler {

    @Override public void handle(WorldSession session, Packet packet) {
        WorldPacket response = new WorldPacket(WorldOpcode.SmsgAccountDataTimes, 4 + 1 + 4 + (8 * 4));

        response.body.putBit(1);
        response.body.flush();

        for (AccountDataType dataType : AccountDataType.values()) {
            response.body.putInt((int)session.player.getAccountData(dataType).timestamp);
        }

        response.body.putInt(AccountDataType.globalCacheMask);
        response.body.putInt((int)System.currentTimeMillis());

        session.send(response);
    }
}
