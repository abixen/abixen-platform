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
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataValueType
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.data.DataValue
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFileColumn
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFileColumnBuilder
import spock.lang.Specification

class DataFileColumnToDataFileColumnDtoConverterTest extends Specification {

    private static final ArrayList<DataValue> VALUES = new ArrayList<DataValue>()
    private static final String NAME = "name"
    private static final int POSITION = 1
    private static final DataValueType DATA_VALUE_TYPE = DataValueType.STRING

    private DataValueToDataValueDtoConverter dataValueToDataValueDtoConverter;
    private DataFileColumnToDataFileColumnDtoConverter dataFileColumnToDataFileColumnDtoConverter;

    void setup() {
        dataValueToDataValueDtoConverter = Mock();
        dataFileColumnToDataFileColumnDtoConverter = new DataFileColumnToDataFileColumnDtoConverter(dataValueToDataValueDtoConverter);
    }

    void "should return null when dataFile is null"() {
        given:
        final DataFileColumn dataFileColumn = null;

        when:
        final DataFileColumnDto result = dataFileColumnToDataFileColumnDtoConverter.convert(dataFileColumn)

        then:
        result == null;
    }

    void "should convert DataFileColumn to DataFileColumnDto"() {
        given:
        final DataFileColumn dataFileColumn = new DataFileColumnBuilder()
                .dataValueType(DATA_VALUE_TYPE)
                .name(NAME)
                .position(POSITION)
                .values(VALUES)
                .build()



        final List<DataValueDto> dataValueDtoList = dataValueToDataValueDtoConverter.convertToList(VALUES)

        when:
        final DataFileColumnDto result = dataFileColumnToDataFileColumnDtoConverter.convert(dataFileColumn);

        then:
        result.name == dataFileColumn.name;
        result.dataValueType == dataFileColumn.dataValueType;
        result.position == dataFileColumn.position;
        result.values == dataValueDtoList;
    }
}
