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

    MsgVerifyConnectivity(0x4F57),

    /*
     * Client messages
     */

    CmsgAuthProof               (0x01ED, WorldOpcodeStatus.Inactive), // 3.3.5
    CmsgCharEnum                (0x0037, WorldOpcodeStatus.Inactive), // 3.3.5
    CmsgRealmSplit              (0x038C, WorldOpcodeStatus.Inactive), // 3.3.5
    CmsgPing                    (0x01DC, WorldOpcodeStatus.Inactive), // 3.3.5
    CmsgReadyForAccountDataTimes(0x04FF, WorldOpcodeStatus.Inactive), // 3.3.5
    CmsgCharCreate              (0x0036, WorldOpcodeStatus.Inactive), // 3.3.5
    CmsgPlayerLogin             (0x003D, WorldOpcodeStatus.Inactive), // 3.3.5
    CmsgSetActiveVoiceChannel   (0x03D3, WorldOpcodeStatus.Inactive), // 3.3.5

    CmsgAuthSession             (0x00B2, WorldOpcodeStatus.Active), // 5.4.8
    CmsgLogDisconnect           (0x10B3, WorldOpcodeStatus.Active), // 5.4.8

    /*
     * Server messages
     */

    SmsgAuthChallenge      (0x0949), // 5.4.8
    SmsgAuthResponse       (0x0ABA), // 5.4.8
    SmsgClientCacheVersion (0X002A), // 5.4.8
    SmsgTutorialFlags      (0x1B90), // 5.4.8
    SmsgTimezoneInformation(0x19C1), // 5.4.8
    SmsgAddonInfo          (0x160A), // 5.4.8

    SmsgCharEnum        (0x003B), // 3.3.5
    SmsgPong            (0x01DD), // 3.3.5
    SmsgRealmSplit      (0x038B), // 3.3.5
    SmsgCharCreate      (0x003A), // 3.3.5
    SmsgLoginVerifyWorld(0x0236); // 3.3.5

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
