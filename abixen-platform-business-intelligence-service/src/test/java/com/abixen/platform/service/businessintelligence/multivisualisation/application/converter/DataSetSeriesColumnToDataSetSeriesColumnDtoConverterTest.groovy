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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesColumnDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.ColumnType
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataSetSeriesColumn
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceColumn
import spock.lang.Specification

class DataSetSeriesColumnToDataSetSeriesColumnDtoConverterTest extends Specification {

    private DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter
    private DataSetSeriesColumnToDataSetSeriesColumnDtoConverter dataSetSeriesColumnToDataSetSeriesColumnDtoConverter;

    void setup() {
        dataSourceColumnToDataSourceColumnDtoConverter = Mock()
        dataSetSeriesColumnToDataSetSeriesColumnDtoConverter = new DataSetSeriesColumnToDataSetSeriesColumnDtoConverter(dataSourceColumnToDataSourceColumnDtoConverter)
    }

    void "should return null when DataSetSeriesColumn is null"() {
        given:
        final DataSetSeriesColumn dataSetSeriesColumn = null

        when:
        final DataSetSeriesColumnDto dataSetSeriesColumnDto = dataSetSeriesColumnToDataSetSeriesColumnDtoConverter.convert(dataSetSeriesColumn)

        then:
        dataSetSeriesColumnDto == null
    }

    void "should convert DataSetSeriesColumn to DataSetSeriesColumnDto"() {
        given:
        final DataSourceColumn dataSourceColumn = [] as DataSourceColumn

        final DataSetSeriesColumn dataSetSeriesColumn = DataSetSeriesColumn.builder()
                .name("name")
                .column(ColumnType.X, dataSourceColumn)
                .build()

        final DataSourceColumnDto dataSourceColumnDto = [] as DataSourceColumnDto

        dataSourceColumnToDataSourceColumnDtoConverter.convert(dataSourceColumn) >> dataSourceColumnDto

        when:
        final DataSetSeriesColumnDto dataSetSeriesColumnDto = dataSetSeriesColumnToDataSetSeriesColumnDtoConverter.convert(dataSetSeriesColumn)

        then:
        dataSetSeriesColumnDto != null
        dataSetSeriesColumnDto.getId() == dataSetSeriesColumn.getId()
        dataSetSeriesColumnDto.getName() == dataSetSeriesColumn.getName()
        dataSetSeriesColumnDto.getType() == dataSetSeriesColumn.getType()
        dataSetSeriesColumnDto.getDataSourceColumn() == dataSourceColumnDto

        1 * dataSourceColumnToDataSourceColumnDtoConverter.convert(dataSourceColumn) >> dataSourceColumnDto
        0 * _
    }
}