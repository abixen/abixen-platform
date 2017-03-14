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

package com.abixen.platform.service.businessintelligence.multivisualisation.service.impl;

import com.abixen.platform.service.businessintelligence.multivisualisation.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.ChartConfiguration;
import com.abixen.platform.service.businessintelligence.multivisualisation.repository.ChartConfigurationRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.repository.DataSourceColumnRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.ChartConfigurationDomainBuilderService;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.ChartConfigurationService;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class ChartConfigurationServiceImpl implements ChartConfigurationService {

    private final ChartConfigurationRepository chartConfigurationRepository;
    private final ChartConfigurationDomainBuilderService chartConfigurationDomainBuilderService;
    private final DataSourceService dataSourceService;
    private final DataSourceColumnRepository dataSourceColumnRepository;

    @Autowired
    public ChartConfigurationServiceImpl(ChartConfigurationRepository chartConfigurationRepository,
                                         ChartConfigurationDomainBuilderService chartConfigurationDomainBuilderService,
                                         DataSourceService dataSourceService,
                                         DataSourceColumnRepository dataSourceColumnRepository) {
        this.chartConfigurationRepository = chartConfigurationRepository;
        this.chartConfigurationDomainBuilderService = chartConfigurationDomainBuilderService;
        this.dataSourceService = dataSourceService;
        this.dataSourceColumnRepository = dataSourceColumnRepository;
    }

    @Override
    public ChartConfiguration buildChartConfiguration(ChartConfigurationForm chartConfigurationForm) {
        log.debug("buildChartConfiguration() - chartConfigurationForm: " + chartConfigurationForm);
        return chartConfigurationDomainBuilderService.newChartConfigurationBuilderInstance()
                .basic(chartConfigurationForm.getModuleId(), chartConfigurationForm.getChartType())
                .data(chartConfigurationForm.getDataSetChart(), dataSourceService.findDataSource(chartConfigurationForm.getDataSource().getId()), dataSourceColumnRepository)
                .axis(chartConfigurationForm.getAxisXName(), chartConfigurationForm.getAxisYName())
                .filter(chartConfigurationForm.getFilter())
                .build();
    }

    @Override
    public ChartConfigurationForm createChartConfiguration(ChartConfigurationForm chartConfigurationForm) {
        ChartConfiguration chartConfiguration = buildChartConfiguration(chartConfigurationForm);
        return new ChartConfigurationForm(createChartConfiguration(chartConfiguration));
    }

    @Override
    public ChartConfigurationForm updateChartConfiguration(ChartConfigurationForm chartConfigurationForm) {
        log.debug("updateChartConfiguration() - chartConfigurationForm: " + chartConfigurationForm);

        ChartConfiguration chartConfiguration = findChartConfigurationByModuleId(chartConfigurationForm.getModuleId());
        ChartConfiguration chartConfigurationUpdated = chartConfigurationDomainBuilderService.newChartConfigurationBuilderForUpdateInstance(chartConfiguration)
                .basic(chartConfigurationForm.getModuleId(), chartConfigurationForm.getChartType())
                .data(chartConfigurationForm.getDataSetChart(), dataSourceService.findDataSource(chartConfigurationForm.getDataSource().getId()), dataSourceColumnRepository)
                .axis(chartConfigurationForm.getAxisXName(), chartConfigurationForm.getAxisYName())
                .filter(chartConfigurationForm.getFilter())
                .build();

        return new ChartConfigurationForm(updateChartConfiguration(chartConfigurationUpdated));
    }

    @Override
    public ChartConfiguration findChartConfigurationByModuleId(Long moduleId) {
        return chartConfigurationRepository.findByModuleId(moduleId);
    }

    @Override
    public ChartConfiguration createChartConfiguration(ChartConfiguration chartConfiguration) {
        log.debug("createChartConfiguration() - chartConfiguration: " + chartConfiguration);
        chartConfiguration.getDataSetChart().getDataSetSeries().forEach(dataSetSeries -> {
            dataSetSeries.setDataSet(chartConfiguration.getDataSetChart());
        });
        return chartConfigurationRepository.save(chartConfiguration);
    }

    @Override
    public ChartConfiguration updateChartConfiguration(ChartConfiguration chartConfiguration) {
        log.debug("updateChartConfiguration() - chartConfiguration: " + chartConfiguration);
        chartConfiguration.getDataSetChart().getDataSetSeries().forEach(dataSetSeries -> {
            dataSetSeries.setDataSet(chartConfiguration.getDataSetChart());
        });
        return chartConfigurationRepository.save(chartConfiguration);
    }

    @Override
    public void removeChartConfiguration(Long moduleId) {
        log.debug("removeChartConfiguration - moduleId", moduleId);
        ChartConfiguration chartConfiguration = chartConfigurationRepository.findByModuleId(moduleId);
        if (chartConfiguration != null) {
            chartConfigurationRepository.delete(chartConfiguration);
        }
    }
}
