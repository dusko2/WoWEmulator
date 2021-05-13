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

    CmsgAuthSession             (0x00B2, WorldOpcodeStatus.Active), // 5.4.8
    CmsgLogDisconnect           (0x10B3, WorldOpcodeStatus.Active), // 5.4.8
    CmsgReadyForAccountDataTimes(0x031C, WorldOpcodeStatus.Active), // 5.4.8
    CmsgEnumCharacters          (0x00E0, WorldOpcodeStatus.Active), // 5.4.8
    CmsgPing                    (0x0012, WorldOpcodeStatus.Active), // 5.4.8
    CmsgRealmSplit              (0x18B2, WorldOpcodeStatus.Inactive), // 5.4.8
    CmsgUpdateAccountData       (0x0068, WorldOpcodeStatus.Active), // 5.4.8
    CmsgRandomizeCharacterName  (0x0B1C, WorldOpcodeStatus.Active), // 5.4.8
    CmsgCharacterCreate         (0x0F1D, WorldOpcodeStatus.Active), // 5.4.8

    /*
     * Server messages
     */

    SmsgAuthChallenge         (0x0949), // 5.4.8
    SmsgAuthResponse          (0x0ABA), // 5.4.8
    SmsgClientCacheVersion    (0X002A), // 5.4.8
    SmsgTutorialFlags         (0x1B90), // 5.4.8
    SmsgTimezoneInformation   (0x19C1), // 5.4.8
    SmsgAddonInfo             (0x160A), // 5.4.8
    SmsgAccountDataTimes      (0x162B), // 5.4.8
    SmsgPong                  (0x1969), // 5.4.8
    SmsgEnumCharactersResult  (0x11C3), // 5.4.8
    SmsgRandomizeCharacterName(0x169F), // 5.4.8
    SmsgCharacterCreate       (0x1CAA), // 5.4.8

    SmsgUpdateAccountDataDone (0x0000); // Unknown

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
