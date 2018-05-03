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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataValueType
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceColumn
import spock.lang.Specification

class DataSourceColumnToDataSourceColumnDtoConverterTest extends Specification {

    private DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter

    void setup() {
        dataSourceColumnToDataSourceColumnDtoConverter = new DataSourceColumnToDataSourceColumnDtoConverter()
    }

    void "should return null when DataSourceColumn is null"() {
        given:
        final DataSourceColumn dataSourceColumn = null

        when:
        final DataSourceColumnDto result = dataSourceColumnToDataSourceColumnDtoConverter.convert(dataSourceColumn)

        then:
        result == null
    }

    void "should convert DataSourceColumn to DataSourceColumnDto"() {
        given:
        final DataSourceColumn dataSourceColumn = DataSourceColumn.builder()
                        .details("name")
                        .parameters(DataValueType.DATE, 1)
                        .build()

        when:
        final DataSourceColumnDto dataSourceColumnDto = dataSourceColumnToDataSourceColumnDtoConverter.convert(dataSourceColumn)

        then:
        dataSourceColumnDto != null
        dataSourceColumnDto.getName() == dataSourceColumn.getName()
        dataSourceColumnDto.getDataValueType() == dataSourceColumn.getDataValueType()
        dataSourceColumnDto.getPosition() == dataSourceColumn.getPosition()

        0 * _
    }
}

