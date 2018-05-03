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


import com.abixen.platform.service.businessintelligence.multivisualisation.application.converter.DatabaseConnectionToDatabaseConnectionDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.database.DatabaseFactory;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.database.DatabaseService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.file.FileDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.connection.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.Map;
import java.util.List;

@Slf4j
@Service
public class ChartDataService {

    private final DatabaseFactory databaseFactory;
    private final FileService fileService;
    private final DataSourceService dataSourceService;
    private final DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter;

    @Autowired
    public ChartDataService(FileService fileService,
                            DataSourceService dataSourceService,
                            DatabaseFactory databaseFactory,
                            DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter) {
        this.fileService = fileService;
        this.dataSourceService = dataSourceService;
        this.databaseFactory = databaseFactory;
        this.databaseConnectionToDatabaseConnectionDtoConverter = databaseConnectionToDatabaseConnectionDtoConverter;
    }

    public List<Map<String, DataValueDto>> findChartData(final ChartConfigurationForm chartConfigurationForm, final String seriesName) {
        log.debug("findChartData() - chartConfigurationForm: {}, seriesName: {}", chartConfigurationForm, seriesName);

        final DataSource dataSource = dataSourceService.find(chartConfigurationForm.getDataSource().getId());
        switch (dataSource.getDataSourceType()) {
            case DB : return findChartDataInDatabaseDataSource(dataSource, chartConfigurationForm, seriesName);
            case FILE: return findChartDataInFileDataSource(dataSource, chartConfigurationForm, seriesName);
            default: throw new NotImplementedException();
        }
    }

    private List<Map<String, DataValueDto>> findChartDataInDatabaseDataSource(final DataSource dataSource, final ChartConfigurationForm chartConfigurationForm, final String seriesName) {
        final DatabaseConnection databaseConnection = ((DatabaseDataSource) dataSource).getDatabaseConnection();
        final DatabaseService databaseService = databaseFactory.getDatabaseService(databaseConnection.getDatabaseType());
        final Connection connection = databaseService.getConnection(databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection));

        return databaseService.findChartData(connection, dataSource, chartConfigurationForm, seriesName);
    }

    private List<Map<String, DataValueDto>> findChartDataInFileDataSource(final DataSource dataSource, final ChartConfigurationForm chartConfigurationForm, final String seriesName) {
        return fileService.findChartData(((FileDataSource) dataSource), chartConfigurationForm, seriesName);
    }

}