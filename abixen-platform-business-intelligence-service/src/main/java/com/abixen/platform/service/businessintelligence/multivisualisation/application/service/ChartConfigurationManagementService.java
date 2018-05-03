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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service;

import com.abixen.platform.service.businessintelligence.multivisualisation.application.converter.ChartConfigurationToChartConfigurationDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.ChartConfigurationDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.ChartConfiguration;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataSet;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataSetSeries;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataSetSeriesColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.ChartConfigurationService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.DataSourceColumnService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.DataSourceService;
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
public class ChartConfigurationManagementService {

    private final DataSourceService dataSourceService;
    private final ChartConfigurationService chartConfigurationService;
    private final DataSourceColumnService dataSourceColumnService;
    private final ChartConfigurationToChartConfigurationDtoConverter chartConfigurationToChartConfigurationDtoConverter;


    @Autowired
    public ChartConfigurationManagementService(ChartConfigurationService chartConfigurationService,
                                               DataSourceService dataSourceService,
                                               DataSourceColumnService dataSourceColumnService,
                                               ChartConfigurationToChartConfigurationDtoConverter chartConfigurationToChartConfigurationDtoConverter) {
        this.dataSourceColumnService = dataSourceColumnService;
        this.dataSourceService = dataSourceService;
        this.chartConfigurationService = chartConfigurationService;
        this.chartConfigurationToChartConfigurationDtoConverter = chartConfigurationToChartConfigurationDtoConverter;
    }

    public ChartConfigurationDto findChartConfiguration(final Long moduleId) {
        log.debug("findChartConfiguration() - moduleId: {}", moduleId);

        final ChartConfiguration chartConfiguration = chartConfigurationService.find(moduleId);

        return chartConfigurationToChartConfigurationDtoConverter.convert(chartConfiguration);
    }

    public ChartConfigurationForm createChartConfiguration(final ChartConfigurationForm chartConfigurationForm) {
        log.debug("createChartConfiguration() - chartConfigurationForm: {}", chartConfigurationForm);

        final ChartConfiguration chartConfiguration = ChartConfiguration.builder()
                .moduleId(chartConfigurationForm.getModuleId())
                .chartParameters(chartConfigurationForm.getChartType(), buildDataSet(chartConfigurationForm.getDataSet()))
                .dataParameters(chartConfigurationForm.getFilter(), dataSourceService.find(chartConfigurationForm.getDataSource().getId()))
                .axisNames(chartConfigurationForm.getAxisXName(), chartConfigurationForm.getAxisYName())
                .build();

        final ChartConfiguration createdChartConfiguration = chartConfigurationService.create(chartConfiguration);
        final ChartConfigurationDto createdChartConfigurationDto = chartConfigurationToChartConfigurationDtoConverter.convert(createdChartConfiguration);

        return new ChartConfigurationForm(createdChartConfigurationDto);
    }

    public ChartConfigurationForm updateChartConfiguration(final ChartConfigurationForm chartConfigurationForm) {
        log.debug("createChartConfiguration() - chartConfigurationForm: {}", chartConfigurationForm);

        final ChartConfiguration chartConfiguration = chartConfigurationService.find(chartConfigurationForm.getModuleId());
        chartConfiguration.changeModuleId(chartConfigurationForm.getModuleId());
        chartConfiguration.changeAxisNames(chartConfigurationForm.getAxisXName(), chartConfigurationForm.getAxisYName());
        chartConfiguration.changeChartParameters(chartConfigurationForm.getChartType(), buildDataSet(chartConfigurationForm.getDataSet()));
        chartConfiguration.changeDataParameters(chartConfigurationForm.getFilter(), dataSourceService.find(chartConfigurationForm.getDataSource().getId()));

        final ChartConfiguration updatedChartConfiguration = chartConfigurationService.update(chartConfiguration);
        final ChartConfigurationDto updatedChartConfigurationDto = chartConfigurationToChartConfigurationDtoConverter.convert(updatedChartConfiguration);

        return new ChartConfigurationForm(updatedChartConfigurationDto);
    }

    public void deleteChartConfiguration(final Long moduleId) {
        chartConfigurationService.delete(moduleId);
    }

    private DataSet buildDataSet(final DataSetDto dataSetDto) {
        final DataSet dataSet = DataSet.builder()
                .dataSetSeries(dataSetDto.getDataSetSeries().stream()
                        .map(buildDataSetSeries())
                        .collect(Collectors.toSet()))
                .domainSeries(buildDataSetSeriesColumn(dataSetDto.getDomainXSeriesColumn()),
                        ofNullable(dataSetDto.getDomainZSeriesColumn())
                                .map(this::buildDataSetSeriesColumn)
                                .orElse(null))
                .build();

        dataSet.getDataSetSeries().forEach(
                dataSetSeries -> dataSetSeries.changeDataParameters(dataSetSeries.getFilter(), dataSet)
        );

        return dataSet;
    }

    private Function<DataSetSeriesDto, DataSetSeries> buildDataSetSeries() {
        return dataSetSeriesDto -> DataSetSeries.builder()
                .dataParameters(dataSetSeriesDto.getFilter(), null)
                .valueSeriesColumn(buildDataSetSeriesColumn(dataSetSeriesDto.getValueSeriesColumn()))
                .name(dataSetSeriesDto.getName())
                .build();
    }

    private DataSetSeriesColumn buildDataSetSeriesColumn(final DataSetSeriesColumnDto dataSetSeriesColumnDto) {
        return DataSetSeriesColumn.builder()
                .name(dataSetSeriesColumnDto.getName())
                .column(dataSetSeriesColumnDto.getType(), dataSourceColumnService.find(dataSetSeriesColumnDto.getDataSourceColumn().getId()))
                .build();
    }

}