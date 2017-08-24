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
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataSourceType;

import java.util.Set;

public class DataSourceBuilder extends EntityBuilder<DataSource> {

    public DataSourceBuilder details(String name, String description) {
        this.product.setName(name);
        this.product.setDescription(description);
        return this;
    }

    public DataSourceBuilder paramters(DataSourceType dataSourceType, String filter) {
        this.product.setDataSourceType(dataSourceType);
        this.product.setFilter(filter);
        return this;
    }

    public DataSourceBuilder columns(Set<DataSourceColumn> dataSourceColumns) {
        this.product.setColumns(dataSourceColumns);
        return this;
    }


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
}
