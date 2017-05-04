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

package com.abixen.platform.service.businessintelligence.multivisualisation.converter;

import com.abixen.platform.common.converter.AbstractConverter;
import com.abixen.platform.common.exception.PlatformRuntimeException;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.data.DataValue;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class DataValueToDataValueDtoConverter extends AbstractConverter<DataValue, DataValueDto> {

    @Override
    public DataValueDto convert(DataValue dataValue, Map<String, Object> parameters) {
        if (dataValue.getValue() instanceof Integer) {
            return new DataValueDto<Integer>()
                    .setValue((Integer) dataValue.getValue());
        }
        if (dataValue.getValue() instanceof Double) {
            return new DataValueDto<Double>()
                    .setValue((Double) dataValue.getValue());
        }
        if (dataValue.getValue() instanceof String) {
            return new DataValueDto<String>()
                    .setValue((String) dataValue.getValue());
        }
        if (dataValue.getValue() instanceof Date) {
            return new DataValueDto<Date>()
                    .setValue((Date) dataValue.getValue());
        }
        throw new PlatformRuntimeException("Value type not recognized.");
    }
}
