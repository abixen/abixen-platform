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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileColumnDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataValueDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataValueType
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data.DataValue
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data.DataValueString
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFileColumn
import spock.lang.Specification

class DataFileColumnToDataFileColumnDtoConverterTest extends Specification {

    private DataValueToDataValueDtoConverter dataValueToDataValueDtoConverter
    private DataFileColumnToDataFileColumnDtoConverter dataFileColumnToDataFileColumnDtoConverter

    void setup() {
        dataValueToDataValueDtoConverter = Mock();
        dataFileColumnToDataFileColumnDtoConverter = new DataFileColumnToDataFileColumnDtoConverter(dataValueToDataValueDtoConverter)
    }

    void "should return null when dataFileColumn is null"() {
        given:
        final DataFileColumn dataFileColumn = null

        when:
        final DataFileColumnDto result = dataFileColumnToDataFileColumnDtoConverter.convert(dataFileColumn)

        then:
        result == null
    }

    void "should convert DataFileColumn to DataFileColumnDto"() {
        given:
        final DataValue dataValue = [] as DataValueString
        final List<DataValue> dataValues = Collections.singletonList(dataValue)

        final DataFileColumn dataFileColumn = DataFileColumn.builder()
                .dataValueType(DataValueType.DATE)
                .name("name")
                .position(1)
                .values(dataValues)
                .build()

        final DataValueDto dataValueDto = [] as DataValueDto
        final List<DataValueDto> dataValueDtos = Collections.singletonList(dataValueDto)


        dataValueToDataValueDtoConverter.convertToList(dataValues) >> dataValueDtos

        when:
        final DataFileColumnDto dataFileColumnDto = dataFileColumnToDataFileColumnDtoConverter.convert(dataFileColumn)

        then:
        dataFileColumnDto != null
        dataFileColumnDto.getName() == dataFileColumn.getName()
        dataFileColumnDto.getDataValueType() == dataFileColumn.getDataValueType()
        dataFileColumnDto.getPosition() == dataFileColumn.getPosition()
        dataFileColumnDto.getValues() == dataValueDtos

        1 * dataValueToDataValueDtoConverter.convertToList(dataValues) >> dataValueDtos
        0 * _
    }

}