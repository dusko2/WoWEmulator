/*
 * PlayerCharacter.java
 * WoWEmulator
 *
 * Created on May 12, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package wowemulator.player.character;

import wowemulator.guid.HighGuidType;
import wowemulator.guid.ObjectGuid;
import wowemulator.utils.Vec4;

/**
 *
 * @author Dusko Mirkovic
 */
public class PlayerCharacter {

    private static int autoIncrement = 1;

    public final ObjectGuid objectGuid;
    public final ObjectGuid guildGuid = ObjectGuid.create(0, 0, HighGuidType.None);

    public final Vec4 position = new Vec4(0, 0, 0, 0);

    public final CharacterCreateInfo info;

    public PlayerCharacter(ObjectGuid objectGuid, CharacterCreateInfo info) {
        this.objectGuid = objectGuid;
        this.info = info;
    }

    public static PlayerCharacter createNewCharacter(CharacterCreateInfo characterCreateInfo) {
        return new PlayerCharacter(ObjectGuid.create(autoIncrement++, 0, HighGuidType.Player), characterCreateInfo);
    }
}
