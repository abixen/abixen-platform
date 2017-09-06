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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetChartDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesColumnDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.DataSetChart
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.DataSetChartBuilder
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.DataSetSeries
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.DataSetSeriesColumn
import spock.lang.Specification

class DataSetChartToDataSetChartDtoConverterTest extends Specification {

    private DataSetSeriesColumnToDataSetSeriesColumnDtoConverter dataSetSeriesColumnToDataSetSeriesColumnDtoConverter
    private DataSetSeriesToDataSetSeriesDtoConverter dataSetSeriesToDataSetSeriesDtoConverter
    private DataSetChartToDataSetChartDtoConverter dataSetChartToDataSetChartDtoConverter

    void setup() {
        dataSetSeriesColumnToDataSetSeriesColumnDtoConverter = Mock()
        dataSetSeriesToDataSetSeriesDtoConverter = Mock()
        dataSetChartToDataSetChartDtoConverter = new DataSetChartToDataSetChartDtoConverter(dataSetSeriesColumnToDataSetSeriesColumnDtoConverter, dataSetSeriesToDataSetSeriesDtoConverter)
    }

    void "should return null when DataSetChart is null"() {
        given:
        final DataSetChart dataSetChart = null

        when:
        final DataSetChartDto dataSetChartDto = dataSetChartToDataSetChartDtoConverter.convert(dataSetChart)

        then:
        dataSetChartDto == null
    }

    void "should convert DataSetChart to DataSetChartDto"() {
        given:
        final DataSetSeries dataSetSeries = [] as DataSetSeries
        final Set<DataSetSeries> dataSetSeriesSet = Collections.singleton(dataSetSeries)

        final DataSetSeriesColumn domainXSeriesColumn = [] as DataSetSeriesColumn
        final DataSetSeriesColumn domainZSeriesColumn = [] as DataSetSeriesColumn

        final DataSetChart dataSetChart = new DataSetChartBuilder()
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
        final DataSetChartDto dataSetChartDto = dataSetChartToDataSetChartDtoConverter.convert(dataSetChart)

        then:
        dataSetChartDto != null
        dataSetChartDto.getId() == dataSetChart.getId()
        dataSetChartDto.getDataSetSeries() == dataSetSeriesDtos
        dataSetChartDto.getDomainXSeriesColumn() == domainXSeriesColumnDto
        dataSetChartDto.getDomainZSeriesColumn() == domainZSeriesColumnDto

        1 * dataSetSeriesToDataSetSeriesDtoConverter.convertToSet(dataSetSeriesSet) >> dataSetSeriesDtos
        1 * dataSetSeriesColumnToDataSetSeriesColumnDtoConverter.convert(domainXSeriesColumn) >> domainXSeriesColumnDto
        1 * dataSetSeriesColumnToDataSetSeriesColumnDtoConverter.convert(domainZSeriesColumn) >> domainZSeriesColumnDto
        0 * _
    }
}
