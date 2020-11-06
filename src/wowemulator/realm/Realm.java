/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.realm;

/**
 *
 * @author Dusko
 */
public class Realm {

    public final int id;
    public final String name;
    public final String address;
    public final int port;
    
    public final float population = 0; // Placeholder
    public final int flags = 0; // Placeholder

    public Realm(int id, String name, String address, int port) {
        this.id = id;
        this.name = name;
        this.address = address + ":" + Integer.toString(port);
        this.port = port;
    }
    
    public final short size() {
        return (short)(8 + 4 + address.length() + name.length());
    }
}
