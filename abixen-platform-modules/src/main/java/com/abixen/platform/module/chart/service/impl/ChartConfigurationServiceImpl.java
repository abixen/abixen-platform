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

package com.abixen.platform.module.chart.service.impl;

import com.abixen.platform.module.chart.form.ChartConfigurationForm;
import com.abixen.platform.module.chart.model.impl.ChartConfiguration;
import com.abixen.platform.module.chart.repository.ChartConfigurationRepository;
import com.abixen.platform.module.chart.service.ChartConfigurationDomainBuilderService;
import com.abixen.platform.module.chart.service.ChartConfigurationService;
import com.abixen.platform.module.chart.service.DatabaseDataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Transactional
@Service
public class ChartConfigurationServiceImpl implements ChartConfigurationService {

    private final Logger log = LoggerFactory.getLogger(ChartConfigurationServiceImpl.class);

    @Resource
    private ChartConfigurationRepository chartConfigurationRepository;

    @Autowired
    private ChartConfigurationDomainBuilderService chartConfigurationDomainBuilderService;

    @Autowired
    private DatabaseDataSourceService databaseDataSourceService;

    @Override
    public ChartConfiguration buildChartConfiguration(ChartConfigurationForm chartConfigurationForm) {
        log.debug("buildChartConfiguration() - chartConfigurationForm: " + chartConfigurationForm);
        return chartConfigurationDomainBuilderService.newChartConfigurationBuilderInstance()
                .basic(chartConfigurationForm.getModuleId(), chartConfigurationForm.getChartType())
                .data(chartConfigurationForm.getDataSetChart(), databaseDataSourceService.findDataSource(chartConfigurationForm.getDataSource().getId()))
                .axis(chartConfigurationForm.getAxisXName(), chartConfigurationForm.getAxisYName())
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
        chartConfiguration.setChartType(chartConfigurationForm.getChartType());

        return new ChartConfigurationForm(updateChartConfiguration(chartConfiguration));
    }

    @Override
    public ChartConfiguration findChartConfigurationByModuleId(Long moduleId) {
        return chartConfigurationRepository.findByModuleId(moduleId);
    }

    @Override
    public ChartConfiguration createChartConfiguration(ChartConfiguration chartConfiguration) {
        log.debug("createChartConfiguration() - chartConfiguration: " + chartConfiguration);
        chartConfiguration.getDataSetChart().getDataSetSeries().forEach(dataSetSeries -> {
            dataSetSeries.getSeriesColumns().forEach(dataSetSeriesColumn -> {
            });
            dataSetSeries.setDataSet(chartConfiguration.getDataSetChart());
        });
        ChartConfiguration createdChartConfiguration = chartConfigurationRepository.save(chartConfiguration);
        return createdChartConfiguration;
    }

    @Override
    public ChartConfiguration updateChartConfiguration(ChartConfiguration chartConfiguration) {
        log.debug("updateChartConfiguration() - chartConfiguration: " + chartConfiguration);
        return chartConfigurationRepository.save(chartConfiguration);
    }
}
