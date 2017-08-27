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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.ChartConfiguration;


public interface ChartConfigurationService {

    ChartConfiguration find(Long id);

    ChartConfiguration create(ChartConfigurationForm chartConfigurationForm);

    ChartConfiguration create(ChartConfiguration chartConfiguration);

    ChartConfiguration update(ChartConfigurationForm chartConfigurationForm);

    ChartConfiguration update(ChartConfiguration chartConfiguration);

    void delete(Long moduleId);

    ChartConfiguration build(ChartConfigurationForm chartConfigurationForm);
}
