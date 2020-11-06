/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.logon.auth;

/**
 *
 * @author Dusko
 */
public enum AuthOpcode {

    ClientLogonChallenge(0x00,                       -1),
    ClientLogonProof    (0x01, 1 + 32 + 20 + 20 + 1 + 1),
    ClientRealmlist     (0x10,                        4);

    private final int rawValue;
    private final int size;

    private AuthOpcode(int rawValue, int size) {
        this.rawValue = rawValue;
        this.size = size;
    }

    public short getRawValue() {
        return (short)rawValue;
    }

    public short getSize() {
        return (short)size;
    }

    public static AuthOpcode getOpcode(short value) {
        for (AuthOpcode opcode : AuthOpcode.values()) {
            if (opcode.getRawValue() == value) {
                return opcode;
            }
        }
        
        return null;
    }
}
