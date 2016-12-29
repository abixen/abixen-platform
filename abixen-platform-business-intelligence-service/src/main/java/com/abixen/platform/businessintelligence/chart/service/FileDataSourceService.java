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

package com.abixen.platform.businessintelligence.chart.service;

import com.abixen.platform.businessintelligence.chart.form.FileDataSourceForm;
import com.abixen.platform.businessintelligence.chart.model.impl.DataSourceColumn;
import com.abixen.platform.businessintelligence.chart.model.impl.FileDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface FileDataSourceService {

    Page<FileDataSource> getDataSources(String jsonCriteria, Pageable pageable);

    Page<FileDataSource> findAllDataSources(Pageable pageable);

    Set<DataSourceColumn> getDataSourceColumns(Long dataSourceId);

    List<Map<String, Integer>> getAllColumns(Long dataSourceId);

    FileDataSource buildDataSource(FileDataSourceForm fileDataSourceForm);

    FileDataSourceForm createDataSource(FileDataSourceForm fileDataSourceForm);

    FileDataSource createDataSource(FileDataSource fileDataSource);

    FileDataSourceForm updateDataSource(FileDataSourceForm fileDataSourceForm);

    FileDataSource updateDataSource(FileDataSource fileDataSource);

    FileDataSource findDataSource(Long id);
}
