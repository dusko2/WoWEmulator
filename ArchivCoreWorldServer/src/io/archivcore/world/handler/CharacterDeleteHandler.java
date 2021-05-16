/*
 * CharacterDeleteHandler.java
 * ArchivCoreWorldServer
 *
 * Created on May 15, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package io.archivcore.world.handler;

import io.archivcore.guid.ObjectGuid;
import io.archivcore.networking.packet.Packet;
import io.archivcore.world.WorldSession;
import io.archivcore.world.packet.WorldPacket;
import io.archivcore.world.protocol.WorldOpcode;
import io.archivcore.world.protocol.WorldOpcodeHandler;

/**
 *
 * @author Dusko Mirkovic
 */
public class CharacterDeleteHandler implements WorldOpcodeHandler {

    private enum CharacterDeleteResult {

        InProgress       (71),
        Success          (72),
        Failed           (73),
        LockedForTransfer(74),
        GuildLeader      (75),
        ArenaCaptain     (76),
        HasHeirloomOrMail(77);

        public final byte rawValue;

        private CharacterDeleteResult(int rawValue) {
            this.rawValue = (byte)rawValue;
        }
    }

    @Override public void handle(WorldSession session, Packet packet) {
        byte[] guidMask = packet.body.getGuidMask(1, 3, 2, 7, 4, 6, 0, 5);
        byte[] guidBytes = packet.body.getGuidBytes(guidMask, 7, 1, 6, 0, 3, 4, 2, 5);

        ObjectGuid objectGuid = new ObjectGuid(guidBytes);
        session.player.deleteCharacter(objectGuid);

        sendCharacterDeleteResponse(session, CharacterDeleteResult.Success);
    }

    private void sendCharacterDeleteResponse(WorldSession session, CharacterDeleteResult result) {
        WorldPacket packet = new WorldPacket(WorldOpcode.SmsgCharacterDelete, 1);
        packet.body.putByte(result.rawValue);
        session.send(packet);
    }
}
