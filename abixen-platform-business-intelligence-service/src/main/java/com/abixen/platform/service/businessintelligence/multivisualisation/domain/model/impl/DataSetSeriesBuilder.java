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

public class DataSetSeriesBuilder extends EntityBuilder<DataSetSeries> {

    @Override
    public DataSetSeries build() {
        return super.build();
    }

    @Override
    protected void initProduct() {
        this.product = new DataSetSeries();
    }

    @Override
    protected DataSetSeries assembleProduct() {
        return super.assembleProduct();
    }

    public DataSetSeriesBuilder name(final String name) {
        this.product.setName(name);
        return this;
    }

    public DataSetSeriesBuilder valueSeriesColumn(final DataSetSeriesColumn valueSeriesColumn) {
        this.product.setValueSeriesColumn(valueSeriesColumn);
        return this;
    }

    public DataSetSeriesBuilder dataParameters(final String filter, final DataSet dataSet) {
        this.product.setFilter(filter);
        this.product.setDataSet(dataSet);
        return this;
    }
}
