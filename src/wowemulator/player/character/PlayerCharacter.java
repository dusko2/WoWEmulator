/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.player.character;

/**
 *
 * @author Dusko
 */
public class PlayerCharacter {

    public final long guid;
    private byte level;
    
    public final String name;
    public final byte race;
    public final byte cClass;
    public final byte gender;
    public final byte skinColor;
    public final byte faceStyle;
    public final byte hairStyle;
    public final byte hairColor;
    public final byte facialHair;
    
    public PlayerCharacter(long guid, CharacterCreateInfo characterCreateInfo) {
        this.guid = guid;
        this.level = 1;
        
        this.name = characterCreateInfo.name;
        this.race = characterCreateInfo.race;
        this.cClass = characterCreateInfo.cClass;
        this.gender = characterCreateInfo.gender;
        this.skinColor = characterCreateInfo.skinColor;
        this.faceStyle = characterCreateInfo.faceStyle;
        this.hairStyle = characterCreateInfo.hairStyle;
        this.hairColor = characterCreateInfo.hairColor;
        this.facialHair = characterCreateInfo.facialHair;
    }

    public byte getLevel() {
        return level;
    }
}
