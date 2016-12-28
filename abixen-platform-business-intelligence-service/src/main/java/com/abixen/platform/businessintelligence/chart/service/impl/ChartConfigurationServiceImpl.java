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

package com.abixen.platform.businessintelligence.chart.service.impl;

import com.abixen.platform.businessintelligence.chart.form.ChartConfigurationForm;
import com.abixen.platform.businessintelligence.chart.model.impl.ChartConfiguration;
import com.abixen.platform.businessintelligence.chart.repository.ChartConfigurationRepository;
import com.abixen.platform.businessintelligence.chart.repository.DataSourceColumnRepository;
import com.abixen.platform.businessintelligence.chart.service.ChartConfigurationDomainBuilderService;
import com.abixen.platform.businessintelligence.chart.service.ChartConfigurationService;
import com.abixen.platform.businessintelligence.chart.service.DatabaseDataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Slf4j
@Transactional
@Service
public class ChartConfigurationServiceImpl implements ChartConfigurationService {

    @Resource
    private ChartConfigurationRepository chartConfigurationRepository;

    @Autowired
    private ChartConfigurationDomainBuilderService chartConfigurationDomainBuilderService;

    @Autowired
    private DatabaseDataSourceService databaseDataSourceService;

    @Autowired
    private DataSourceColumnRepository dataSourceColumnRepository;

    @Override
    public ChartConfiguration buildChartConfiguration(ChartConfigurationForm chartConfigurationForm) {
        log.debug("buildChartConfiguration() - chartConfigurationForm: " + chartConfigurationForm);
        return chartConfigurationDomainBuilderService.newChartConfigurationBuilderInstance()
                .basic(chartConfigurationForm.getModuleId(), chartConfigurationForm.getChartType())
                .data(chartConfigurationForm.getDataSetChart(), databaseDataSourceService.findDataSource(chartConfigurationForm.getDataSource().getId()), dataSourceColumnRepository)
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
        ChartConfiguration chartConfigurationUpdated = chartConfigurationDomainBuilderService.newChartConfigurationBuilderForUpdateInstance(chartConfiguration)
                .basic(chartConfigurationForm.getModuleId(), chartConfigurationForm.getChartType())
                .data(chartConfigurationForm.getDataSetChart(), databaseDataSourceService.findDataSource(chartConfigurationForm.getDataSource().getId()), dataSourceColumnRepository)
                .axis(chartConfigurationForm.getAxisXName(), chartConfigurationForm.getAxisYName())
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
        throw new NotImplementedException("Method removeChartConfiguration(Long moduleId) is not implemented yet!");
    }
}
