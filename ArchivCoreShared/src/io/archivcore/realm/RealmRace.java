/*
 * RealmRace.java
 * WoWEmulator
 *
 * Created on May 8, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package io.archivcore.realm;

/**
 *
 * @author Dusko Mirkovic
 */
public class RealmRace {

    public final byte id;
    public final byte expansion;

    public RealmRace(byte id, byte expansion) {
        this.id = id;
        this.expansion = expansion;
    }
}
