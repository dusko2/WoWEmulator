/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.networking.packet;

import io.archivcore.networking.DataBuffer;

/**
 *
 * @author Dusko
 */
public class Packet {

    public final short rawOpcode;
    public final short size;
    public final DataBuffer body;

    public Packet(PacketHeader header, byte[] bodyBytes) {
        this.rawOpcode = header.opcode;
        this.size = header.packetSize;
        this.body = new DataBuffer(bodyBytes);
    }

    public Packet(int opcode, int size) {
        this.rawOpcode = (short)opcode;
        this.size = (short)size;
        this.body = new DataBuffer(size);
    }

    public Packet(short rawOpcode, short size, DataBuffer body) {
        this.rawOpcode = rawOpcode;
        this.size = size;
        this.body = body;
    }

    public Packet wrap() {
        DataBuffer buffer = body.wrap();
        PacketHeader header = new PacketHeader(rawOpcode, (short)buffer.size);

        return new Packet(header, buffer.array());
    }
}
