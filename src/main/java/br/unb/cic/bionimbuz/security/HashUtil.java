/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unb.cic.bionimbuz.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rafaelsardenberg
 */
public class HashUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HashUtil.class);
    private static final int KEY_SIZE = 256;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private HashUtil() {
        super();
    }
    public static String calculateSha3(final String path) throws IOException {
        try (
             FileInputStream fileInputStream = new FileInputStream(path);) {
            return calculateSha3(fileInputStream);
        }
    }
    public static String calculateSha3(final InputStream inputStream) throws IOException {
        long now = System.currentTimeMillis();
        final byte[] dataBytes = new byte[KEY_SIZE];
        final SHA3.DigestSHA3 digester = new SHA3.DigestSHA3(KEY_SIZE);
        int nread = 0;
        while ((nread = inputStream.read(dataBytes)) != -1) {
            digester.update(dataBytes, 0, nread);
        }
        final byte[] mdbytes = digester.digest();
        LOGGER.info(String.format("HASH time: %d", System.currentTimeMillis() - now));
        // Convert the byte to hex format
        now = System.currentTimeMillis();
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mdbytes.length; i++) {
            builder.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        LOGGER.info(String.format("HASH to HEX time: %d", System.currentTimeMillis() - now));
        return builder.toString();
        // Runtime.getRuntime().exec("sha256 " ).;
    }
}
