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

package com.abixen.platform.module.chart.service;


import com.abixen.platform.module.chart.form.ChartConfigurationForm;
import com.abixen.platform.module.chart.model.web.DataSourceValueWeb;

import java.util.Map;
import java.util.List;

public interface ChartDataService {

    List<Map<String, DataSourceValueWeb>> getChartData(ChartConfigurationForm chartConfigurationForm);

    List<Map<String, DataSourceValueWeb>> getChartDataPreview(ChartConfigurationForm chartConfigurationForm, String seriesName);

}
