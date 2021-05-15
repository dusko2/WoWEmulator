/*
 * Main.java
 * ArchivCoreAuthServer
 *
 * Created on May 13, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package io.archivcore;

import io.archivcore.logon.LogonServer;
import io.archivcore.realm.Realmlist;

/**
 *
 * @author Dusko Mirkovic
 */
public class Main {

    public static void main(String[] args) {
        Realmlist.getInstance().load();
        LogonServer.getInstance().start();
    }
}
