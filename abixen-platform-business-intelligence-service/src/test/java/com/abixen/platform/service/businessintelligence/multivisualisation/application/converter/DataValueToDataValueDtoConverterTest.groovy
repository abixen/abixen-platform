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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.converter

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataValueDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data.DataValue
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data.DataValueDate
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data.DataValueDouble
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data.DataValueInteger
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data.DataValueString
import spock.lang.Specification

class DataValueToDataValueDtoConverterTest extends Specification {

    private DataValueToDataValueDtoConverter dataValueToDataValueDtoConverter

    void setup() {
        dataValueToDataValueDtoConverter = new DataValueToDataValueDtoConverter()
    }


    void "should return null when DataValue is null"() {
        given:
        final DataValue dataValue = null

        when:
        final DataValueDto dataValueDto = dataValueToDataValueDtoConverter.convert(dataValue)

        then:
        dataValueDto == null
    }

    void "should convert DataValue to DataValueDto<Integer> if value instanceof Integer"() {
        given:
        final DataValue dataValue = DataValueInteger.builder()
                .value(Integer.valueOf(2))
                .build()

        when:
        final DataValueDto dataValueDto = dataValueToDataValueDtoConverter.convert(dataValue)

        then:
        dataValueDto != null
        dataValueDto.getValue() instanceof Integer
        dataValueDto.getValue() == dataValue.getValue()

        0 * _
    }

    void "should convert DataValue to DataValueDto<Double> if value instanceof Double"() {
        given:
        final DataValue dataValue = DataValueDouble.builder()
                .value(Double.valueOf(2))
                .build()

        when:
        final DataValueDto dataValueDto = dataValueToDataValueDtoConverter.convert(dataValue)

        then:
        dataValueDto != null
        dataValueDto.getValue() instanceof Double
        dataValueDto.getValue() == dataValue.getValue()

        0 * _
    }

    void "should convert DataValue to DataValueDto<String> if value instanceof String"() {
        given:
        final DataValue dataValue = DataValueString.builder()
                .value("value")
                .build()

        when:
        final DataValueDto dataValueDto = dataValueToDataValueDtoConverter.convert(dataValue)

        then:
        dataValueDto != null
        dataValueDto.getValue() instanceof String
        dataValueDto.getValue() == dataValue.getValue()

        0 * _
    }

    void "should convert DataValue to DataValueDto<Date> if value instanceof Date"() {
        given:
        final DataValue dataValue = DataValueDate.builder()
                .value(new Date())
                .build()

        when:
        final DataValueDto dataValueDto = dataValueToDataValueDtoConverter.convert(dataValue)

        then:
        dataValueDto != null
        dataValueDto.getValue() instanceof Date
        dataValueDto.getValue() == dataValue.getValue()

        0 * _
    }
}
