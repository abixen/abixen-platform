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

package com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.web.facade.impl;

import com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.web.facade.converter.ChartConfigurationToChartConfigurationDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.ChartConfigurationDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.web.facade.ChartConfigurationFacade;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.ChartConfiguration;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.ChartConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ChartConfigurationFacadeImpl implements ChartConfigurationFacade {

    private final ChartConfigurationService chartConfigurationService;
    private final ChartConfigurationToChartConfigurationDtoConverter chartConfigurationToChartConfigurationDtoConverter;

    @Autowired
    public ChartConfigurationFacadeImpl(ChartConfigurationService chartConfigurationService, ChartConfigurationToChartConfigurationDtoConverter chartConfigurationToChartConfigurationDtoConverter) {
        this.chartConfigurationService = chartConfigurationService;
        this.chartConfigurationToChartConfigurationDtoConverter = chartConfigurationToChartConfigurationDtoConverter;
    }

    @Override
    public ChartConfigurationDto createChartConfiguration(ChartConfigurationForm chartConfigurationForm) {
        ChartConfiguration chartConfiguration = chartConfigurationService.create(chartConfigurationForm);
        ChartConfigurationDto chartConfigurationDto = chartConfigurationToChartConfigurationDtoConverter.convert(chartConfiguration);
        return chartConfigurationDto;
    }

    @Override
    public ChartConfigurationDto updateChartConfiguration(ChartConfigurationForm chartConfigurationForm) {
        ChartConfiguration chartConfiguration = chartConfigurationService.update(chartConfigurationForm);
        ChartConfigurationDto chartConfigurationDto = chartConfigurationToChartConfigurationDtoConverter.convert(chartConfiguration);
        return chartConfigurationDto;
    }

    @Override
    public ChartConfigurationDto findChartConfiguration(Long moduleId) {
        ChartConfiguration chartConfiguration = chartConfigurationService.find(moduleId);
        ChartConfigurationDto chartConfigurationDto = chartConfigurationToChartConfigurationDtoConverter.convert(chartConfiguration);
        return chartConfigurationDto;
    }
}
