/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.service.businessintelligence.multivisualisation.util.impl;

import com.abixen.platform.core.exception.PlatformRuntimeException;
import com.abixen.platform.service.businessintelligence.multivisualisation.util.DatabaseConnectionPasswordEncryption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@Component
public class DatabaseConnectionPasswordEncryptionImpl implements DatabaseConnectionPasswordEncryption {

    private static SecretKeySpec secretKey;
    private static byte[] key;
    private final Integer baseSixteen = 16;
    private final String iv = "sN3w2Va9i6bL2fd7";
    private final String secret = "Sa87LK45Sjsd98HG";

    @Override
    public void generateKey(String setKey) {

        MessageDigest sha = null;
        try {
            key = setKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, baseSixteen);
            secretKey = new SecretKeySpec(key, "AES");

        } catch (NoSuchAlgorithmException e) {
            throw new PlatformRuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new PlatformRuntimeException(e);
        }
    }

    @Override
    public String encryptPassword(String databaseConnectionPassword) {

        try {
            generateKey(secret);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes("UTF-8")));
            return Base64.getEncoder().encodeToString(cipher.doFinal(databaseConnectionPassword.getBytes("UTF-8")));
        } catch (Exception e) {
           throw new PlatformRuntimeException(e);
        }
    }

    @Override
    public String decryptPassword(String databaseConnectionEncryptedPassword) {

        try {
            generateKey(secret);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes("UTF-8")));
            return new String(cipher.doFinal(Base64.getDecoder().decode(databaseConnectionEncryptedPassword)));
        } catch (Exception e) {
            throw new PlatformRuntimeException(e);
        }
    }
}