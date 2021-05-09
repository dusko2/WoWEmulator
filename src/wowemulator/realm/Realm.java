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

    public final RealmClass[] classes = new RealmClass[] {
        new RealmClass((byte)1, (byte)0),
        new RealmClass((byte)2, (byte)0),
        new RealmClass((byte)3, (byte)0),
        new RealmClass((byte)4, (byte)0),
        new RealmClass((byte)5, (byte)0),
        new RealmClass((byte)6, (byte)2),
        new RealmClass((byte)7, (byte)0),
        new RealmClass((byte)8, (byte)0),
        new RealmClass((byte)9, (byte)0),
        new RealmClass((byte)10, (byte)4),
        new RealmClass((byte)11, (byte)0)
    };

    public final RealmRace[] races = new RealmRace[] {
        new RealmRace((byte)1, (byte)0),
        new RealmRace((byte)2, (byte)0),
        new RealmRace((byte)3, (byte)0),
        new RealmRace((byte)4, (byte)0),
        new RealmRace((byte)5, (byte)0),
        new RealmRace((byte)6, (byte)0),
        new RealmRace((byte)7, (byte)0),
        new RealmRace((byte)8, (byte)0),
        new RealmRace((byte)9, (byte)3),
        new RealmRace((byte)10, (byte)1),
        new RealmRace((byte)11, (byte)1),
        new RealmRace((byte)22, (byte)3),
        new RealmRace((byte)24, (byte)4),
        new RealmRace((byte)25, (byte)4),
        new RealmRace((byte)26, (byte)4),
    };

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
