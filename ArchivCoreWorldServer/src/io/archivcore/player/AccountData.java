/*
 * AccountData.java
 * WoWEmulator
 *
 * Created on May 9, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package io.archivcore.player;

/**
 *
 * @author Dusko Mirkovic
 */
public class AccountData {

    public final AccountDataType type;
    public final long timestamp;
    public final String data;

    public AccountData(AccountDataType type, long timestamp, String data) {
        this.type = type;
        this.timestamp = timestamp;
        this.data = data;
    }
}
