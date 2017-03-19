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

import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.util.DataSourceColumnBuilder;
import com.abixen.platform.common.util.EntityBuilder;


public class DataSourceColumnBuilderImpl extends EntityBuilder<DataSourceColumn> implements DataSourceColumnBuilder {

    @Override
    public DataSourceColumnBuilder create() {
        return this;
    }

    @Override
    public DataSourceColumnBuilder name(String name) {
        this.product.setName(name);
        return this;
    }

    @Override
    public DataSourceColumnBuilder position(Integer position) {
        this.product.setPosition(position);
        return this;
    }

    @Override
    protected void initProduct() {
        this.product = new DataSourceColumn();
    }
}
