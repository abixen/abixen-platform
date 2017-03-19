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

package com.abixen.platform.service.businessintelligence.multivisualisation.util;

import com.abixen.platform.common.exception.PlatformRuntimeException;
import com.abixen.platform.common.model.Model;

import java.lang.reflect.Field;


public class SqlParameterUtil {

    protected static <T> Class<?> getDomainAttributeType(String domainAttributeName, Class<T> domainType) {
        Field f = null;
        try {
            f = domainType.getDeclaredField(domainAttributeName);
        } catch (NoSuchFieldException e) {
            throw new PlatformRuntimeException(e);
        }
        return f.getType();
    }

    public static <T> String getSqlConditionLeftArgument(Class<T> domainType, String domainAttributeName) {
        final String entityAlias = "e";
        String condition;

        Class<?> domainAttributeType = getDomainAttributeType(domainAttributeName, domainType);

        if (Enum.class.isAssignableFrom(domainAttributeType)) {
            condition = entityAlias + "." + domainAttributeName;
        } else if (Model.class.isAssignableFrom(domainAttributeType)) {
            condition = entityAlias + "." + domainAttributeName;
        } else {
            condition = entityAlias + "." + domainAttributeName;
        }

        return condition;
    }

    public static <T, I> I getParameterValue(Class<T> domainType, String domainAttributeName, I attributeValue) {
        Class<?> domainAttributeType = getDomainAttributeType(domainAttributeName, domainType);

        if (Enum.class.isAssignableFrom(domainAttributeType)) {
            return (I) getParameterAsEnumType(attributeValue, domainAttributeType);
        } else if (String.class.isAssignableFrom(domainAttributeType)) {
            String attributeValueString = (String) attributeValue;
            if (((String) attributeValue).startsWith("*")) {
                attributeValueString = attributeValueString.replaceFirst("\\*", "%");
            }
            if (((String) attributeValue).endsWith("*")) {
                attributeValueString = attributeValueString.substring(0, attributeValueString.length() - 1) + "%";
            }
            return (I) attributeValueString;

        } else {
            return (I) attributeValue;
        }
    }

    public static <T> T getParameterAsEnumType(Object attributeValue, Class<T> enumType) {
        T parameter = null;
        try {
            parameter = (T) enumType.getMethod("valueOf", String.class).invoke(enumType, attributeValue);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return parameter;
    }
}
