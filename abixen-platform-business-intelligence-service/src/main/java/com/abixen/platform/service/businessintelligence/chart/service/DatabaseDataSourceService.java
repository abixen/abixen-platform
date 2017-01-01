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

package com.abixen.platform.service.businessintelligence.chart.service;

import com.abixen.platform.service.businessintelligence.chart.form.DatabaseDataSourceForm;
import com.abixen.platform.service.businessintelligence.chart.model.impl.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.chart.model.impl.DatabaseDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface DatabaseDataSourceService {

    Page<DatabaseDataSource> getDataSources(String jsonCriteria, Pageable pageable);

    Page<DatabaseDataSource> findAllDataSources(Pageable pageable);

    Set<DataSourceColumn> getDataSourceColumns(Long dataSourceId);

    List<Map<String, Integer>> getAllColumns(Long dataSourceId);

    DatabaseDataSource buildDataSource(DatabaseDataSourceForm databaseDataSourceForm);

    DatabaseDataSourceForm createDataSource(DatabaseDataSourceForm databaseDataSourceForm);

    DatabaseDataSource createDataSource(DatabaseDataSource databaseDataSource);

    DatabaseDataSourceForm updateDataSource(DatabaseDataSourceForm databaseDataSourceForm);

    DatabaseDataSource updateDataSource(DatabaseDataSource databaseDataSource);

    DatabaseDataSource findDataSource(Long id);
}
