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

package com.abixen.platform.service.businessintelligence.chart.util.impl;

import com.abixen.platform.core.util.EntityBuilder;
import com.abixen.platform.service.businessintelligence.chart.model.enumtype.DataSourceFileType;
import com.abixen.platform.service.businessintelligence.chart.model.enumtype.DataSourceType;
import com.abixen.platform.service.businessintelligence.chart.model.impl.FileDataSource;
import com.abixen.platform.service.businessintelligence.chart.util.FileDataSourceBuilder;


public class FileDataSourceBuilderImpl extends EntityBuilder<FileDataSource> implements FileDataSourceBuilder {

    @Override
    protected void initProduct() {
        this.product = new FileDataSource();
    }


    @Override
    public FileDataSourceBuilder base(String name, String description, DataSourceFileType dataSourceFileType) {
        this.product.setName(name);
        this.product.setDescription(description);
        this.product.setDataSourceFileType(dataSourceFileType);
        this.product.setDataSourceType(DataSourceType.FILE);
        return this;
    }

    @Override
    public FileDataSourceBuilder filter(String filter) {
        this.product.setFilter(filter);
        return this;
    }
}
