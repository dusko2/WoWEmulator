/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.client;

/**
 *
 * @author Dusko
 */
public class Client {

    public final String username;
    public final ClientInfo clientInfo;
    
    private byte[] sessionKey;

    public Client(String username, ClientInfo clientInfo) {
        this.username = username;
        this.clientInfo = clientInfo;
    }

    public void setSessionKey(byte[] sessionKey) {
        this.sessionKey = sessionKey;
    }

    public byte[] getSessionKey() {
        return sessionKey;
    }
}
