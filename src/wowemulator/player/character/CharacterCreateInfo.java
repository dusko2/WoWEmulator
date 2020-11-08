/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.player.character;

import wowemulator.networking.packet.Packet;

/**
 *
 * @author Dusko
 */
public class CharacterCreateInfo {

    public final String name;
    public final byte race;
    public final byte cClass;
    public final byte gender;
    public final byte skinColor;
    public final byte faceStyle;
    public final byte hairStyle;
    public final byte hairColor;
    public final byte facialHair;
    
    public CharacterCreateInfo(Packet packet) {
        this.name = formatted(packet.getString());
        this.race = packet.getByte();
        this.cClass = packet.getByte();
        this.gender = packet.getByte();
        this.skinColor = packet.getByte();
        this.faceStyle = packet.getByte();
        this.hairStyle = packet.getByte();
        this.hairColor = packet.getByte();
        this.facialHair = packet.getByte();
    }
    
    private String formatted(String string) {
        return Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase();
    }
}
