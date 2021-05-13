/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.packet;

import io.archivcore.networking.DataBuffer;
import wowemulator.world.protocol.WorldOpcode;
import wowemulator.networking.packet.Packet;
import wowemulator.utils.Vec4;

/**
 *
 * @author Dusko
 */
public class WorldPacket extends Packet {

    public final WorldOpcode worldOpcode;

    public WorldPacket(WorldOpcode opcode, int size) {
        super(opcode.getRawValue(), size);

        worldOpcode = opcode;
    }

    public final void putPosition(Vec4 position) {
        body.putFloat(position.x);
        body.putFloat(position.y);
        body.putFloat(position.z);
        body.putFloat(position.o);
    }

    @Override public Packet wrap() {
        DataBuffer buffer = body.wrap();

        WorldPacket packet = new WorldPacket(worldOpcode, buffer.size);
        packet.body.putBytes(buffer.array());

        return packet;
    }
}
