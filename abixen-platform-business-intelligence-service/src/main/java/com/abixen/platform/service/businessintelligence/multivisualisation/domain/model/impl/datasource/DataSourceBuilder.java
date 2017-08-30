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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource;

import com.abixen.platform.common.util.EntityBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataSourceType;

import java.util.Set;

public class DataSourceBuilder extends EntityBuilder<DataSource> {

    @Override
    public DataSource build() {
        return super.build();
    }

    @Override
    public void initProduct() {
        this.product = new DataSource();
    }

    @Override
    protected DataSource assembleProduct() {
        return super.assembleProduct();
    }

    public DataSourceBuilder details(final String name, final String description) {
        this.product.setName(name);
        this.product.setDescription(description);
        return this;
    }

    public DataSourceBuilder paramters(final DataSourceType dataSourceType, final String filter) {
        this.product.setDataSourceType(dataSourceType);
        this.product.setFilter(filter);
        return this;
    }


    public DataSourceBuilder columns(final Set<DataSourceColumn> dataSourceColumns) {
        this.product.setColumns(dataSourceColumns);
        return this;
    }
}
