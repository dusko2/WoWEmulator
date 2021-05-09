/*
 * AddonData.java
 * WoWEmulator
 *
 * Created on May 9, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package wowemulator.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dusko Mirkovic
 */
public class AddonData {

    public final String name;
    public final int crc;

    public AddonData(String name, int crc) {
        this.name = name;
        this.crc = crc;
    }

    public static List<AddonData> getAddonData() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddonData.class.getName()).log(Level.SEVERE, null, ex);
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/characters", "root", "root");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        ResultSet result = null;
        try {
            result = statement.executeQuery("SELECT name, crc FROM addons");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        List<AddonData> list = new LinkedList<>();

        try {
            while (result.next()) {
                AddonData data = new AddonData(result.getString("name"), result.getInt("crc"));
                list.add(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddonData.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
}
