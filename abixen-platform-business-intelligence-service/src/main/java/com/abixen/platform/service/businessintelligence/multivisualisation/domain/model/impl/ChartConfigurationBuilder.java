/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl;

import com.abixen.platform.common.util.EntityBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.ChartType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource;


public class ChartConfigurationBuilder extends EntityBuilder<ChartConfiguration> {

    public ChartConfigurationBuilder moduleId(final Long moduleId) {
        this.product.setModuleId(moduleId);
        return this;
    }

    public ChartConfigurationBuilder chartParameters(final ChartType chartType, final DataSetChart dataSetChart) {
        this.product.setChartType(chartType);
        this.product.setDataSetChart(dataSetChart);
        return this;
    }

    public ChartConfigurationBuilder axisNames(final String axisXName, final String axisYName) {
        this.product.setAxisXName(axisXName);
        this.product.setAxisYName(axisYName);
        return this;
    }

    public ChartConfigurationBuilder dataParameters(final String filter, final DataSource dataSource) {
        this.product.setFilter(filter);
        this.product.setDataSource(dataSource);
        return this;
    }


    @Override
    public ChartConfiguration build() {
        return super.build();
    }

    @Override
    protected void initProduct() {
        this.product = new ChartConfiguration();
    }

    @Override
    protected ChartConfiguration assembleProduct() {
        return super.assembleProduct();
    }
}
