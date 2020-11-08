/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.packet;

import wowemulator.utils.BigNumber;
import wowemulator.world.protocol.WorldOpcode;

/**
 *
 * @author Dusko
 */
public class AuthChallengePacket extends WorldPacket {

    public AuthChallengePacket(byte[] authSeed) {
        super(WorldOpcode.SmsgAuthChallenge, 4 + 4 + 32);
        
        putInt(1);
        putBytes(authSeed);
        putBytes(BigNumber.randomBytes(32));
    }
}
