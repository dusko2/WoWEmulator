/*
 * UpdateAccountDataHandler.java
 * WoWEmulator
 *
 * Created on May 9, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package io.archivcore.world.handler;

import io.archivcore.networking.DataBuffer;
import io.archivcore.networking.packet.Packet;
import io.archivcore.player.AccountData;
import io.archivcore.player.AccountDataType;
import io.archivcore.utils.ZLibUtils;
import io.archivcore.world.WorldSession;
import io.archivcore.world.protocol.WorldOpcodeHandler;

/**
 *
 * @author Dusko Mirkovic
 */
public class UpdateAccountDataHandler implements WorldOpcodeHandler {

    @Override public void handle(WorldSession session, Packet packet) {
        int decompressedSize = packet.body.getInt();
        int timestamp = packet.body.getInt();
        int compCount = packet.body.getInt();

        byte[] compressedData = new byte[compCount];
        packet.body.getBytes(compressedData);

        int type = packet.body.getBits(3);
        if (type >= AccountDataType.maxValue) {
            System.out.println(">> [UpdateAccountData] Unknown AccountDataType: " + type);
            return;
        }

        DataBuffer decompressed = ZLibUtils.decompress(compressedData, decompressedSize);
        String data = decompressed.getString();

        session.player.setAccountData(new AccountData(AccountDataType.get(type), timestamp, data));

        // TODO: Update after WorldOpcode.SmsgUpdateAccountDataDone structure is known
    }
}
