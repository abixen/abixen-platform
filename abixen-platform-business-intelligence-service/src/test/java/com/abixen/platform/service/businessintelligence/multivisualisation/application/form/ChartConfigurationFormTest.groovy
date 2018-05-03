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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.form

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.ChartConfigurationDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.ChartType
import spock.lang.Specification

class ChartConfigurationFormTest extends Specification {

    void "should build ChartConfigurationForm from ChartConfigurationDto"() {
        given:
        final DataSourceDto dataSource = [] as DataSourceDto
        final DataSetDto dataSet = DataSetDto.builder().build()

        final ChartConfigurationDto chartConfigurationDto = new ChartConfigurationDto()
                .setAxisXName("axisXName")
                .setAxisYName("axisYName")
                .setChartType(ChartType.CUMULATIVE_LINE)
                .setDataSet(dataSet)
                .setDataSource(dataSource)
                .setFilter("filter")
                .setId(1L)
                .setModuleId(2L)

        when:
        final ChartConfigurationForm chartConfigurationForm = new ChartConfigurationForm(chartConfigurationDto)

        then:
        chartConfigurationForm != null
        chartConfigurationForm.getId() == chartConfigurationDto.getId()
        chartConfigurationForm.getModuleId() == chartConfigurationDto.getModuleId()
        chartConfigurationForm.getFilter() == chartConfigurationDto.getFilter()
        chartConfigurationForm.getAxisXName() == chartConfigurationDto.getAxisXName()
        chartConfigurationForm.getAxisYName() == chartConfigurationDto.getAxisYName()
        chartConfigurationForm.getChartType() == chartConfigurationDto.getChartType()
        chartConfigurationForm.getDataSet() == chartConfigurationDto.getDataSet()
        chartConfigurationForm.getDataSource() == chartConfigurationDto.getDataSource()

        0 * _
    }
}
