/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.protocol;

/**
 *
 * @author Dusko
 */
public enum WorldOpcode {

    MsgTransferInitiate(0x4F57),
    
    /*
     * Client messages
     */
    
    CmsgAuthProof               (0x01ED, WorldOpcodeStatus.Active),
    CmsgCharEnum                (0x0037, WorldOpcodeStatus.Active),
    CmsgRealmSplit              (0x038C, WorldOpcodeStatus.Active),
    CmsgPing                    (0x01DC, WorldOpcodeStatus.Active),
    CmsgReadyForAccountDataTimes(0x04FF, WorldOpcodeStatus.Active),
    CmsgCharCreate              (0x0036, WorldOpcodeStatus.Active),
    CmsgPlayerLogin             (0x003D, WorldOpcodeStatus.Active),
    CmsgSetActiveVoiceChannel   (0x03D3, WorldOpcodeStatus.Inactive),
    
    /*
     * Server messages
     */
    
    SmsgAuthChallenge   (0x01EC),
    SmsgAuthResponse    (0x01EE),
    SmsgCharEnum        (0x003B),
    SmsgPong            (0x01DD),
    SmsgRealmSplit      (0x038B),
    SmsgCharCreate      (0x003A),
    SmsgLoginVerifyWorld(0x0236);
    
    private final int rawValue;
    public final WorldOpcodeStatus status;
    
    private WorldOpcode(int rawValue) {
        this.rawValue = rawValue;
        this.status = WorldOpcodeStatus.Active;
    }
    
    private WorldOpcode(int rawValue, WorldOpcodeStatus status) {
        this.rawValue = rawValue;
        this.status = status;
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
