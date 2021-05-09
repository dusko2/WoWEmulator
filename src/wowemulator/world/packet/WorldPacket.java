/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.packet;

import wowemulator.world.protocol.WorldOpcode;
import java.nio.ByteOrder;
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
        body.order(ByteOrder.LITTLE_ENDIAN);
    }

    public final void putPosition(Vec4 position) {
        putFloat(position.x);
        putFloat(position.y);
        putFloat(position.z);
        putFloat(position.o);
    }

    @Override public Packet wrap() {
        byte[] wrapped = new byte[body.position()];
        body.position(0);
        body.get(wrapped);

        WorldPacket packet = new WorldPacket(worldOpcode, wrapped.length);
        packet.putBytes(wrapped);

        return packet;
    }
}
