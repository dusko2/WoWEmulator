/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.handler;

import wowemulator.world.protocol.WorldOpcodeHandler;
import wowemulator.networking.packet.Packet;
import wowemulator.player.AccountDataType;
import wowemulator.utils.BitPack;
import wowemulator.world.WorldSession;
import wowemulator.world.packet.WorldPacket;
import wowemulator.world.protocol.WorldOpcode;

/**
 *
 * @author Dusko
 */
public class ReadyForAccountDataTimesHandler implements WorldOpcodeHandler {

    @Override public void handle(WorldSession session, Packet packet) {
        WorldPacket response = new WorldPacket(WorldOpcode.SmsgAccountDataTimes, 4 + 1 + 4 + (8 * 4));
        BitPack pack = new BitPack(response);

        pack.write(1);
        pack.flush();

        for (AccountDataType dataType : AccountDataType.values()) {
            response.putInt((int)session.player.getAccountData(dataType).timestamp);
        }

        response.putInt(AccountDataType.globalCacheMask);
        response.putInt((int)System.currentTimeMillis());

        session.send(response);
    }
}
