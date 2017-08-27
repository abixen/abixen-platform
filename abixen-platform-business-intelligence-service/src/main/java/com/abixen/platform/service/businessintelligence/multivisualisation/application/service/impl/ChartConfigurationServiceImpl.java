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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service.impl;

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetChartDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.*;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.ChartConfigurationRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataSourceColumnRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.ChartConfigurationService;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Slf4j
@Transactional
@Service
public class ChartConfigurationServiceImpl implements ChartConfigurationService {

    private final ChartConfigurationRepository chartConfigurationRepository;
    private final DataSourceService dataSourceService;
    private final DataSourceColumnRepository dataSourceColumnRepository;

    @Autowired
    public ChartConfigurationServiceImpl(ChartConfigurationRepository chartConfigurationRepository,
                                         DataSourceService dataSourceService,
                                         DataSourceColumnRepository dataSourceColumnRepository) {
        this.chartConfigurationRepository = chartConfigurationRepository;
        this.dataSourceService = dataSourceService;
        this.dataSourceColumnRepository = dataSourceColumnRepository;
    }

    @Override
    public ChartConfiguration buildChartConfiguration(ChartConfigurationForm chartConfigurationForm) {
        log.debug("buildChartConfiguration() - chartConfigurationForm: " + chartConfigurationForm);
        return new ChartConfigurationBuilder()
                .moduleId(chartConfigurationForm.getModuleId())
                .chartParameters(chartConfigurationForm.getChartType(), buildDataSetChart(chartConfigurationForm.getDataSetChart()))
                .dataParameters(chartConfigurationForm.getFilter(), dataSourceService.findDataSource(chartConfigurationForm.getDataSource().getId()))
                .axisNames(chartConfigurationForm.getAxisXName(), chartConfigurationForm.getAxisYName())
                .build();
    }


    @Override
    public ChartConfiguration createChartConfiguration(ChartConfigurationForm chartConfigurationForm) {
        log.debug("createChartConfiguration() - chartConfigurationForm: " + chartConfigurationForm);
        ChartConfiguration chartConfiguration = buildChartConfiguration(chartConfigurationForm);

        return createChartConfiguration(chartConfiguration);
    }

    @Override
    public ChartConfiguration updateChartConfiguration(ChartConfigurationForm chartConfigurationForm) {
        log.debug("updateChartConfiguration() - chartConfigurationForm: " + chartConfigurationForm);

        ChartConfiguration chartConfiguration = findChartConfigurationByModuleId(chartConfigurationForm.getModuleId());
        chartConfiguration.changeModuleId(chartConfigurationForm.getModuleId());
        chartConfiguration.changeAxisNames(chartConfigurationForm.getAxisXName(), chartConfigurationForm.getAxisYName());
        chartConfiguration.changeChartParameters(chartConfigurationForm.getChartType(), buildDataSetChart(chartConfigurationForm.getDataSetChart()));
        chartConfiguration.changeDataParameters(chartConfigurationForm.getFilter(), dataSourceService.findDataSource(chartConfigurationForm.getDataSource().getId()));

        return chartConfiguration;
    }

    @Override
    public ChartConfiguration findChartConfigurationByModuleId(Long moduleId) {
        log.debug("findChartConfigurationByModuleId() - moduleId: " + moduleId);

        return chartConfigurationRepository.findByModuleId(moduleId);
    }

    @Override
    public ChartConfiguration createChartConfiguration(ChartConfiguration chartConfiguration) {
        log.debug("createChartConfiguration() - chartConfiguration: " + chartConfiguration);
        chartConfiguration.getDataSetChart().getDataSetSeries().forEach(dataSetSeries -> {
            dataSetSeries.changeDataParameters(dataSetSeries.getFilter(), chartConfiguration.getDataSetChart());
        });

        return chartConfigurationRepository.save(chartConfiguration);
    }

    @Override
    public ChartConfiguration updateChartConfiguration(ChartConfiguration chartConfiguration) {
        log.debug("updateChartConfiguration() - chartConfiguration: " + chartConfiguration);
        chartConfiguration.getDataSetChart().getDataSetSeries().forEach(dataSetSeries -> {
            dataSetSeries.changeDataParameters(dataSetSeries.getFilter(), chartConfiguration.getDataSetChart());
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

    private DataSetChart buildDataSetChart(DataSetChartDto dataSetChartDto) {
        DataSetChart dataSetChart = new DataSetChartBuilder()
                .dataSetSeries(dataSetChartDto.getDataSetSeries().stream()
                        .map(buildDataSetSeries())
                        .collect(Collectors.toSet()))
                .domainSeries(buildDataSetSeriesColumn(dataSetChartDto.getDomainXSeriesColumn()),
                        ofNullable(dataSetChartDto.getDomainZSeriesColumn())
                        .map(this::buildDataSetSeriesColumn)
                        .orElse(null))
                .build();

        dataSetChart.getDataSetSeries().forEach(
                dataSetSeries -> dataSetSeries.changeDataParameters(dataSetSeries.getFilter(), dataSetChart)
        );

        return dataSetChart;
    }

    private Function<DataSetSeriesDto, DataSetSeries> buildDataSetSeries() {
        return dataSetSeriesDto -> new DataSetSeriesBuilder()
                .dataParameters(dataSetSeriesDto.getFilter(), null)
                .valueSeriesColumn(buildDataSetSeriesColumn(dataSetSeriesDto.getValueSeriesColumn()))
                .name(dataSetSeriesDto.getName())
                .build();
    }

    private DataSetSeriesColumn buildDataSetSeriesColumn(DataSetSeriesColumnDto dataSetSeriesColumnDto) {
        return new DataSetSeriesColumnBuilder()
                .name(dataSetSeriesColumnDto.getName())
                .column(dataSetSeriesColumnDto.getType(), dataSourceColumnRepository.findOne(dataSetSeriesColumnDto.getDataSourceColumn().getId()))
                .build();
    }
}
