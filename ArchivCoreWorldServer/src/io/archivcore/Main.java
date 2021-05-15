/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore;

import io.archivcore.realm.Realm;
import io.archivcore.realm.Realmlist;
import io.archivcore.world.WorldServer;

/**
 *
 * @author Dusko
 */
public class Main {

    public static final long serverStartTime = System.currentTimeMillis();

    public static void main(String[] args) {
        Realmlist.getInstance().load();

        for (Realm realm : Realmlist.getInstance()) {
            WorldServer server = new WorldServer(realm.port);
            server.start();
        }
    }
}
