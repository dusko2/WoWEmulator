/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.client;

import wowemulator.networking.packet.Packet;

/**
 *
 * @author Dusko
 */
public class ClientInfo {

    public final String gamename;
    public final String version;
    public final int build;
    public final String platform;
    public final String os;
    public final String country;
    public final int timezoneBias;
    public final String ipAddress;

    public ClientInfo(String gamename, String version, int build, String platform, String os, String country, int timezoneBias, String ipAddress) {
        this.gamename = gamename;
        this.version = version;
        this.build = build;
        this.platform = platform;
        this.os = os;
        this.country = country;
        this.timezoneBias = timezoneBias;
        this.ipAddress = ipAddress;
    }
    
    public static ClientInfo parse(Packet packet) {
        String gamename = packet.getString(4);
        String version = parseVersion(packet);
        int build = packet.getShort();
        String platform = packet.getString(4);
        String os = packet.getString(4);
        String country = packet.getString(4);
        int timezoneBias = packet.getInt();
        String ip = parseIPAddress(packet);
        
        return new ClientInfo(gamename, version, build, platform, os, country, timezoneBias, ip);
    }
    
    private static String parseVersion(Packet packet) {
        String version = Byte.toString(packet.getByte());
        
        byte middleValue = packet.getByte();
        version += Byte.toString(middleValue >= 10 ? 0 : middleValue);
        
        version += Byte.toString(packet.getByte());
        
        return version;
    }
    
    private static String parseIPAddress(Packet packet) {
        int intIP = packet.getInt();

        int octet[] = { 0, 0, 0, 0 };

        for (int i = 0; i < 4; i++) {
            octet[i] = ((intIP >> (i * 8)) & 0xFF);
        }

        return octet[3] + "." + octet[2] + "." + octet[1] + "." + octet[0];
    }
}
