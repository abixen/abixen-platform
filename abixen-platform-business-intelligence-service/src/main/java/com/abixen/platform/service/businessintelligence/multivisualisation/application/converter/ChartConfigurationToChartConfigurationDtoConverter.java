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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.converter;

import com.abixen.platform.common.application.converter.AbstractConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.ChartConfigurationDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.ChartConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChartConfigurationToChartConfigurationDtoConverter extends AbstractConverter<ChartConfiguration, ChartConfigurationDto> {

    private final DataSetToDataSetDtoConverter dataSetToDataSetDtoConverter;
    private final DataSourceToDataSourceDtoConverter dataSourceToDataSourceDtoConverter;

    @Autowired
    public ChartConfigurationToChartConfigurationDtoConverter(DataSetToDataSetDtoConverter dataSetToDataSetDtoConverter,
                                                              DataSourceToDataSourceDtoConverter dataSourceToDataSourceDtoConverter) {
        this.dataSetToDataSetDtoConverter = dataSetToDataSetDtoConverter;
        this.dataSourceToDataSourceDtoConverter = dataSourceToDataSourceDtoConverter;
    }

    @Override
    public ChartConfigurationDto convert(ChartConfiguration chartConfiguration, Map<String, Object> parameters) {
        if (chartConfiguration == null) {
            return null;
        }

        ChartConfigurationDto chartConfigurationDto = new ChartConfigurationDto();

        chartConfigurationDto.setId(chartConfiguration.getId())
                .setModuleId(chartConfiguration.getModuleId())
                .setAxisXName(chartConfiguration.getAxisXName())
                .setAxisYName(chartConfiguration.getAxisYName())
                .setFilter(chartConfiguration.getFilter())
                .setChartType(chartConfiguration.getChartType())
                .setDataSet(dataSetToDataSetDtoConverter.convert(chartConfiguration.getDataSet()))
                .setDataSource(dataSourceToDataSourceDtoConverter.convert(chartConfiguration.getDataSource()));

        return chartConfigurationDto;
    }
}
