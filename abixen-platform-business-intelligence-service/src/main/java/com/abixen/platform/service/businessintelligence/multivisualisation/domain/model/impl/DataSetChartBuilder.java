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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl;


import java.util.Set;

public class DataSetChartBuilder extends DataSetBuilder {

    @Override
    public DataSetChartBuilder domainSeries(DataSetSeriesColumn domainXSeriesColumn, DataSetSeriesColumn domainZSeriesColumn) {
        return (DataSetChartBuilder) super.domainSeries(domainXSeriesColumn, domainZSeriesColumn);
    }

    @Override
    public DataSetChartBuilder dataSetSeries(Set<DataSetSeries> dataSetSeries) {
        return (DataSetChartBuilder) super.dataSetSeries(dataSetSeries);
    }

    @Override
    public DataSetChart build() {
        return (DataSetChart) super.build();
    }

    @Override
    protected void initProduct() {
        this.product = new DataSetChart();
    }

    @Override
    protected DataSetChart assembleProduct() {
        return (DataSetChart) super.assembleProduct();
    }
}
