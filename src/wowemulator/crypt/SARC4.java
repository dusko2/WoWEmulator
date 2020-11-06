/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.crypt;

/**
 *
 * @author Dusko
 */
public class SARC4 {

    private final byte state[] = new byte[256];
    private int x;
    private int y;
    
    public boolean init(byte[] key) {
        for (int i = 0; i < 256; i++) {
            state[i] = (byte)i;
        }

        x = 0;
        y = 0;

        int index1 = 0;
        int index2 = 0;

        byte tmp;

        if (key == null || key.length == 0) {
            throw new NullPointerException();
        }
    
        for (int i=0; i < 256; i++) {

            index2 = ((key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;

            tmp = state[i];
            state[i] = state[index2];
            state[index2] = tmp;

            index1 = (index1 + 1) % key.length;
        }
        
        return true;
    }
    
    public byte[] update(byte[] buf) {
        int xorIndex;
        byte tmp;

        if (buf == null) {
            return null;
        }

        byte[] result = new byte[buf.length];

        for (int i=0; i < buf.length; i++) {

            x = (x + 1) & 0xff;
            y = ((state[x] & 0xff) + y) & 0xff;

            tmp = state[x];
            state[x] = state[y];
            state[y] = tmp;

            xorIndex = ((state[x] &0xff) + (state[y] & 0xff)) & 0xff;
            result[i] = (byte)(buf[i] ^ state[xorIndex]);
        }

        return result;
    }
}
