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
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.database.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataSourceColumnWeb;
import com.abixen.platform.service.businessintelligence.multivisualisation.util.DatabaseDataSourceBuilder;

import java.util.HashSet;
import java.util.Set;


public class DatabaseDataSourceBuilderImpl extends EntityBuilder<DatabaseDataSource> implements DatabaseDataSourceBuilder {


    @Override
    protected void initProduct() {
        this.product = new DatabaseDataSource();
    }

    @Override
    public DatabaseDataSourceBuilder base(String name, String description) {
        this.product.setName(name);
        this.product.setDescription(description);
        this.product.setDataSourceType(DataSourceType.DB);
        return this;
    }

    @Override
    public DatabaseDataSourceBuilder connection(DatabaseConnection databaseConnection) {
        this.product.setDatabaseConnection(databaseConnection);
        return this;
    }

    @Override
    public DatabaseDataSourceBuilder data(String table, String filter) {
        this.product.setTable(table);
        this.product.setFilter(filter);
        return this;
    }

    @Override
    public DatabaseDataSourceBuilder columns(Set<DataSourceColumnWeb> columns) {

        Set<DataSourceColumn> dataSourceColumns = new HashSet<>();

        for (DataSourceColumnWeb dataSourceColumnWeb : columns) {
            DataSourceColumn dataSourceColumn = new DataSourceColumn();
            dataSourceColumn.setName(dataSourceColumnWeb.getName());
            dataSourceColumn.setPosition(dataSourceColumnWeb.getPosition());
            dataSourceColumn.setDataValueType(dataSourceColumnWeb.getDataValueType());
            dataSourceColumn.setDataSource(this.product);
            dataSourceColumns.add(dataSourceColumn);
        }

        this.product.setColumns(dataSourceColumns);

        return this;
    }
}
