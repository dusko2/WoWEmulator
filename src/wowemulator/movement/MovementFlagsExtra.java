/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.movement;

import wowemulator.movement.enums.MovementFlagExtra;

/**
 *
 * @author Dusko
 */
public class MovementFlagsExtra {

    public short flags;

    public MovementFlagsExtra() {
        this.flags = 0;
    }

    public MovementFlagsExtra(short flags) {
        this.flags = flags;
    }
    
    public final void addMovementFlagExtra(MovementFlagExtra movementFlagExtra) {
        flags |= movementFlagExtra.rawValue;
    }
    
    public final void removeMovementFlatExtra(MovementFlagExtra movementFlagExtra) {
        flags &= ~movementFlagExtra.rawValue;
    }
    
    public final boolean hasMovementFlagExtra(MovementFlagExtra movementFlagExtra) {
        return (flags & movementFlagExtra.rawValue) != 0;
    }
}
