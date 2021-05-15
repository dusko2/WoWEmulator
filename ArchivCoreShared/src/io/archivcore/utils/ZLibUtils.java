/*
 * ZLibUtils.java
 * WoWEmulator
 *
 * Created on May 9, 2021
 * Copyright(c) Dusko Mirkovic, All Rights Reserved.
 *
 */

package io.archivcore.utils;

import io.archivcore.networking.DataBuffer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.InflaterInputStream;

/**
 *
 * @author Dusko Mirkovic
 */
public class ZLibUtils {

    public static DataBuffer decompress(byte[] source, int offset, int count, int destinationCount) {
        DataBuffer decompressed = new DataBuffer(destinationCount);

        try (InputStream decompressor = new InflaterInputStream(new ByteArrayInputStream(source, offset, count))) {
            byte[] decompressedData = new byte[destinationCount];
            decompressor.read(decompressedData);

            decompressed.putBytes(decompressedData);
        } catch (IOException ex) {
            Logger.getLogger(ZLibUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        decompressed.position(0);
        return decompressed;
    }

    public static DataBuffer decompress(byte[] source, int destinationCount) {
        return decompress(source, 0, source.length, destinationCount);
    }
}
