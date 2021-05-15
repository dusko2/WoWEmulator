/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Dusko
 */
public class HashUtils {

    public static MessageDigest getSHA1() {
        try {
            return MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static String passwordHash(String username, String password) {
        String raw = username.toUpperCase() + ":" + password.toUpperCase();

        MessageDigest digest = getSHA1();
        digest.update(raw.getBytes());

        return DatatypeConverter.printHexBinary(digest.digest());
    }
}
