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

package com.abixen.platform.service.businessintelligence.multivisualisation.model.util.encoder;

import com.abixen.platform.common.exception.PlatformRuntimeException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import static java.security.MessageDigest.getInstance;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

public final class AES128Encoder {
    private static final Integer BASE_SIXTEEN = 16;
    private static final String IV = "sN3w2Va9i6bL2fd7";
    private static final String SECRET = "Sa87LK45Sjsd98HG";

    private static SecretKeySpec generateKey(String setKey) {
        try {
            return new SecretKeySpec(prepareKey(setKey, getInstance("SHA-256")), "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new PlatformRuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new PlatformRuntimeException(e);
        }
    }

    private static byte[] prepareKey(String setKey, MessageDigest sha) throws UnsupportedEncodingException {
        return Arrays.copyOf(sha.digest(setKey.getBytes("UTF-8")), BASE_SIXTEEN);
    }

    public static String encryptPassword(String decryptedText) {

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(ENCRYPT_MODE, generateKey(SECRET), new IvParameterSpec(IV.getBytes("UTF-8")));
            return Base64.getEncoder().encodeToString(cipher.doFinal(decryptedText.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new PlatformRuntimeException(e);
        }
    }

    public static String decryptPassword(String encryptedText) {

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(DECRYPT_MODE, generateKey(SECRET), new IvParameterSpec(IV.getBytes("UTF-8")));
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
        } catch (Exception e) {
            throw new PlatformRuntimeException(e);
        }
    }
}
