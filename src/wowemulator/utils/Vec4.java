/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.utils;

/**
 *
 * @author Dusko
 */
public class Vec4 {

    public static final int BYTES = 16;
    
    public float x;
    public float y;
    public float z;
    public float o;

    public Vec4() {
        this(0, 0, 0, 0);
    }

    public Vec4(float x, float y, float z, float o) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.o = o;
    }
}
