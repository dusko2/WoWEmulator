/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.movement;

import wowemulator.utils.Vec4;

/**
 *
 * @author Dusko
 */
public class MovementInfo {

    public class TransportInfo {
        
        public long guid = 0;
        public final Vec4 position = new Vec4();
        public byte seat = 0;
        public int time = 0;
        public int time2 = 0;
    }
    
    public class JumpInfo {
        
        public float zSpeed = 0;
        public float sinAngle = 0;
        public float cosAngle = 0;
        public float xySpeed = 0;
    }
    
    public long guid = 0;
    public final MovementFlags movementFlags = new MovementFlags();
    public final MovementFlagsExtra movementFlagsExtra = new MovementFlagsExtra();
    public final Vec4 position = new Vec4();
    public int time = 0;
    
    public final TransportInfo transport = new TransportInfo();
    
    // swimming/flying
    public float pitch = 0;
    
    // falling
    public int fallTime = 0;
    
    public final JumpInfo jump = new JumpInfo();
    
    // spline
    public float splineElevation = 0;
}
