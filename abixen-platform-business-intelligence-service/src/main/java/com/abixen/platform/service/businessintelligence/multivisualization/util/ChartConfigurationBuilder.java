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

package com.abixen.platform.service.businessintelligence.multivisualization.util;


import com.abixen.platform.service.businessintelligence.multivisualization.model.enumtype.ChartType;
import com.abixen.platform.service.businessintelligence.multivisualization.model.impl.ChartConfiguration;
import com.abixen.platform.service.businessintelligence.multivisualization.model.impl.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualization.model.web.DataSetChartWeb;
import com.abixen.platform.service.businessintelligence.multivisualization.repository.DataSourceColumnRepository;


public interface ChartConfigurationBuilder {

    ChartConfiguration build();

    ChartConfigurationBuilder basic(Long moduleId, ChartType chartType);

    ChartConfigurationBuilder data(DataSetChartWeb dataSetChart, DatabaseDataSource databaseDataSource, DataSourceColumnRepository dataSourceColumnRepository);

    ChartConfigurationBuilder axis(String axisXName, String axisYName);

}

