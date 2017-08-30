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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.ChartConfigurationDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetChartDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.ChartConfigurationManagementService;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DataSourceService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.*;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataSourceColumnRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.ChartConfigurationService;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.converter.ChartConfigurationToChartConfigurationDtoConverter;
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
public class ChartConfigurationManagementServiceImpl implements ChartConfigurationManagementService {

    private final DataSourceService dataSourceService;
    private final ChartConfigurationService chartConfigurationService;
    private final DataSourceColumnRepository dataSourceColumnRepository;
    private final ChartConfigurationToChartConfigurationDtoConverter chartConfigurationToChartConfigurationDtoConverter;


    @Autowired
    public ChartConfigurationManagementServiceImpl(ChartConfigurationService chartConfigurationService,
                                                   DataSourceService dataSourceService,
                                                   DataSourceColumnRepository dataSourceColumnRepository,
                                                   ChartConfigurationToChartConfigurationDtoConverter chartConfigurationToChartConfigurationDtoConverter) {
        this.dataSourceColumnRepository = dataSourceColumnRepository;
        this.dataSourceService = dataSourceService;
        this.chartConfigurationService = chartConfigurationService;
        this.chartConfigurationToChartConfigurationDtoConverter = chartConfigurationToChartConfigurationDtoConverter;
    }

    @Override
    public ChartConfigurationDto findChartConfiguration(final Long moduleId) {
        log.debug("findChartConfiguration() - moduleId: {}", moduleId);

        final ChartConfiguration chartConfiguration = chartConfigurationService.find(moduleId);

        return chartConfigurationToChartConfigurationDtoConverter.convert(chartConfiguration);
    }

    @Override
    public ChartConfigurationDto createChartConfiguration(final ChartConfigurationForm chartConfigurationForm) {
        log.debug("createChartConfiguration() - chartConfigurationForm: {}", chartConfigurationForm);

        ChartConfiguration chartConfiguration = build(chartConfigurationForm);
        final ChartConfiguration updatedChartConfiguration = chartConfigurationService.create(chartConfiguration);

        return chartConfigurationToChartConfigurationDtoConverter.convert(updatedChartConfiguration);
    }


    @Override
    public ChartConfigurationDto updateChartConfiguration(final ChartConfigurationForm chartConfigurationForm) {
        log.debug("createChartConfiguration() - chartConfigurationForm: {}", chartConfigurationForm);

        final ChartConfiguration chartConfiguration = chartConfigurationService.find(chartConfigurationForm.getModuleId());
        chartConfiguration.changeModuleId(chartConfigurationForm.getModuleId());
        chartConfiguration.changeAxisNames(chartConfigurationForm.getAxisXName(), chartConfigurationForm.getAxisYName());
        chartConfiguration.changeChartParameters(chartConfigurationForm.getChartType(), buildDataSetChart(chartConfigurationForm.getDataSetChart()));
        chartConfiguration.changeDataParameters(chartConfigurationForm.getFilter(), dataSourceService.find(chartConfigurationForm.getDataSource().getId()));

        final ChartConfiguration createdChartConfiguration = chartConfigurationService.update(chartConfiguration);

        return chartConfigurationToChartConfigurationDtoConverter.convert(createdChartConfiguration);
    }

    @Override
    public void deleteChartConfiguration(final Long moduleId) {
        chartConfigurationService.delete(moduleId);
    }

    public ChartConfiguration build(final ChartConfigurationForm chartConfigurationForm) {
        log.debug("build() - chartConfigurationForm: {}", chartConfigurationForm);
        return new ChartConfigurationBuilder()
                .moduleId(chartConfigurationForm.getModuleId())
                .chartParameters(chartConfigurationForm.getChartType(), buildDataSetChart(chartConfigurationForm.getDataSetChart()))
                .dataParameters(chartConfigurationForm.getFilter(), dataSourceService.find(chartConfigurationForm.getDataSource().getId()))
                .axisNames(chartConfigurationForm.getAxisXName(), chartConfigurationForm.getAxisYName())
                .build();
    }

    private DataSetChart buildDataSetChart(final DataSetChartDto dataSetChartDto) {
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

    private DataSetSeriesColumn buildDataSetSeriesColumn(final DataSetSeriesColumnDto dataSetSeriesColumnDto) {
        return new DataSetSeriesColumnBuilder()
                .name(dataSetSeriesColumnDto.getName())
                .column(dataSetSeriesColumnDto.getType(), dataSourceColumnRepository.findOne(dataSetSeriesColumnDto.getDataSourceColumn().getId()))
                .build();
    }

}
