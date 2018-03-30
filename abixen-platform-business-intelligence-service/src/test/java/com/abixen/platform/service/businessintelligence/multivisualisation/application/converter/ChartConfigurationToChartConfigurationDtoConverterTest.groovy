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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.ChartConfigurationDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetChartDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.ChartType
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.ChartConfiguration
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.ChartConfigurationBuilder
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.DataSetChart
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource
import spock.lang.Specification

class ChartConfigurationToChartConfigurationDtoConverterTest extends Specification {

    private DataSetChartToDataSetChartDtoConverter dataSetChartToDataSetChartDtoConverter;
    private DataSourceToDataSourceDtoConverter dataSourceToDataSourceDtoConverter;
    private ChartConfigurationToChartConfigurationDtoConverter chartConfigurationToChartConfigurationDtoConverter;

    void setup() {
        dataSetChartToDataSetChartDtoConverter = Mock()
        dataSourceToDataSourceDtoConverter = Mock()
        chartConfigurationToChartConfigurationDtoConverter = new ChartConfigurationToChartConfigurationDtoConverter(dataSetChartToDataSetChartDtoConverter, dataSourceToDataSourceDtoConverter)
    }

    void "should return null when ChartConfiguration is null"() {
        given:
        final ChartConfiguration chartConfiguration = null

        when:
        final ChartConfigurationDto chartConfigurationDto = chartConfigurationToChartConfigurationDtoConverter.convert(chartConfiguration)

        then:
        chartConfigurationDto == null
    }

    void "should convert ChartConfiguration to ChartConfigurationDto"() {
        given:
        final DataSource dataSource = [] as DataSource
        final DataSetChart dataSetChart = [] as DataSetChart

        final ChartConfiguration chartConfiguration = new ChartConfigurationBuilder()
            .axisNames("axisXName", "axisYName")
            .moduleId(1L)
            .dataParameters("filter", dataSource)
            .chartParameters(ChartType.CUMULATIVE_LINE, dataSetChart)
            .build()

        final DataSourceDto dataSourceDto = [] as DataSourceDto
        final DataSetChartDto dataSetChartDto = [] as DataSetChartDto

        dataSourceToDataSourceDtoConverter.convert(dataSource) >> dataSourceDto
        dataSetChartToDataSetChartDtoConverter.convert(dataSetChart) >> dataSetChartDto

        when:
        final ChartConfigurationDto chartConfigurationDto = chartConfigurationToChartConfigurationDtoConverter.convert(chartConfiguration)

        then:
        chartConfigurationDto != null
        chartConfigurationDto.getId() == chartConfiguration.getId()
        chartConfigurationDto.getModuleId() == chartConfiguration.getModuleId()
        chartConfigurationDto.getAxisXName() == chartConfiguration.getAxisXName()
        chartConfigurationDto.getAxisYName() == chartConfiguration.getAxisYName()
        chartConfigurationDto.getChartType() == chartConfiguration.getChartType()
        chartConfigurationDto.getFilter() == chartConfiguration.getFilter()
        chartConfigurationDto.getDataSetChart() == dataSetChartDto
        chartConfigurationDto.getDataSource() == dataSourceDto

        1 * dataSourceToDataSourceDtoConverter.convert(dataSource) >> dataSourceDto
        1 * dataSetChartToDataSetChartDtoConverter.convert(dataSetChart) >> dataSetChartDto
        0 * _
    }
}
