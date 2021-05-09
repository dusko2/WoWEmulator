/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.packet;

import wowemulator.world.protocol.WorldOpcode;

/**
 *
 * @author Dusko
 */
public class AuthChallengePacket extends WorldPacket {

    public AuthChallengePacket(byte[] authSeed) {
        super(WorldOpcode.SmsgAuthChallenge, 39);

        putShort((short)0);

        for (int i = 0; i < 8; i++) {
            putInt(0);
        }

        putByte((byte)1);
        putBytes(authSeed);
    }
}
