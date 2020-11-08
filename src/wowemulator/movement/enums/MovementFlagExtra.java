/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.movement.enums;

/**
 *
 * @author Dusko
 */
public enum MovementFlagExtra {

    None                (0x00000000),
    NoStrafe            (0x00000001),
    NoJumping           (0x00000002),
    Unknown3            (0x00000004),
    FullSpeedTurning    (0x00000008),
    FullSpeedPitching   (0x00000010),
    AlwaysAllowPitching (0x00000020),
    Unknown7            (0x00000040),
    Unknown8            (0x00000080),
    Unknown9            (0x00000100),
    Unknown10           (0x00000200),
    InterpolatedMovement(0x00000400),
    InterpolatedTurning (0x00000800),
    InterpolatedPitching(0x00001000),
    Unknown14           (0x00002000),
    Unknown15           (0x00004000),
    Unknown16           (0x00008000);
    
    public final short rawValue;

    private MovementFlagExtra(int rawValue) {
        this.rawValue = (short)rawValue;
    }
}
