/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.player.character;

import wowemulator.WoWEmulator;
import wowemulator.movement.enums.MovementFlag;
import wowemulator.movement.enums.MovementFlagExtra;
import wowemulator.movement.MovementInfo;
import wowemulator.object.update.UpdateFlag;
import wowemulator.object.update.UpdateFlags;
import wowemulator.object.update.UpdateType;
import wowemulator.world.packet.WorldPacket;
import wowlib.utils.Vec4;

/**
 *
 * @author Dusko
 */
public class PlayerCharacter {

    public final long guid;
    
    public final String name;
    public final byte race;
    public final byte cClass;
    public final byte gender;
    public final byte skinColor;
    public final byte faceStyle;
    public final byte hairStyle;
    public final byte hairColor;
    public final byte facialHair;
    
    private final byte level;
    
    private final int zoneID = 0;
    private final int mapID = 1;
    public final Vec4 position = new Vec4(1, 1, 50, 1);
    
    private int guildID;
    
    private final UpdateFlags updateFlags = new UpdateFlags();
    private final MovementInfo movementInfo = new MovementInfo();
    
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
    
    public final void buildCreateUpdateBlock(WorldPacket packet, boolean newObject, PlayerCharacter target) {
        UpdateType updateType = newObject ? UpdateType.CreateObject2 : UpdateType.CreateObject;
        
        if (target == this) {
            updateFlags.add(UpdateFlag.Self);
        }
        
        packet.putByte(updateType.rawValue);
        packet.putPackGUID(guid);
        packet.putByte((byte)4); // Player = 4
        
        buildMovementPacket(packet);
    }
    
    public final void buildMovementUpdate(WorldPacket packet) {
        
    }
    
    public final void buildMovementPacket(WorldPacket packet) {
        packet.putInt(movementInfo.movementFlags.intValue());
        packet.putShort(movementInfo.movementFlagsExtra.shortValue());
        packet.putInt((int)WoWEmulator.serverStartTime);
        packet.putPosition(position);
        
        if (movementInfo.movementFlags.contains(MovementFlag.OnTransport)) {
            
            
            if (movementInfo.movementFlagsExtra.contains(MovementFlagExtra.InterpolatedMovement)) {
                
            }
        }
        
        if (movementInfo.movementFlags.contains(MovementFlag.Swimming) ||
            movementInfo.movementFlags.contains(MovementFlag.Flying) ||
            movementInfo.movementFlagsExtra.contains(MovementFlagExtra.AlwaysAllowPitching)) {
            
            packet.putFloat(movementInfo.pitch);
        }
        
        packet.putInt(movementInfo.fallTime);
        
        if (movementInfo.movementFlags.contains(MovementFlag.Falling)) {
            packet.putFloat(movementInfo.jump.zSpeed);
            packet.putFloat(movementInfo.jump.sinAngle);
            packet.putFloat(movementInfo.jump.cosAngle);
            packet.putFloat(movementInfo.jump.xySpeed);
        }
        
        if (movementInfo.movementFlags.contains(MovementFlag.SplineElevation)) {
            packet.putFloat((movementInfo.splineElevation));
        }
    }

    public byte getLevel() {
        return level;
    }

    public int getZoneID() {
        return zoneID;
    }

    public int getMapID() {
        return mapID;
    }

    public int getGuildID() {
        return guildID;
    }
}
