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

package com.abixen.platform.businessintelligence.chart.util;


import com.abixen.platform.businessintelligence.chart.model.impl.DatabaseConnection;
import com.abixen.platform.businessintelligence.chart.model.impl.DatabaseDataSource;
import com.abixen.platform.businessintelligence.chart.model.web.DataSourceColumnWeb;

import java.util.Set;


public interface DatabaseDataSourceBuilder {

    DatabaseDataSource build();

    DatabaseDataSourceBuilder base(String name, String description);

    DatabaseDataSourceBuilder connection(DatabaseConnection databaseConnection);

    DatabaseDataSourceBuilder data(String table, String filter);

    DatabaseDataSourceBuilder columns(Set<DataSourceColumnWeb> columns);

}

