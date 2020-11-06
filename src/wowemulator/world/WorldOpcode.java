/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world;

/**
 *
 * @author Dusko
 */
public enum WorldOpcode {

    MsgTransferInitiate(0x4F57),
    
    CmsgAuthProof      (0x01ED),
    CmsgCharEnum       (0x0037),
    CmsgRealmSplit     (0x038C),
    CmsgPing           (0x01DC),
    
    SmsgAuthChallenge  (0x01EC),
    SmsgAuthResponse   (0x01EE),
    SmsgCharEnum       (0x003B),
    SmsgPong           (0x01DD);
    
    private final int rawValue;

    private WorldOpcode(int rawValue) {
        this.rawValue = rawValue;
    }

    public short getRawValue() {
        return (short)rawValue;
    }
    
    public static WorldOpcode get(int rawValue) {
        for (WorldOpcode opcode : WorldOpcode.values()) {
            if (opcode.rawValue == rawValue) {
                return opcode;
            }
        }
        
        return null;
    }
}
