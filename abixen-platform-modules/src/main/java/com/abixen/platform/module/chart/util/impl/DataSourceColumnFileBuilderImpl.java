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
import com.abixen.platform.module.chart.model.impl.FileDataSource;
import com.abixen.platform.module.chart.model.impl.DataSourceColumnFile;
import com.abixen.platform.module.chart.util.DataSourceColumnFileBuilder;

import java.util.List;



public class DataSourceColumnFileBuilderImpl extends EntityBuilder<DataSourceColumnFile> implements DataSourceColumnFileBuilder {

    @Override
    public DataSourceColumnFileBuilder create() {
        return this;
    }

    @Override
    public DataSourceColumnFileBuilder dataSource(FileDataSource fileDataSource) {
        this.product.setDataSource(fileDataSource);
        return this;
    }

    @Override
    public DataSourceColumnFileBuilder name(String name) {
        this.product.setName(name);
        return this;
    }

    @Override
    public DataSourceColumnFileBuilder position(Integer position) {
        this.product.setPosition(position);
        return this;
    }

    @Override
    public DataSourceColumnFileBuilder dataSourceValue(List dataSourceValues) {
        this.product.setValues(dataSourceValues);
        return this;
    }

    @Override
    protected void initProduct() {
        this.product = new DataSourceColumnFile();
    }
}



