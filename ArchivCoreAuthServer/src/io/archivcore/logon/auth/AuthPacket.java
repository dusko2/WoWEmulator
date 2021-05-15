/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.logon.auth;

import io.archivcore.networking.packet.Packet;

/**
 *
 * @author Dusko
 */
public class AuthPacket extends Packet {

    public AuthPacket(int opcode, int size) {
        super(opcode, size);

        body.putByte((byte)rawOpcode);
    }
}
