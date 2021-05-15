/*
 * Account.java
 * ArchivCoreAuthServer
 *
 * Created on May 15, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package io.archivcore.account;

import io.archivcore.database.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Dusko Mirkovic
 */
public class Account {

    public final int id;
    public final String username;
    public final String hash;

    private String sessionKey;

    public Account(int id, String username, String hash, String sessionKey) {
        this.id = id;
        this.username = username;
        this.hash = hash;
        this.sessionKey = sessionKey;
    }

    public byte[] getHashBytes() {
        return DatatypeConverter.parseHexBinary(hash);
    }

    public byte[] getSessionKeyBytes() {
        return DatatypeConverter.parseHexBinary(sessionKey);
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        DatabaseConnection.getInstance().executeUpdate("UPDATE account SET session_key = '" + sessionKey + "' WHERE id = " + id);
    }

    public void setSessionKey(byte[] sessionKey) {
        setSessionKey(DatatypeConverter.printHexBinary(sessionKey));
    }

    public static Account findBy(int id) {
        DatabaseConnection connection = DatabaseConnection.getInstance();
        ResultSet result = connection.executeQuery("SELECT * FROM account WHERE id = " + id);

        try {
            if (result.next()) {
                String username = result.getString("username");
                String hash = result.getString("hash");
                String sessionKey = result.getString("session_key");

                return new Account(id, username, hash, sessionKey);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return null;
    }

    public static Account findBy(String username) {
        DatabaseConnection connection = DatabaseConnection.getInstance();
        ResultSet result = connection.executeQuery("SELECT * FROM account WHERE username = '" + username + "'");

        try {
            if (result.next()) {
                int id = result.getInt("id");
                String hash = result.getString("hash");
                String sessionKey = result.getString("session_key");

                return new Account(id, username, hash, sessionKey);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return null;
    }
}
