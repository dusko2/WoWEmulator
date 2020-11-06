/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.networking.packet;

/**
 *
 * @author Dusko
 */
public class PacketHeader {

    public final short opcode;
    public final short packetSize;

    public PacketHeader(short opcode, short packetSize) {
        this.opcode = opcode;
        this.packetSize = packetSize;
    }
}
