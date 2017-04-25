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

package com.abixen.platform.service.businessintelligence.multivisualisation.util.impl;

import com.abixen.platform.common.util.EntityBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.DataSet;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.DataSetSeries;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.DataSetSeriesColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.util.DataSetSeriesBuilder;

import java.util.Date;

public class DataSetSeriesBuilderImpl extends EntityBuilder<DataSetSeries> implements DataSetSeriesBuilder {

    @Override
    public DataSetSeriesBuilder create() {
        Date currentDate = new Date();
        //this.product.setCreatedDate(currentDate);
        //this.product.setLastModifiedDate(currentDate);
        return this;
    }

    @Override
    public DataSetSeriesBuilder dataSet(DataSet dataSet) {
        this.product.setDataSet(dataSet);
        return this;
    }


    @Override
    protected void initProduct() {
        this.product = new DataSetSeries();
    }

    @Override
    public DataSetSeriesBuilder name(String name) {
        this.product.setName(name);
        return this;
    }

    @Override
    public DataSetSeriesBuilder valueSeriesColumn(DataSetSeriesColumn seriesColumns) {
        this.product.setValueSeriesColumn(seriesColumns);
        return this;
    }

}
