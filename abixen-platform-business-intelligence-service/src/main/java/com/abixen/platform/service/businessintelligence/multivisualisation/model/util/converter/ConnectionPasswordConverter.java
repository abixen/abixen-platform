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

package com.abixen.platform.service.businessintelligence.multivisualisation.model.util.converter;

import com.abixen.platform.service.businessintelligence.multivisualisation.model.util.encoder.AES128Encoder;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class ConnectionPasswordConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String decryptedPassword) {
        if (decryptedPassword != null && !decryptedPassword.equals("")) {
            return AES128Encoder.encryptPassword(decryptedPassword);
        }
        return decryptedPassword;
    }

    @Override
    public String convertToEntityAttribute(String encryptedPassword) {
        if (encryptedPassword != null && !encryptedPassword.equals("")) {
            return AES128Encoder.decryptPassword(encryptedPassword);
        }
        return encryptedPassword;
    }
}
