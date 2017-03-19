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
import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.ColumnType;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.DataSetSeriesColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.util.DataSetSeriesColumnBuilder;


public class DataSetSeriesColumnBuilderImpl extends EntityBuilder<DataSetSeriesColumn> implements DataSetSeriesColumnBuilder {


    @Override
    public DataSetSeriesColumnBuilder create() {
        return this;
    }

    @Override
    public DataSetSeriesColumnBuilder name(String name) {
        this.product.setName(name);
        return this;
    }

    @Override
    public DataSetSeriesColumnBuilder dataSourceColumn(DataSourceColumn dataSourceColumn) {
        this.product.setDataSourceColumn(dataSourceColumn);
        return this;
    }

    @Override
    public DataSetSeriesColumnBuilder columnType(ColumnType columnType) {
        this.product.setType(columnType);
        return this;
    }

    @Override
    protected void initProduct() {
        this.product = new DataSetSeriesColumn();
    }
}
