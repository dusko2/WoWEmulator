/*
 * DatabaseConnection.java
 * ArchivCoreShared
 *
 * Created on May 13, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package io.archivcore.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Dusko Mirkovic
 */
public class DatabaseConnection {

    private final Connection connection;

    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/archiv_core", "root", "root");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public final ResultSet executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public final void executeUpdate(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static class DatabaseConnectionInstance {

        public static final DatabaseConnection instance = new DatabaseConnection();
    }

    public static DatabaseConnection getInstance() {
        return DatabaseConnectionInstance.instance;
    }
}
