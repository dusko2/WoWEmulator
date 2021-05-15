/*
 * CharacterCreateInfo.java
 * WoWEmulator
 *
 * Created on May 12, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package io.archivcore.player.character;

import io.archivcore.networking.packet.Packet;

/**
 *
 * @author Dusko Mirkovic
 */
public class CharacterCreateInfo {

    public final byte outfitID;
    public final byte hairStyle;
    public final byte clazz;
    public final byte skin;
    public final byte face;
    public final byte race;
    public final byte facialHair;
    public final byte gender;
    public final byte hairColor;
    public final String name;

    public CharacterCreateInfo(Packet packet) {
        this.outfitID = packet.body.getByte();
        this.hairStyle = packet.body.getByte();
        this.clazz = packet.body.getByte();
        this.skin = packet.body.getByte();
        this.face = packet.body.getByte();
        this.race = packet.body.getByte();
        this.facialHair = packet.body.getByte();
        this.gender = packet.body.getByte();
        this.hairColor = packet.body.getByte();

        int nameLength = packet.body.getBits(6);
        boolean unknown = packet.body.getBit();

        this.name = packet.body.getString(nameLength);

        if (unknown) {
            packet.body.getInt();
        }
    }
}
