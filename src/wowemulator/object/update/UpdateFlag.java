/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.object.update;

import wowlib.utils.flag.BaseFlag;

/**
 *
 * @author Dusko
 */
public enum UpdateFlag implements BaseFlag<Short> {

    None              (0x0000),
    Self              (0x0001),
    Transport         (0x0002),
    HasTarget         (0x0004),
    Unknown           (0x0008),
    LowGuid           (0x0010),
    Living            (0x0020),
    StationaryPosition(0x0040),
    Vehicle           (0x0080),
    Position          (0x0100),
    Rotation          (0x0200);
    
    private final short rawValue;

    private UpdateFlag(int rawValue) {
        this.rawValue = (short)rawValue;
    }

    @Override public Short rawValue() {
        return rawValue;
    }
}
