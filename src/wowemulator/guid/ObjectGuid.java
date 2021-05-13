/*
 * ObjectGuid.java
 * WoWEmulator
 *
 * Created on May 12, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package wowemulator.guid;

import io.archivcore.networking.DataBuffer;

/**
 *
 * @author Dusko Mirkovic
 */
public class ObjectGuid {

    public final long guid;
    public final byte[] bytes;

    public ObjectGuid() {
        this.guid = 0;
        this.bytes = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
    }

    public ObjectGuid(long guid) {
        this.guid = guid;
        this.bytes = new byte[Long.BYTES];

        DataBuffer buffer = new DataBuffer(Long.BYTES);
        buffer.putLong(this.guid);
        buffer.position(0);
        buffer.getBytes(this.bytes);
    }

    public static ObjectGuid create(int id, int e, HighGuidType type) {
        long part1 = (long)id;
        long part2 = (long)e << 32;
        long part3 = (long)type.rawValue << type.getFactor();

        return new ObjectGuid(part1 | part2 | part3);
    }
}
