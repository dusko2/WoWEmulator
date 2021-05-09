/*
 * RealmClass.java
 * WoWEmulator
 *
 * Created on May 8, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package wowemulator.realm;

/**
 *
 * @author Dusko Mirkovic
 */
public class RealmClass {

    public final byte id;
    public final byte expansion;

    public RealmClass(byte id, byte expansion) {
        this.id = id;
        this.expansion = expansion;
    }
}
