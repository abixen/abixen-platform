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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesColumnDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataSet
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataSetSeries
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataSetSeriesColumn
import spock.lang.Specification

class DataSetToDataSetDtoConverterTest extends Specification {

    private DataSetSeriesColumnToDataSetSeriesColumnDtoConverter dataSetSeriesColumnToDataSetSeriesColumnDtoConverter
    private DataSetSeriesToDataSetSeriesDtoConverter dataSetSeriesToDataSetSeriesDtoConverter
    private DataSetToDataSetDtoConverter dataSetToDataSetDtoConverter

    void setup() {
        dataSetSeriesColumnToDataSetSeriesColumnDtoConverter = Mock()
        dataSetSeriesToDataSetSeriesDtoConverter = Mock()
        dataSetToDataSetDtoConverter = new DataSetToDataSetDtoConverter(dataSetSeriesColumnToDataSetSeriesColumnDtoConverter, dataSetSeriesToDataSetSeriesDtoConverter)
    }

    void "should return null when DataSet is null"() {
        given:
        final DataSet dataSet = null

        when:
        final DataSetDto dataSetDto = dataSetToDataSetDtoConverter.convert(dataSet)

        then:
        dataSetDto == null
    }

    void "should convert DataSet to DataSetDto"() {
        given:
        final DataSetSeries dataSetSeries = [] as DataSetSeries
        final Set<DataSetSeries> dataSetSeriesSet = Collections.singleton(dataSetSeries)

        final DataSetSeriesColumn domainXSeriesColumn = [] as DataSetSeriesColumn
        final DataSetSeriesColumn domainZSeriesColumn = [] as DataSetSeriesColumn

        final DataSet dataSet = DataSet.builder()
                .dataSetSeries(dataSetSeriesSet)
                .domainSeries(domainXSeriesColumn, domainZSeriesColumn)
                .build()

        final DataSetSeriesDto dataSetSeriesDto = [] as DataSetSeriesDto
        final Set<DataSetSeriesDto> dataSetSeriesDtos = Collections.singleton(dataSetSeriesDto)

        final DataSetSeriesColumnDto domainXSeriesColumnDto = [] as DataSetSeriesColumnDto
        final DataSetSeriesColumnDto domainZSeriesColumnDto = [] as DataSetSeriesColumnDto

        dataSetSeriesToDataSetSeriesDtoConverter.convertToSet(dataSetSeriesSet) >> dataSetSeriesDtos
        dataSetSeriesColumnToDataSetSeriesColumnDtoConverter.convert(domainXSeriesColumn) >> domainXSeriesColumnDto
        dataSetSeriesColumnToDataSetSeriesColumnDtoConverter.convert(domainZSeriesColumn) >> domainZSeriesColumnDto

        when:
        final DataSetDto dataSetDto = dataSetToDataSetDtoConverter.convert(dataSet)

        then:
        dataSetDto != null
        dataSetDto.getId() == dataSet.getId()
        dataSetDto.getDataSetSeries() == dataSetSeriesDtos
        dataSetDto.getDomainXSeriesColumn() == domainXSeriesColumnDto
        dataSetDto.getDomainZSeriesColumn() == domainZSeriesColumnDto

        1 * dataSetSeriesToDataSetSeriesDtoConverter.convertToSet(dataSetSeriesSet) >> dataSetSeriesDtos
        1 * dataSetSeriesColumnToDataSetSeriesColumnDtoConverter.convert(domainXSeriesColumn) >> domainXSeriesColumnDto
        1 * dataSetSeriesColumnToDataSetSeriesColumnDtoConverter.convert(domainZSeriesColumn) >> domainZSeriesColumnDto
        0 * _
    }

}