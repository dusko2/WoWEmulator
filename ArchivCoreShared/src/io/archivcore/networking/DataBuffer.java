/*
 * DataBuffer.java
 * ArchivCoreShared
 *
 * Created on May 9, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package io.archivcore.networking;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author Dusko Mirkovic
 */
public class DataBuffer {

    private final ByteBuffer internalBuffer;
    public final int size;

    private byte bitPosition = 8;
    private byte bitValue;

    public DataBuffer(int capacity) {
        this.internalBuffer = ByteBuffer.allocate(capacity);
        this.internalBuffer.order(ByteOrder.LITTLE_ENDIAN);

        this.size = capacity;
    }

    public DataBuffer(byte[] data) {
        this.internalBuffer = ByteBuffer.wrap(data);
        this.internalBuffer.order(ByteOrder.LITTLE_ENDIAN);

        this.size = data.length;
    }

    public final byte[] array() {
        return internalBuffer.array();
    }

    public final int position() {
        return internalBuffer.position();
    }

    public final void position(int value) {
        internalBuffer.position(value);
    }

    public final void put(int position, byte value) {
        internalBuffer.put(position, value);
    }

    public final byte getByte() {
        return internalBuffer.get();
    }

    public final void getBytes(byte[] bytes) {
        internalBuffer.get(bytes);
    }

    public final void getBytes(byte[] bytes, int... indices) {
        for (int index : indices) {
            bytes[index] = getByte();
        }
    }

    public final short getShort() {
        return internalBuffer.getShort();
    }

    public final int getInt() {
        return internalBuffer.getInt();
    }

    public final long getLong() {
        return internalBuffer.getLong();
    }

    public final String getString() {
        StringBuilder builder = new StringBuilder();

        while (position() < size) {
            byte value = getByte();
            if (value == 0) {
                break;
            }

            builder.append((char)value);
        }

        return builder.toString();
    }

    public final String getString(int size) {
        byte[] bytes = new byte[size];
        internalBuffer.get(bytes);

        return new String(bytes);
    }

    public final byte getByteSeq(byte mask) {
        if (mask != 0) {
            return (byte)(mask ^ getByte());
        }

        return 0;
    }

    public final byte[] getGuidMask(int... order) {
        byte[] guidMask = new byte[order.length];

        for (int offset : order) {
            guidMask[offset] = getBit() ? (byte)1 : 0;
        }

        return guidMask;
    }

    public final byte[] getGuidBytes(byte[] guidMask, int... order) {
        byte[] guidBytes = new byte[order.length];

        for (int offset : order) {
            guidBytes[offset] = getByteSeq(guidMask[offset]);
        }

        return guidBytes;
    }

    public final void putByte(byte value) {
        internalBuffer.put(value);
    }

    public final void putBytes(byte[] bytes) {
        internalBuffer.put(bytes);
    }

    public final void putShort(short value) {
        internalBuffer.putShort(value);
    }

    public final void putInt(int value) {
        internalBuffer.putInt(value);
    }

    public final void putLong(long value) {
        internalBuffer.putLong(value);
    }

    public final void putFloat(float value) {
        internalBuffer.putFloat(value);
    }

    public final void putString(String string, boolean nullTerminated) {
        internalBuffer.put(string.getBytes());

        if (nullTerminated) {
            internalBuffer.put((byte)0);
        }
    }

    public final void putByteSeq(byte value) {
        if (value != 0) {
            putByte((byte)(value ^ 1));
        }
    }

    public final void putGuidBytes(byte[] guidBytes, int... order) {
        for (int offset : order) {
            putByteSeq(guidBytes[offset]);
        }
    }

    public final void putGuidMask(byte[] guidBytes, int... order) {
        for (int offset : order) {
            putBit(guidBytes[offset]);
        }
    }

    public DataBuffer wrap() {
        byte[] wrapped = new byte[internalBuffer.position()];
        internalBuffer.position(0);
        internalBuffer.get(wrapped);

        return new DataBuffer(wrapped);
    }

    public boolean getBit() {
        if (bitPosition == 8) {
            bitValue = getByte();
            bitPosition = 0;
        }

        int returnValue = bitValue;

        bitValue = (byte)(2 * returnValue);
        bitPosition++;

        return ((returnValue >> 7) == 1 || (returnValue >> 7) == -1);
    }

    public int getBits(int bitCount) {
        int returnValue = 0;

        for (int i = bitCount - 1; i >= 0; --i) {
            returnValue = getBit() ? (1 << i) | returnValue : returnValue;
        }

        return returnValue;
    }

    public void putBit(int bit) {
        bitPosition--;

        if (bit != 0) {
            bitValue |= (byte)(1 << (bitPosition));
        }

        if (bitPosition == 0) {
            bitPosition = 8;
            internalBuffer.put(bitValue);
            bitValue = 0;
        }
    }

    public void putBit(boolean bit) {
        putBit(bit ? 1 : 0);
    }

    public void putBit(int bit, int count) {
        for (int i = count - 1; i >= 0; --i) {
            putBit((bit >> i) & 1);
        }
    }

    public void flush() {
        if (bitPosition == 8) {
            return;
        }

        internalBuffer.put(bitValue);
        bitValue = 0;
        bitPosition = 8;
    }

//    public int getNameLength(byte bitCount) {
//        int returnValue = 0;
//        // Unknown, always before namelength bits...
//        getBit();
//
//        for (int i = bitCount - 1; i >= 0; --i) {
//            returnValue = getBit() ? (1 << i) | returnValue : returnValue;
//        }
//
//        return returnValue;
//    }
}
