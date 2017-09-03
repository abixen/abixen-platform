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

import com.abixen.platform.service.businessintelligence.infrastructure.configuration.PlatformModuleConfiguration;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.ColumnType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataSourceType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataValueType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.*;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumnBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.ChartConfigurationRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataSourceColumnRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataSourceRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.ChartConfigurationManagementService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

import static com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.ChartType.LINE_AND_BAR_WITH_FOCUS_CHART;
import static java.util.Arrays.asList;
import static org.mockito.MockitoAnnotations.initMocks;

@Service
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformModuleConfiguration.class)
@Ignore
public class ChartConfigurationManagementServiceImplTest {
    /*private static final String AXIS_X_NAME = "axisXName";
    private static final String AXIS_Y_NAME = "axisYName";
    private static final String FILTER = "filter";
    private static final Long ID = 0L;
    private static final Long MODULE_ID = 1L;
    private static final String DATASOURCE_NAME = "dataSourceName";
    private static final String DATASOURCE_FILTER = "dataSourceFilter";
    private static final String DATASOURCE_DESCRIPTION = "dataSourceDescription";

    @Autowired
    private ChartConfigurationRepository chartConfigurationRepository;

    @Autowired
    private ChartConfigurationManagementService chartConfigurationManagementService;

    @Autowired
    private DataSourceColumnRepository dataSourceColumnRepository;

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Autowired
    private DataSetRepository dataSetRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    @Ignore
    public void checkThatRemoveChartConfigurationMethodProperlyDateChartConfigurationObject(){
        ChartConfiguration givenChartConfiguration = prepareChartConfiguration();
        ChartConfiguration savedChartConfiguration = chartConfigurationRepository.save(givenChartConfiguration);
        chartConfigurationRepository.flush();
        chartConfigurationManagementService.deleteChartConfiguration(savedChartConfiguration.getModuleId());
        chartConfigurationRepository.flush();
        entityManager.clear();
        ChartConfiguration deletedChartConfiguration = chartConfigurationRepository.findOne(savedChartConfiguration.getId());
        assert deletedChartConfiguration == null;
    }



    private ChartConfiguration prepareChartConfiguration() {
        DataSource dataSource = prepareDataSource();
        return new ChartConfigurationBuilder()
                .axisNames(AXIS_X_NAME, AXIS_Y_NAME)
                .chartParameters(LINE_AND_BAR_WITH_FOCUS_CHART,  prepareDataSetChart(dataSource))
                .dataParameters(FILTER, dataSource)
                .moduleId(MODULE_ID)
                .build();
    }

    private DataSetChart prepareDataSetChart(DataSource dataSource) {
        List<DataSourceColumn> dataSourceColumnList = dataSource.getColumns().stream()
                .collect(Collectors.toList());

        DataSetChart dataSetChart = new DataSetChartBuilder()
                .domainSeries(
                        new DataSetSeriesColumnBuilder()
                                .name(StringUtils.EMPTY)
                                .column(ColumnType.X, dataSourceColumnList.get(0))
                                .build(),
                        new DataSetSeriesColumnBuilder()
                                .name(StringUtils.EMPTY)
                                .column(ColumnType.Z, dataSourceColumnList.get(1))
                                .build())
                .dataSetSeries(new HashSet<>(asList(new DataSetSeriesBuilder()
                        .valueSeriesColumn(new DataSetSeriesColumnBuilder()
                                .name(StringUtils.EMPTY)
                                .column(ColumnType.Z, dataSourceColumnList.get(2))
                                .build())
                        .name(StringUtils.EMPTY)
                        .build())))
                .build();

        dataSetChart.getDataSetSeries().forEach(
                dataSetSeries -> dataSetSeries.changeDataParameters(dataSetSeries.getFilter(), dataSetChart)
        );

        dataSetRepository.save(dataSetChart);
        return dataSetChart;
    }

    private DataSource prepareDataSource() {
        DataSource dataSource = new DataSourceBuilder()
                .details(DATASOURCE_NAME, DATASOURCE_DESCRIPTION)
                .paramters(DataSourceType.DB, DATASOURCE_FILTER)
                .build();
        dataSource = dataSourceRepository.save(dataSource);
        DataSourceColumn dataSourceColumnFirst = new DataSourceColumnBuilder()
                .details("")
                .paramters(DataValueType.STRING, 1)
                .dataSource(dataSource)
                .build();
        dataSourceColumnFirst = dataSourceColumnRepository.save(dataSourceColumnFirst);
        DataSourceColumn dataSourceColumnSecond = new DataSourceColumnBuilder()
                .details("")
                .paramters(DataValueType.STRING, 2)
                .dataSource(dataSource)
                .build();
        DataSourceColumn dataSourceColumnThird = new DataSourceColumnBuilder()
                .details("")
                .paramters(DataValueType.STRING, 2)
                .dataSource(dataSource)
                .build();
        dataSourceColumnSecond = dataSourceColumnRepository.save(dataSourceColumnSecond);
        Set<DataSourceColumn> dataSourceColumns = new HashSet<>();
        dataSourceColumns.add(dataSourceColumnFirst);
        dataSourceColumns.add(dataSourceColumnSecond);
        dataSourceColumns.add(dataSourceColumnThird);
        dataSource.changeColumns(dataSourceColumns);
        return dataSourceRepository.save(dataSource);
    }*/
}