/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.logon.auth;

/**
 *
 * @author Dusko
 */
public enum AuthCodes {

    CharacterCreateSuccess(0x2F),
    AuthResponseOk        (0x0C),
    AuthUnknownAccount    (0x04),
    AuthAlreadyLoggedIn   (0x06);
    
    public final byte rawValue;

    private AuthCodes(int rawValue) {
        this.rawValue = (byte)rawValue;
    }
}
