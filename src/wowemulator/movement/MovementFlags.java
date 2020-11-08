/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.movement;

import wowemulator.movement.enums.MovementFlag;

/**
 *
 * @author Dusko
 */
public class MovementFlags {

    public int flags;

    public MovementFlags() {
        this.flags = 0;
    }

    public MovementFlags(int flags) {
        this.flags = flags;
    }
    
    public final void addMovementFlag(MovementFlag movementFlag) {
        flags |= movementFlag.rawValue;
    }
    
    public final void removeMovementFlat(MovementFlag movementFlag) {
        flags &= ~movementFlag.rawValue;
    }
    
    public final boolean hasMovementFlag(MovementFlag movementFlag) {
        return (flags & movementFlag.rawValue) != 0;
    }
}
