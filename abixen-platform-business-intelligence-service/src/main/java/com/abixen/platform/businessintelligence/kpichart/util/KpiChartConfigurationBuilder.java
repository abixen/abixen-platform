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

package com.abixen.platform.businessintelligence.kpichart.util;


import com.abixen.platform.businessintelligence.kpichart.model.enumtype.AnimationType;
import com.abixen.platform.businessintelligence.kpichart.model.enumtype.ColorCode;
import com.abixen.platform.businessintelligence.kpichart.model.impl.KpiChartConfiguration;


public interface KpiChartConfigurationBuilder {

    KpiChartConfiguration build();

    KpiChartConfigurationBuilder create();

    KpiChartConfigurationBuilder basic(Long moduleId);

    KpiChartConfigurationBuilder value(Double value, Double maxValue);

    KpiChartConfigurationBuilder animation(Integer animationDuration, Integer animationDelay, AnimationType animationType);

    KpiChartConfigurationBuilder appearance(Integer lineWidth, Integer radius, Boolean semi, Boolean clockwise, Boolean responsive, Boolean rounded, ColorCode colorCode);

    KpiChartConfigurationBuilder description(String description);

}

