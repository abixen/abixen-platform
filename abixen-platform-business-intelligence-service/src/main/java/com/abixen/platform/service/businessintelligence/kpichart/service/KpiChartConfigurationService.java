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

package com.abixen.platform.service.businessintelligence.kpichart.service;

import com.abixen.platform.service.businessintelligence.kpichart.form.KpiChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.kpichart.model.impl.KpiChartConfiguration;


public interface KpiChartConfigurationService {

    KpiChartConfiguration buildKpiChartConfiguration(KpiChartConfigurationForm kpiChartConfigurationForm);

    KpiChartConfigurationForm createKpiChartConfiguration(KpiChartConfigurationForm kpiChartConfigurationForm);

    KpiChartConfigurationForm updateKpiChartConfiguration(KpiChartConfigurationForm kpiChartConfigurationForm);

    KpiChartConfiguration findKpiChartConfigurationByModuleId(Long id);

    KpiChartConfiguration createKpiChartConfiguration(KpiChartConfiguration kpiChartConfiguration);

    KpiChartConfiguration updateKpiChartConfiguration(KpiChartConfiguration kpiChartConfiguration);

    void removeKpiChartConfiguration(Long moduleId);
}
