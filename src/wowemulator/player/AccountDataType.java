/*
 * AccountDataType.java
 * WoWEmulator
 *
 * Created on May 9, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package wowemulator.player;

/**
 *
 * @author Dusko Mirkovic
 */
public enum AccountDataType {

    GlobalConfigCache        (0),
    PerCharacterConfigCache  (1),
    GlobalBindingsCache      (2),
    PerCharacterBindingsCache(3),
    GlobalMacrosCache        (4),
    PerCharacterMacrosCache  (5),
    PerCharacterLayoutCache  (6),
    PerCharacterChatCache    (7);

    public static final int globalCacheMask = 0x15;
    public static final int perCharacterCacheMask = 0xEA;
    public static final int maxValue = 8;

    public final int rawValue;

    private AccountDataType(int rawValue) {
        this.rawValue = rawValue;
    }

    public static AccountDataType get(int rawValue) {
        for (AccountDataType type : AccountDataType.values()) {
            if (type.rawValue == rawValue) {
                return type;
            }
        }

        return null;
    }
}
