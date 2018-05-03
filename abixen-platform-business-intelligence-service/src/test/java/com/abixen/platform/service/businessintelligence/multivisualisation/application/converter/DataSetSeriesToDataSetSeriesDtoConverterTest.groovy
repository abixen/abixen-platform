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
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataSet
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataSetSeries
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataSetSeriesColumn
import spock.lang.Specification

class DataSetSeriesToDataSetSeriesDtoConverterTest extends Specification {

    private DataSetSeriesColumnToDataSetSeriesColumnDtoConverter dataSetSeriesColumnToDataSetSeriesColumnDtoConverter
    private DataSetSeriesToDataSetSeriesDtoConverter dataSetSeriesToDataSetSeriesDtoConverter

    void setup() {
        dataSetSeriesColumnToDataSetSeriesColumnDtoConverter = Mock()
        dataSetSeriesToDataSetSeriesDtoConverter = new DataSetSeriesToDataSetSeriesDtoConverter(dataSetSeriesColumnToDataSetSeriesColumnDtoConverter)
    }

    void "should return null when DataSetSeries is null"() {
        given:
        final DataSetSeries dataSetSeries = null

        when:
        final DataSetSeriesDto dataSetSeriesDto = dataSetSeriesToDataSetSeriesDtoConverter.convert(dataSetSeries)

        then:
        dataSetSeriesDto == null
    }

    void "should convert DataSetSeries to DataSetSeriesDto"() {
        given:
        final DataSet dataSet = [] as DataSet

        final DataSetSeriesColumn dataSetSeriesColumn = [] as DataSetSeriesColumn

        final DataSetSeries dataSetSeries = DataSetSeries.builder()
                .name("name")
                .dataParameters("filter", dataSet)
                .valueSeriesColumn(dataSetSeriesColumn)
                .build()

        final DataSetSeriesColumnDto dataSetSeriesColumnDto = [] as DataSetSeriesColumnDto

        dataSetSeriesColumnToDataSetSeriesColumnDtoConverter.convert(dataSetSeriesColumn) >> dataSetSeriesColumnDto

        when:
        final DataSetSeriesDto dataSetSeriesDto = dataSetSeriesToDataSetSeriesDtoConverter.convert(dataSetSeries)

        then:
        dataSetSeriesDto != null
        dataSetSeriesDto.getId() == dataSetSeries.getId()
        dataSetSeriesDto.getName() == dataSetSeries.getName()
        dataSetSeriesDto.getFilter() == dataSetSeries.getFilter()
        dataSetSeriesDto.getValueSeriesColumn() == dataSetSeriesColumnDto

        1 * dataSetSeriesColumnToDataSetSeriesColumnDtoConverter.convert(dataSetSeriesColumn) >> dataSetSeriesColumnDto
        0 * _
    }
}
