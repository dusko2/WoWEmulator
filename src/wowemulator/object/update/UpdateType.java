/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.object.update;

/**
 *
 * @author Dusko
 */
public enum UpdateType {

    Values           (0),
    Movement         (1),
    CreateObject     (2),
    CreateObject2    (3),
    OutOfRangeObjects(4),
    NearObjects      (5);
    
    public final byte rawValue;

    private UpdateType(int rawValue) {
        this.rawValue = (byte)rawValue;
    }
}
