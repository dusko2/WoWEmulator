/*
 * UpdateAccountDataHandler.java
 * WoWEmulator
 *
 * Created on May 9, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package wowemulator.world.handler;

import java.nio.ByteBuffer;
import wowemulator.networking.packet.Packet;
import wowemulator.player.AccountData;
import wowemulator.player.AccountDataType;
import wowemulator.utils.BitUnpack;
import wowemulator.utils.ByteBufferUtils;
import wowemulator.utils.ZLibUtils;
import wowemulator.world.WorldSession;
import wowemulator.world.protocol.WorldOpcodeHandler;

/**
 *
 * @author Dusko Mirkovic
 */
public class UpdateAccountDataHandler implements WorldOpcodeHandler {

    @Override public void handle(WorldSession session, Packet packet) {
        int decompressedSize = packet.getInt();
        int timestamp = packet.getInt();
        int compCount = packet.getInt();

        byte[] compressedData = new byte[compCount];
        packet.getBytes(compressedData);

        ByteBuffer decompressed = ZLibUtils.decompress(compressedData, decompressedSize);

        BitUnpack unpack = new BitUnpack(packet);
        int type = unpack.getBits(3);

        if (type >= AccountDataType.maxValue) {
            System.out.println(">> [UpdateAccountData] Unknown AccountDataType: " + type);
            return;
        }

        String data = ByteBufferUtils.getString(decompressed);
        session.player.setAccountData(new AccountData(AccountDataType.get(type), timestamp, data));

        // TODO: Update after WorldOpcode.SmsgUpdateAccountDataDone structure is known
    }
}
