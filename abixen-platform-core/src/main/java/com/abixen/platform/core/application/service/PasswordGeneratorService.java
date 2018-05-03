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

package com.abixen.platform.core.application.service;

import com.abixen.platform.common.infrastructure.annotation.PlatformApplicationService;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
@PlatformApplicationService
public class PasswordGeneratorService {

    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUM = "0123456789";
    private static final String SPL_CHARS = "!@#$%^&*_=+-/";


    public String generate(int length, int noOfCAPSAlpha, int noOfDigits, int noOfSpecialChars) {
        log.debug("generate() - length: " + length + ", noOfCAPSAlpha: " + noOfCAPSAlpha + ", noOfDigits: " + noOfDigits + ", noOfSpecialChars: " + noOfSpecialChars);
        if (length < 1) {
            throw new IllegalArgumentException("Length should be greater than 0.");
        }
        if ((noOfCAPSAlpha + noOfDigits + noOfSpecialChars) != length) {
            throw new IllegalArgumentException("Length should be atleast sum of (CAPS, DIGITS, SPECIALS CHARS) Length!");
        }
        Random random = new Random();
        char[] passwordChars = new char[length];
        int index = 0;
        for (int i = 0; i < noOfCAPSAlpha; i++) {
            index = getNextIndex(random, length, passwordChars);
            passwordChars[index] = ALPHA_CAPS.charAt(random.nextInt(ALPHA_CAPS.length()));
        }
        for (int i = 0; i < noOfDigits; i++) {
            index = getNextIndex(random, length, passwordChars);
            passwordChars[index] = NUM.charAt(random.nextInt(NUM.length()));
        }
        for (int i = 0; i < noOfSpecialChars; i++) {
            index = getNextIndex(random, length, passwordChars);
            passwordChars[index] = SPL_CHARS.charAt(random.nextInt(SPL_CHARS.length()));
        }
        for (int i = 0; i < length; i++) {
            if (passwordChars[i] == 0) {
                passwordChars[i] = ALPHA.charAt(random.nextInt(ALPHA.length()));
            }
        }
        return new String(passwordChars);
    }

    private static int getNextIndex(Random random, int length, char[] passwordChars) {
        int index = random.nextInt(length);
        while (passwordChars[index] != 0) {
            index = random.nextInt(length);
        }
        return index;
    }
}