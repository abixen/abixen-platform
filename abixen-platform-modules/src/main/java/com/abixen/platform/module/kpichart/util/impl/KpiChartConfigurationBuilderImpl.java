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

package com.abixen.platform.module.kpichart.util.impl;

import com.abixen.platform.core.util.EntityBuilder;
import com.abixen.platform.module.kpichart.model.enumtype.AnimationType;
import com.abixen.platform.module.kpichart.model.enumtype.ColorCode;
import com.abixen.platform.module.kpichart.model.impl.KpiChartConfiguration;
import com.abixen.platform.module.kpichart.util.KpiChartConfigurationBuilder;



public class KpiChartConfigurationBuilderImpl extends EntityBuilder<KpiChartConfiguration> implements KpiChartConfigurationBuilder {

    @Override
    public KpiChartConfigurationBuilder create() {
        return this;
    }

    @Override
    public KpiChartConfigurationBuilder basic(Long moduleId) {
        this.product.setModuleId(moduleId);

        return this;
    }

    @Override
    public KpiChartConfigurationBuilder value(Double value, Double maxValue) {
        this.product.setValue(value);
        this.product.setMaxValue(maxValue);
        return this;
    }

    @Override
    public KpiChartConfigurationBuilder animation(Integer animationDuration, Integer animationDelay, AnimationType animationType) {
        this.product.setAnimationDuration(animationDuration);
        this.product.setAnimationDelay(animationDelay);
        this.product.setAnimationType(animationType);
        return this;
    }

    @Override
    public KpiChartConfigurationBuilder appearance(Integer lineWidth, Integer radius, Boolean semi, Boolean clockwise, Boolean responsive, Boolean rounded, ColorCode colorCode) {
        this.product.setLineWidth(lineWidth);
        this.product.setRadius(radius);
        this.product.setSemi(semi);
        this.product.setClockwise(clockwise);
        this.product.setResponsive(responsive);
        this.product.setRounded(rounded);
        this.product.setColorCode(colorCode);
        return this;
    }

    @Override
    public KpiChartConfigurationBuilder description(String description) {
        this.product.setDescription(description);
        return this;
    }

    @Override
    protected void initProduct() {
        this.product = new KpiChartConfiguration();
    }

}