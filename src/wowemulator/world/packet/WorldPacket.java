/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.packet;

import wowemulator.world.protocol.WorldOpcode;
import java.nio.ByteOrder;
import java.util.Arrays;
import wowlib.networking.packet.Packet;
import wowlib.utils.Vec4;

/**
 *
 * @author Dusko
 */
public class WorldPacket extends Packet {

    public WorldPacket(WorldOpcode opcode, int size) {
        super(opcode.getRawValue(), size);
        
        body.order(ByteOrder.LITTLE_ENDIAN);
    }
    
    public final void putPosition(Vec4 position) {
        putFloat(position.x);
        putFloat(position.y);
        putFloat(position.z);
        putFloat(position.o);
    }
    
    public final void putPackGUID(long guid) {
        byte[] packGUID = new byte[8 + 1];
        packGUID[0] = 0;
        
        int arraySize = 1;
        for (byte i = 0; guid != 0; i++) {
            if ((guid & 0xFF) > 0) {
                packGUID[0] |= (byte)(1 << i);
                packGUID[arraySize] = (byte)(guid & 0xFF);
                arraySize++;
            }

            guid >>= 8;
        }
        
        byte[] bytes = Arrays.copyOf(packGUID, arraySize);
        putBytes(bytes);
    }
}
