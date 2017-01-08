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

package com.abixen.platform.service.businessintelligence.multivisualization.service.impl;


import com.abixen.platform.service.businessintelligence.multivisualization.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualization.model.web.DataSourceValueWeb;
import com.abixen.platform.service.businessintelligence.multivisualization.model.impl.database.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualization.model.impl.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualization.service.ChartDataService;
import com.abixen.platform.service.businessintelligence.multivisualization.service.DatabaseDataSourceService;
import com.abixen.platform.service.businessintelligence.multivisualization.service.DatabaseService;
import com.abixen.platform.service.businessintelligence.multivisualization.service.DatabaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.Map;
import java.util.List;

@Service
public class ChartDataServiceImpl implements ChartDataService {

    @Autowired
    private DatabaseFactory databaseFactory;

    @Autowired
    private DatabaseDataSourceService databaseDataSourceService;

    @Override
    public List<Map<String, DataSourceValueWeb>> getChartData(ChartConfigurationForm chartConfigurationForm) {
        DatabaseDataSource databaseDataSource = databaseDataSourceService.findDataSource(chartConfigurationForm.getDataSource().getId());
        DatabaseConnection databaseConnection = databaseDataSource.getDatabaseConnection();
        DatabaseService databaseService = databaseFactory.getDatabaseService(databaseConnection.getDatabaseType());
        Connection connection = databaseService.getConnection(databaseConnection);
        List<Map<String, DataSourceValueWeb>> chartData = databaseService.getChartData(connection, databaseDataSource, chartConfigurationForm);
        return chartData;
    }

    @Override
    public List<Map<String, DataSourceValueWeb>> getChartDataPreview(ChartConfigurationForm chartConfigurationForm, String seriesName) {
        DatabaseDataSource databaseDataSource = databaseDataSourceService.findDataSource(chartConfigurationForm.getDataSource().getId());
        DatabaseConnection databaseConnection = databaseDataSource.getDatabaseConnection();
        DatabaseService databaseService = databaseFactory.getDatabaseService(databaseConnection.getDatabaseType());
        Connection connection = databaseService.getConnection(databaseConnection);
        List<Map<String, DataSourceValueWeb>> chartData = databaseService.getChartDataPreview(connection, databaseDataSource, chartConfigurationForm, seriesName);
        return chartData;
    }
}
