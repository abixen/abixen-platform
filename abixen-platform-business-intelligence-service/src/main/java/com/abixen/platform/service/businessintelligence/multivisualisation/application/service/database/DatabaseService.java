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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service.database;

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DatabaseConnectionForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceColumn;

import java.sql.Connection;
import java.util.Map;
import java.util.List;


public interface DatabaseService {

    Connection getConnection(DatabaseConnectionDto databaseConnection);

    Connection getConnection(DatabaseConnectionForm databaseConnectionForm);

    List<DataSourceColumn> findColumns(Connection connection, String tableName);

    List<String> findTables(Connection connection);

    List<Map<String, DataValueDto>> findChartData(Connection connection, DataSource dataSource, ChartConfigurationForm chartConfigurationForm, String seriesName);

    List<Map<String, DataValueDto>> findDataSourcePreview(Connection connection, DataSource dataSource);

}