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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.converter;

import com.abixen.platform.common.application.converter.AbstractConverter;
import com.abixen.platform.common.infrastructure.exception.PlatformRuntimeException;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data.DataValue;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class DataValueToDataValueDtoConverter extends AbstractConverter<DataValue, DataValueDto> {

    @Override
    public DataValueDto convert(DataValue dataValue, Map<String, Object> parameters) {
        if (dataValue == null) {
            return null;
        }

        if (dataValue.getValue() instanceof Integer) {
            DataValueDto<Integer> integerDataValueDto = new DataValueDto<>();
            integerDataValueDto.setValue((Integer) dataValue.getValue());

            return integerDataValueDto;
        }
        if (dataValue.getValue() instanceof Double) {
            DataValueDto<Double> doubleDataValueDto = new DataValueDto<>();
            doubleDataValueDto.setValue((Double) dataValue.getValue());

            return doubleDataValueDto;
        }
        if (dataValue.getValue() instanceof String) {
            DataValueDto<String> stringDataValueDto = new DataValueDto<>();
            stringDataValueDto.setValue((String) dataValue.getValue());

            return stringDataValueDto;
        }
        if (dataValue.getValue() instanceof Date) {
            DataValueDto<Date> dateDataValueDto = new DataValueDto<>();
            dateDataValueDto.setValue((Date) dataValue.getValue());

            return dateDataValueDto;
        }
        throw new PlatformRuntimeException("Value type not recognized.");
    }
}
