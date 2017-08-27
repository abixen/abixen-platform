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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource;

import com.abixen.platform.common.util.EntityBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataValueType;

public class DataSourceColumnBuilder extends EntityBuilder<DataSourceColumn> {

    public DataSourceColumnBuilder details(final String name) {
        this.product.setName(name);
        return this;
    }

    public DataSourceColumnBuilder paramters(final DataValueType dataValueType, final Integer position) {
        this.product.setDataValueType(dataValueType);
        this.product.setPosition(position);
        return this;
    }

    public DataSourceColumnBuilder dataSource(final DataSource dataSource) {
        this.product.setDataSource(dataSource);
        return this;
    }

    @Override
    public DataSourceColumn build() {
        return super.build();
    }

    @Override
    protected void initProduct() {
        this.product = new DataSourceColumn();
    }

    @Override
    protected DataSourceColumn assembleProduct() {
        return super.assembleProduct();
    }
}
