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


import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.file.FileDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.database.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.*;
import org.apache.commons.lang.NotImplementedException;
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
    private FileService fileService;

    @Autowired
    private DataSourceService dataSourceService;

    @Override
    public List<Map<String, DataValueDto>> getChartData(ChartConfigurationForm chartConfigurationForm, String seriesName) {
        DataSource dataSource = dataSourceService.findDataSource(chartConfigurationForm.getDataSource().getId());
        switch (dataSource.getDataSourceType()) {
            case DB : return getChartDataFromDatabaseDataSource(dataSource, chartConfigurationForm, seriesName);
            case FILE: return getChartDataFromFileDataSource(dataSource, chartConfigurationForm, seriesName);
            default: throw new NotImplementedException();
        }
    }

    private List<Map<String, DataValueDto>> getChartDataFromDatabaseDataSource(DataSource dataSource, ChartConfigurationForm chartConfigurationForm, String seriesName) {
        DatabaseConnection databaseConnection = ((DatabaseDataSource) dataSource).getDatabaseConnection();
        DatabaseService databaseService = databaseFactory.getDatabaseService(databaseConnection.getDatabaseType());
        Connection connection = databaseService.getConnection(databaseConnection);
        return databaseService.getChartData(connection, ((DatabaseDataSource) dataSource), chartConfigurationForm, seriesName);
    }

    private List<Map<String, DataValueDto>> getChartDataFromFileDataSource(DataSource dataSource, ChartConfigurationForm chartConfigurationForm, String seriesName) {
        return fileService.getChartData(((FileDataSource) dataSource), chartConfigurationForm, seriesName);
    }

}
