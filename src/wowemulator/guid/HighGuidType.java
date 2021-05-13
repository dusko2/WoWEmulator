/*
 * HighGUIDType.java
 * WoWEmulator
 *
 * Created on May 12, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package wowemulator.guid;

/**
 *
 * @author Dusko Mirkovic
 */
public enum HighGuidType {

    None(0x000),
    Item(0x400),
    Container(0x400),
    Player(0x000),
    GameObject(0xF11),
    Corpse(0xF101),
    AreaTrigger(0xF102);

    public final int rawValue;

    private HighGuidType(int rawValue) {
        this.rawValue = rawValue;
    }

    public int getFactor() {
        switch (this) {
            case Corpse:
            case AreaTrigger:
                return 48;

            default:
                return 52;
        }
    }

    public static HighGuidType get(int rawValue) {
        for (HighGuidType type : values()) {
            if (type.rawValue == rawValue) {
                return type;
            }
        }

        return null;
    }
}
