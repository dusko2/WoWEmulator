/*
 * ZLibUtils.java
 * WoWEmulator
 *
 * Created on May 9, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package wowemulator.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.InflaterInputStream;

/**
 *
 * @author Dusko Mirkovic
 */
public class ZLibUtils {

    public static ByteBuffer decompress(byte[] source, int offset, int count, int destinationCount) {
        ByteBuffer decompressed = ByteBuffer.allocate(destinationCount);
        decompressed.order(ByteOrder.LITTLE_ENDIAN);

        try (InputStream decompressor = new InflaterInputStream(new ByteArrayInputStream(source, offset, count))) {
            byte[] decompressedData = new byte[destinationCount];
            decompressor.read(decompressedData);

            decompressed.put(decompressedData);
        } catch (IOException ex) {
            Logger.getLogger(ZLibUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        decompressed.position(0);
        return decompressed;
    }

    public static ByteBuffer decompress(byte[] source, int destinationCount) {
        return decompress(source, 0, source.length, destinationCount);
    }
}
