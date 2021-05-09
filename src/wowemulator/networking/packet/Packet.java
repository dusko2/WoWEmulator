/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.networking.packet;

import java.nio.ByteBuffer;

/**
 *
 * @author Dusko
 */
public class Packet {

    public final short rawOpcode;
    public final short size;
    public final ByteBuffer body;

    public Packet(PacketHeader header, byte[] bodyBytes) {
        this.rawOpcode = header.opcode;
        this.size = header.packetSize;
        this.body = ByteBuffer.wrap(bodyBytes);
    }

    public Packet(int opcode, int size) {
        this.rawOpcode = (short)opcode;
        this.size = (short)size;
        this.body = ByteBuffer.allocate(size);
    }

    public final byte getByte() {
        return body.get();
    }

    public final void getBytes(byte[] bytes) {
        body.get(bytes);
    }

    public final void getBytes(byte[] bytes, int... indices) {
        for (int index : indices) {
            bytes[index] = getByte();
        }
    }

    public final short getShort() {
        return body.getShort();
    }

    public final int getInt() {
        return body.getInt();
    }

    public final long getLong() {
        return body.getLong();
    }

    public final String getString() {
        StringBuilder builder = new StringBuilder();

        byte value = getByte();
        while (value != 0) {
            builder.append((char)value);
            value = getByte();
        }

        return builder.toString();
    }

    public final String getString(int size) {
        byte[] bytes = new byte[size];
        body.get(bytes);

        return new String(bytes);
    }

    public final void putByte(byte value) {
        body.put(value);
    }

    public final void putBytes(byte[] bytes) {
        body.put(bytes);
    }

    public final void putShort(short value) {
        body.putShort(value);
    }

    public final void putInt(int value) {
        body.putInt(value);
    }

    public final void putLong(long value) {
        body.putLong(value);
    }

    public final void putFloat(float value) {
        body.putFloat(value);
    }

    public final void putString(String string, boolean nullTerminated) {
        body.put(string.getBytes());

        if (nullTerminated) {
            body.put((byte)0);
        }
    }

    public Packet wrap() {
        byte[] wrapped = new byte[body.position()];
        body.position(0);
        body.get(wrapped);

        return new Packet(new PacketHeader(rawOpcode, (short)wrapped.length), wrapped);
    }
}
