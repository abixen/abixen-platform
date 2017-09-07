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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.impl

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.ChartType
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.ChartConfiguration
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.ChartConfigurationBuilder
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.DataSetChart
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.ChartConfigurationRepository
import spock.lang.Specification

class ChartConfigurationServiceImplTest extends Specification {

    private ChartConfigurationRepository chartConfigurationRepository
    private ChartConfigurationServiceImpl chartConfigurationService

    void setup() {
        chartConfigurationRepository = Mock()
        chartConfigurationService = new ChartConfigurationServiceImpl(chartConfigurationRepository)

    }

    def "should find ChartConfiguration"() {
        given:
        final Long moduleId = 1L

        final ChartConfiguration chartConfiguration = [] as ChartConfiguration

        chartConfigurationRepository.findByModuleId(moduleId) >> chartConfiguration

        when:
        final ChartConfiguration foundChartConfiguration = chartConfigurationService.find(moduleId)

        then:
        foundChartConfiguration != null
        foundChartConfiguration == chartConfiguration

        1 * chartConfigurationRepository.findByModuleId(moduleId) >> chartConfiguration
        0 * _
    }

    def "should create ChartConfiguration"() {
        given:
        final DataSource dataSource = [] as DataSource
        final DataSetChart dataSetChart = [] as DataSetChart

        final ChartConfiguration chartConfiguration = new ChartConfigurationBuilder()
                .axisNames("axisXName", "axisYName")
                .moduleId(1L)
                .dataParameters("filter", dataSource)
                .chartParameters(ChartType.CUMULATIVE_LINE, dataSetChart)
                .build()

        chartConfigurationRepository.save(chartConfiguration) >> chartConfiguration

        when:
        final ChartConfiguration createdChartConfiguration = chartConfigurationService.create(chartConfiguration)

        then:
        createdChartConfiguration != null
        createdChartConfiguration.getId() == chartConfiguration.getId()
        createdChartConfiguration.getModuleId() == chartConfiguration.getModuleId()
        createdChartConfiguration.getAxisXName() == chartConfiguration.getAxisXName()
        createdChartConfiguration.getAxisYName() == chartConfiguration.getAxisYName()
        createdChartConfiguration.getChartType() == chartConfiguration.getChartType()
        createdChartConfiguration.getFilter() == chartConfiguration.getFilter()
        createdChartConfiguration.getDataSetChart() == chartConfiguration.getDataSetChart()
        createdChartConfiguration.getDataSource() == chartConfiguration.getDataSource()

        1 * chartConfigurationRepository.save(chartConfiguration) >> chartConfiguration
        0 * _
    }

    def "should update ChartConfiguration"() {
        given:
        final DataSource dataSource = [] as DataSource
        final DataSetChart dataSetChart = [] as DataSetChart

        final ChartConfiguration chartConfiguration = new ChartConfigurationBuilder()
                .axisNames("axisXName", "axisYName")
                .moduleId(1L)
                .dataParameters("filter", dataSource)
                .chartParameters(ChartType.CUMULATIVE_LINE, dataSetChart)
                .build()

        chartConfigurationRepository.save(chartConfiguration) >> chartConfiguration

        when:
        final ChartConfiguration createdChartConfiguration = chartConfigurationService.update(chartConfiguration)

        then:
        createdChartConfiguration != null
        createdChartConfiguration.getId() == chartConfiguration.getId()
        createdChartConfiguration.getModuleId() == chartConfiguration.getModuleId()
        createdChartConfiguration.getAxisXName() == chartConfiguration.getAxisXName()
        createdChartConfiguration.getAxisYName() == chartConfiguration.getAxisYName()
        createdChartConfiguration.getChartType() == chartConfiguration.getChartType()
        createdChartConfiguration.getFilter() == chartConfiguration.getFilter()
        createdChartConfiguration.getDataSetChart() == chartConfiguration.getDataSetChart()
        createdChartConfiguration.getDataSource() == chartConfiguration.getDataSource()

        1 * chartConfigurationRepository.save(chartConfiguration) >> chartConfiguration
        0 * _
    }

    def "should delete ChartConfiguration when ChartConfiguration exist"() {
        given:
        final Long moduleId = 1L

        final ChartConfiguration chartConfiguration = [] as ChartConfiguration

        chartConfigurationRepository.findByModuleId(moduleId) >> chartConfiguration

        when:
        chartConfigurationService.delete(moduleId)

        then:
        1 * chartConfigurationRepository.findByModuleId(moduleId) >> chartConfiguration
        1 * chartConfigurationRepository.delete(chartConfiguration)
        0 * _
    }

    def "shouldn't delete ChartConfiguration when ChartConfiguration not exist"() {
        given:
        final Long moduleId = 1L

        chartConfigurationRepository.findByModuleId(moduleId) >> null

        when:
        chartConfigurationService.delete(moduleId)

        then:
        1 * chartConfigurationRepository.findByModuleId(moduleId) >> null
        0 * _
    }
}
