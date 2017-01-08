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

package com.abixen.platform.service.businessintelligence.multivisualization.service;

import com.abixen.platform.service.businessintelligence.multivisualization.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualization.form.DatabaseConnectionForm;
import com.abixen.platform.service.businessintelligence.multivisualization.model.web.DataSourceValueWeb;
import com.abixen.platform.service.businessintelligence.multivisualization.model.impl.database.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualization.model.impl.datasource.database.DatabaseDataSource;

import java.sql.Connection;
import java.util.Map;
import java.util.List;


public interface DatabaseService {

    Connection getConnection(DatabaseConnection databaseConnection);

    Connection getConnection(DatabaseConnectionForm databaseConnectionForm);

    List<String> getColumns(Connection connection, String tableName);

    List<String> getTables(Connection connection);

    List<Map<String, DataSourceValueWeb>> getChartData(Connection connection, DatabaseDataSource databaseDataSource, ChartConfigurationForm chartConfigurationForm);

    List<Map<String, DataSourceValueWeb>> getChartDataPreview(Connection connection, DatabaseDataSource databaseDataSource, ChartConfigurationForm chartConfigurationForm, String seriesName);

}
