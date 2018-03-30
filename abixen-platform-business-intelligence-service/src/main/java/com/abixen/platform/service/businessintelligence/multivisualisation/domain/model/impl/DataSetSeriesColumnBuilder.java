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

import com.abixen.platform.common.domain.model.EntityBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.ColumnType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumn;

public class DataSetSeriesColumnBuilder extends EntityBuilder<DataSetSeriesColumn> {

    @Override
    public DataSetSeriesColumn build() {
        return super.build();
    }

    @Override
    protected void initProduct() {
        this.product = new DataSetSeriesColumn();
    }

    @Override
    protected DataSetSeriesColumn assembleProduct() {
        return super.assembleProduct();
    }

    public DataSetSeriesColumnBuilder name(final String name) {
        this.product.setName(name);
        return this;
    }

    public DataSetSeriesColumnBuilder column(final ColumnType columnType, final DataSourceColumn dataSourceColumn) {
        this.product.setType(columnType);
        this.product.setDataSourceColumn(dataSourceColumn);
        return this;
    }
}
