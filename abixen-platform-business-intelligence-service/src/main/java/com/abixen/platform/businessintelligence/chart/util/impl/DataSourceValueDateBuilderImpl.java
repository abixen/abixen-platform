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

package com.abixen.platform.businessintelligence.chart.util.impl;

import com.abixen.platform.core.util.EntityBuilder;
import com.abixen.platform.businessintelligence.chart.model.impl.DataSourceValueDate;
import com.abixen.platform.businessintelligence.chart.util.DataSourceValueBuilder;

import java.util.Date;


public class DataSourceValueDateBuilderImpl extends EntityBuilder<DataSourceValueDate> implements DataSourceValueBuilder<DataSourceValueDate, Date> {

    @Override
    protected void initProduct() {
        this.product = new DataSourceValueDate();
    }

    @Override
    protected DataSourceValueDate assembleProduct() {
        return this.product;
    }

    @Override
    public DataSourceValueBuilder value(Date value) {
        this.product.setValue(value);
        return this;
    }

    @Override
    public DataSourceValueBuilder valueString(String value) {
        //todo formatDate
        return this;
    }

    @Override
    public DataSourceValueBuilder position(Integer position) {
        this.product.setPosition(position);
        return this;
    }

}
