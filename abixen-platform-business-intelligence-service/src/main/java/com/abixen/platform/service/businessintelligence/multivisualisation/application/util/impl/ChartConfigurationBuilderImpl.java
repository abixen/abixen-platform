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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.util.impl;

import com.abixen.platform.common.util.EntityBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetChartDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.ChartType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.*;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataSourceColumnRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.util.ChartConfigurationBuilder;

import java.util.HashSet;
import java.util.Set;



public class ChartConfigurationBuilderImpl extends EntityBuilder<ChartConfiguration> implements ChartConfigurationBuilder {

    public ChartConfigurationBuilderImpl() {
        super();
    }

    public ChartConfigurationBuilderImpl(ChartConfiguration chartConfiguration) {
        super();
        this.product = chartConfiguration;
    }

    @Override
    public ChartConfigurationBuilder basic(Long moduleId, ChartType chartType) {
        this.product.setModuleId(moduleId);
        this.product.setChartType(chartType);
        return this;
    }

    @Override
    public ChartConfigurationBuilder data(DataSetChartDto dataSetChart, DataSource dataSource, DataSourceColumnRepository dataSourceColumnRepository) {

        DataSetChart dataSetChartCreated = new DataSetChart();

        if (dataSetChart.getDomainXSeriesColumn() != null) {
            DataSetSeriesColumnDto dataSetSeriesColumn = dataSetChart.getDomainXSeriesColumn();
            DataSetSeriesColumn dataSetSeriesColumnCreated = new DataSetSeriesColumn();

            dataSetSeriesColumnCreated.setName(dataSetSeriesColumn.getName());
            dataSetSeriesColumnCreated.setType(dataSetSeriesColumn.getType());
            dataSetSeriesColumnCreated.setDataSourceColumn(dataSourceColumnRepository.findOne(dataSetSeriesColumn.getDataSourceColumn().getId()));

            dataSetChartCreated.setDomainXSeriesColumn(dataSetSeriesColumnCreated);
        }

        if (dataSetChart.getDomainZSeriesColumn() != null) {
            DataSetSeriesColumnDto dataSetSeriesColumn = dataSetChart.getDomainZSeriesColumn();
            DataSetSeriesColumn dataSetSeriesColumnCreated = new DataSetSeriesColumn();

            dataSetSeriesColumnCreated.setName(dataSetSeriesColumn.getName());
            dataSetSeriesColumnCreated.setType(dataSetSeriesColumn.getType());
            dataSetSeriesColumnCreated.setDataSourceColumn(dataSourceColumnRepository.findOne(dataSetSeriesColumn.getDataSourceColumn().getId()));

            dataSetChartCreated.setDomainXSeriesColumn(dataSetSeriesColumnCreated);
        }

        Set<DataSetSeries> dataSetSeries = new HashSet<>();

        dataSetChart.getDataSetSeries().stream().forEach(dss -> {
            DataSetSeries dssCreated = new DataSetSeries();
            dssCreated.setFilter(dss.getFilter());
            dssCreated.setName(dss.getName());
            //TODO
            DataSetSeriesColumn dataSetSeriesColumnCreated = new DataSetSeriesColumn();
            DataSetSeriesColumnDto dataSetSeriesColumn = dss.getValueSeriesColumn();
            dataSetSeriesColumnCreated.setId(dataSetSeriesColumn.getId());
            dataSetSeriesColumnCreated.setName(dataSetSeriesColumn.getName());
            dataSetSeriesColumnCreated.setType(dataSetSeriesColumn.getType());
            dataSetSeriesColumnCreated.setDataSourceColumn(dataSourceColumnRepository.findOne(dataSetSeriesColumn.getDataSourceColumn().getId()));

            dssCreated.setValueSeriesColumn(dataSetSeriesColumnCreated);
            dssCreated.getValueSeriesColumn().setDataSourceColumn(dataSourceColumnRepository.findOne(dssCreated.getValueSeriesColumn().getDataSourceColumn().getId()));
            //TODO - add columns

            dataSetSeries.add(dssCreated);
        });

        dataSetChartCreated.setDataSetSeries(dataSetSeries);

        this.product.setDataSetChart(dataSetChartCreated);
        this.product.setDataSource(dataSource);
        return this;
    }

    @Override
    public ChartConfigurationBuilder axis(String axisXName, String axisYName) {
        this.product.setAxisXName(axisXName);
        this.product.setAxisYName(axisYName);
        return this;
    }

    @Override
    public ChartConfigurationBuilder filter(String filter) {
        this.product.setFilter(filter);
        return this;
    }

    @Override
    protected void initProduct() {
        this.product = new ChartConfiguration();
    }

}