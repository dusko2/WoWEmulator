/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator;

import wowemulator.logon.LogonServer;
import wowemulator.realm.Realm;
import wowemulator.realm.Realmlist;
import wowemulator.world.WorldServer;

/**
 *
 * @author Dusko
 */
public class WoWEmulator {

    public static final long serverStartTime = System.currentTimeMillis();

    public static void main(String[] args) {
        startLogonServer();
        startWorldServers();
    }

    private static void startLogonServer() {
        Realmlist.getInstance().load();

        LogonServer.getInstance().start();
    }

    private static void startWorldServers() {
        for (Realm realm : Realmlist.getInstance()) {
            WorldServer server = new WorldServer(realm.port);
            server.start();
        }
    }
}
