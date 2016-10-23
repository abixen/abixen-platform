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

package com.abixen.platform.module.chart.util.impl;

import com.abixen.platform.core.util.EntityBuilder;
import com.abixen.platform.module.chart.model.impl.DataSourceValueFloat;
import com.abixen.platform.module.chart.util.DataSourceValueBuilder;


public class DataSourceValueFloatBuilderImpl extends EntityBuilder<DataSourceValueFloat> implements DataSourceValueBuilder<DataSourceValueFloat, Float> {

    @Override
    protected void initProduct() {
        this.product = new DataSourceValueFloat();
    }

    @Override
    protected DataSourceValueFloat assembleProduct() {
        return this.product;
    }

    @Override
    public DataSourceValueBuilder value(Float value) {
        this.product.setValue(value);
        return this;
    }

    @Override
    public DataSourceValueBuilder valueString(String value) {
        this.product.setValue(Float.valueOf(value));
        return this;
    }

    @Override
    public DataSourceValueBuilder position(Integer position) {
        this.product.setPosition(position);
        return this;
    }

}
