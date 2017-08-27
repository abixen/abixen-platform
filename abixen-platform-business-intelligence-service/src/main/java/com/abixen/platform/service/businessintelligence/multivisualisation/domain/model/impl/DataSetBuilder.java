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

import com.abixen.platform.common.util.EntityBuilder;

import java.util.Set;

public class DataSetBuilder extends EntityBuilder<DataSet> {

    public DataSetBuilder domainSeries(final DataSetSeriesColumn domainXSeriesColumn, final DataSetSeriesColumn domainZSeriesColumn) {
        this.product.setDomainXSeriesColumn(domainXSeriesColumn);
        this.product.setDomainZSeriesColumn(domainZSeriesColumn);
        return this;
    }

    public DataSetBuilder dataSetSeries(final Set<DataSetSeries> dataSetSeries) {
        this.product.setDataSetSeries(dataSetSeries);
        return this;
    }

    @Override
    public DataSet build() {
        return super.build();
    }

    @Override
    protected void initProduct() {
        this.product = new DataSet();
    }

    @Override
    protected DataSet assembleProduct() {
        return super.assembleProduct();
    }
}
