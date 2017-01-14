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

package com.abixen.platform.service.businessintelligence.multivisualization.util.impl;

import com.abixen.platform.core.util.EntityBuilder;
import com.abixen.platform.service.businessintelligence.multivisualization.model.impl.data.DataValueInteger;
import com.abixen.platform.service.businessintelligence.multivisualization.util.DataSourceValueBuilder;


public class DataSourceValueIntegerBuilderImpl extends EntityBuilder<DataValueInteger> implements DataSourceValueBuilder<DataValueInteger, Integer> {

    @Override
    protected void initProduct() {
        this.product = new DataValueInteger();
    }

    @Override
    protected DataValueInteger assembleProduct() {
        return this.product;
    }

    @Override
    public DataSourceValueBuilder value(Integer value) {
        this.product.setValue(value);
        return this;
    }

    @Override
    public DataSourceValueBuilder valueString(String value) {
        this.product.setValue(value != null ? Integer.valueOf(value) : null);
        return this;
    }

    @Override
    public DataSourceValueBuilder position(Integer position) {
        this.product.setPosition(position);
        return this;
    }

}
