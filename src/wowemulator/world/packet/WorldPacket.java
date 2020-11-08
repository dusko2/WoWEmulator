/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.packet;

import wowemulator.world.protocol.WorldOpcode;
import java.nio.ByteOrder;
import wowemulator.networking.packet.Packet;

/**
 *
 * @author Dusko
 */
public class WorldPacket extends Packet {

    public WorldPacket(WorldOpcode opcode, int size) {
        super(opcode.getRawValue(), size);
        
        body.order(ByteOrder.LITTLE_ENDIAN);
    }
}
