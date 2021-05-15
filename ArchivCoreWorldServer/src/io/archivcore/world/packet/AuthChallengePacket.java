/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.world.packet;

import io.archivcore.world.protocol.WorldOpcode;

/**
 *
 * @author Dusko
 */
public class AuthChallengePacket extends WorldPacket {

    public AuthChallengePacket(byte[] authSeed) {
        super(WorldOpcode.SmsgAuthChallenge, 39);

        body.putShort((short)0);

        for (int i = 0; i < 8; i++) {
            body.putInt(0);
        }

        body.putByte((byte)1);
        body.putBytes(authSeed);
    }
}
