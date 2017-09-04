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
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataValueType
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumn
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumnBuilder
import spock.lang.Specification

class DataSourceColumnToDataSourceColumnDtoConverterTest extends Specification {

    private static final String NAME = "name"
    private static final DataValueType DATA_VALUE_TYPE = DataValueType.STRING
    private static final int POSITION = 1

    private DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter;

    void setup() {
        dataSourceColumnToDataSourceColumnDtoConverter = new DataSourceColumnToDataSourceColumnDtoConverter();
    }

    void "should return null when dataSourceColumn is null"() {
        given:
        final DataSourceColumn dataSourceColumn = null;

        when:
        final DataSourceColumnDto result = dataSourceColumnToDataSourceColumnDtoConverter.convert(dataSourceColumn)

        then:
        result == null;
    }

    void "should convert DataSourceColumn to DataSourceColumnDto"() {
        given:
        final DataSourceColumn dataSourceColumn = new DataSourceColumnBuilder()
                        .details(NAME)
                        .paramters(DATA_VALUE_TYPE, POSITION)
                        .build()

        when:
        final DataSourceColumnDto result = dataSourceColumnToDataSourceColumnDtoConverter.convert(dataSourceColumn);

        then:
        result.name == dataSourceColumn.name;
        result.dataValueType == dataSourceColumn.dataValueType;
        result.position == dataSourceColumn.position;
    }
}

