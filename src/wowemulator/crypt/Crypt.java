/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.crypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Dusko
 */
public class Crypt {

    protected byte[] ServerDecryptionKey;
    protected byte[] ServerEncryptionKey;
    protected boolean isEnabled = false;

    protected SARC4 _clientDecrypt = new SARC4();
    protected SARC4 _serverEncrypt = new SARC4();

    public Crypt() {
//        ServerEncryptionKey = new byte[]{ (byte) 0xCC,  (byte)0x98,  (byte)0xAE, 0x04, (byte)0xE8,  (byte)0x97, (byte)0xEA,  (byte)0xCA, 0x12,
//                        (byte)0xDD,  (byte)0xC0,  (byte)0x93, 0x42,  (byte)0x91, 0x53, 0x57 };
//
//        ServerDecryptionKey = new byte[]{ (byte) 0xC2, (byte) 0xB3, 0x72, 0x3C, (byte) 0xC6, (byte) 0xAE, (byte) 0xD9,
//                        (byte) 0xB5, 0x34, 0x3C, 0x53, (byte) 0xEE, 0x2F, 0x43, 0x67, (byte) 0xCE };
        ServerEncryptionKey = createKey(0x08, 0xF1, 0x95, 0x9F, 0x47, 0xE5, 0xD2, 0xDB, 0xA1, 0x3D, 0x77, 0x8F, 0x3F, 0x3E, 0xE7, 0x00);
        ServerDecryptionKey = createKey(0x40, 0xAA, 0xD3, 0x92, 0x26, 0x71, 0x43, 0x47, 0x3A, 0x31, 0x08, 0xA6, 0xE7, 0xDC, 0x98, 0x2A);
    }

    private static byte[] createKey(int... values) {
        byte[] key = new byte[values.length];

        for (int i = 0; i < values.length; i++) {
            key[i] = (byte)values[i];
        }

        return key;
    }

    public byte[] decrypt(byte[] data) {
        if (!isEnabled) {
            return data;
        }
        return _clientDecrypt.update(data);
    }

    public byte[] encrypt(byte[] data) {
        if (!isEnabled) {
            return data;
        }
        return _serverEncrypt.update(data);
    }

    public void init(byte[] key){
        byte[] encryptHash = getKey(ServerEncryptionKey, key);
        _serverEncrypt.init(encryptHash);
        byte[] decryptHash = getKey(ServerDecryptionKey, key);
        _clientDecrypt.init(decryptHash);
        byte[] tar = new byte[1024];
        for(int i = 0; i < tar.length; i++)
                tar[i] = 0;

        _serverEncrypt.update(tar);
        for(int i = 0; i < tar.length; i++)
                tar[i] = 0;

        _clientDecrypt.update(tar);
        this.isEnabled = true;
    }

    private byte[] getKey(byte[] EncryptionKey, byte[] key) {
        SecretKeySpec ds = new SecretKeySpec(EncryptionKey, "HmacSHA1");
        try {
            Mac m = Mac.getInstance("HmacSHA1");
            m.init(ds);
            return m.doFinal(key);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            Logger.getLogger(Crypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
