/*
 * UpdateAccountDataHandler.java
 * WoWEmulator
 *
 * Created on May 9, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package wowemulator.world.handler;

import io.archivcore.networking.DataBuffer;
import wowemulator.networking.packet.Packet;
import wowemulator.player.AccountData;
import wowemulator.player.AccountDataType;
import wowemulator.utils.ZLibUtils;
import wowemulator.world.WorldSession;
import wowemulator.world.protocol.WorldOpcodeHandler;

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
