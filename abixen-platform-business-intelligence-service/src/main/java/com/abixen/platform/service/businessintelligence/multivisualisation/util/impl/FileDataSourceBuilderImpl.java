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
import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DataSourceType;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.file.FileDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFile;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataSourceColumnWeb;
import com.abixen.platform.service.businessintelligence.multivisualisation.util.FileDataSourceBuilder;

import java.util.HashSet;
import java.util.Set;


public class FileDataSourceBuilderImpl extends EntityBuilder<FileDataSource> implements FileDataSourceBuilder {


    @Override
    protected void initProduct() {
        this.product = new FileDataSource();
    }


    @Override
    public FileDataSourceBuilder base(String name, String description) {
        this.product.setName(name);
        this.product.setDescription(description);
        this.product.setDataSourceType(DataSourceType.FILE);
        return this;
    }

    @Override
    public FileDataSourceBuilder data(Set<DataSourceColumnWeb> columns, DataFile dataFile) {
        Set<DataSourceColumn> dataSourceColumnSet = new HashSet<>();
        columns.forEach(column -> {
            DataSourceColumn dataSourceColumn = new DataSourceColumn();
            dataSourceColumn.setName(column.getName());
            dataSourceColumn.setId(column.getId());
            dataSourceColumn.setPosition(column.getPosition());
            dataSourceColumn.setDataValueType(column.getDataValueType());
            dataSourceColumn.setDataSource(this.product);
            dataSourceColumnSet.add(dataSourceColumn);
        });
        this.product.setColumns(dataSourceColumnSet);
        this.product.setDataFile(dataFile);
        return this;
    }

    @Override
    public FileDataSourceBuilder filter(String filter) {
        this.product.setFilter(filter);
        return this;
    }
}
