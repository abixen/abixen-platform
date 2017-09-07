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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.impl;

import com.abixen.platform.common.infrastructure.annotation.PlatformDomainService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.*;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.ChartConfigurationRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.ChartConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional
@PlatformDomainService
public class ChartConfigurationServiceImpl implements ChartConfigurationService {

    private final ChartConfigurationRepository chartConfigurationRepository;

    @Autowired
    public ChartConfigurationServiceImpl(ChartConfigurationRepository chartConfigurationRepository) {
        this.chartConfigurationRepository = chartConfigurationRepository;
    }

    @Override
    public ChartConfiguration find(final Long moduleId) {
        log.debug("findDatabaseConnection() - moduleId: {}", moduleId);

        return chartConfigurationRepository.findByModuleId(moduleId);
    }

    @Override
    public ChartConfiguration create(final ChartConfiguration chartConfiguration) {
        log.debug("create() - chartConfiguration: {}", chartConfiguration);

        resolveReferenceInObject(chartConfiguration);

        return chartConfigurationRepository.save(chartConfiguration);
    }

    @Override
    public ChartConfiguration update(final ChartConfiguration chartConfiguration) {
        log.debug("update() - chartConfiguration: {}", chartConfiguration);

        resolveReferenceInObject(chartConfiguration);

        return chartConfigurationRepository.save(chartConfiguration);
    }

    @Override
    public void delete(final Long moduleId) {
        log.debug("deleteChartConfiguration - moduleId: {}", moduleId);
        final ChartConfiguration chartConfiguration = chartConfigurationRepository.findByModuleId(moduleId);
        if (chartConfiguration != null) {
            chartConfigurationRepository.delete(chartConfiguration);
        }
    }

    private void resolveReferenceInObject(ChartConfiguration chartConfiguration) {
        chartConfiguration.getDataSetChart().getDataSetSeries().forEach(dataSetSeries -> {
            dataSetSeries.changeDataParameters(dataSetSeries.getFilter(), chartConfiguration.getDataSetChart());
        });
    }
}
