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
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataSourceColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DataSourceType;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.repository.DatabaseConnectionRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.util.DatabaseDataSourceBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class DatabaseDataSourceBuilderImpl extends EntityBuilder<DatabaseDataSource> implements DatabaseDataSourceBuilder {

    public DatabaseDataSourceBuilderImpl() {
    }

    public DatabaseDataSourceBuilderImpl(DatabaseDataSource databaseDataSource) {
        if (databaseDataSource != null) {
            this.product = databaseDataSource;
        }
    }

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
    public DatabaseDataSourceBuilder connection(DatabaseConnectionDto databaseConnection, DatabaseConnectionRepository databaseConnectionRepository) {
        this.product.setDatabaseConnection(databaseConnectionRepository.findOne(databaseConnection.getId()));
        return this;
    }

    @Override
    public DatabaseDataSourceBuilder data(String table, String filter) {
        this.product.setTable(table);
        this.product.setFilter(filter);
        return this;
    }

    @Override
    public DatabaseDataSourceBuilder columns(Set<DataSourceColumnDto> columns) {
        Set<DataSourceColumn> dataSourceColumns = new HashSet<>();
        List<String> oldColumnNames = this.product.getColumns().stream()
                .map(DataSourceColumn::getName)
                .peek(s -> s = s.toUpperCase())
                .collect(Collectors.toList());
        List<String> newColumnNames = columns.stream()
                .map(DataSourceColumnDto::getName)
                .peek(s -> s = s.toUpperCase())
                .collect(Collectors.toList());
        newColumnNames.replaceAll(String::toUpperCase);
        oldColumnNames.replaceAll(String::toUpperCase);
        List<DataSourceColumn> toRemove = this.product.getColumns().stream()
                .filter(dataSourceColumn -> !newColumnNames.contains(dataSourceColumn.getName().toUpperCase()))
                .collect(Collectors.toList());
        List<DataSourceColumnDto> toAdd = columns.stream()
                .filter(dataSourceColumn -> !oldColumnNames.contains(dataSourceColumn.getName().toUpperCase()))
                .collect(Collectors.toList());
        if (!toRemove.isEmpty()) {
            this.product.removeColumns(new HashSet<>(toRemove));
        }
        if (!toAdd.isEmpty()) {
            convertDataSourceColumnFromToDataSourceColumn(this.product, dataSourceColumns, toAdd);
            this.product.addColumns(dataSourceColumns);
        }
        return this;
    }

    private void convertDataSourceColumnFromToDataSourceColumn(DatabaseDataSource databaseDataSource,
                                                               Set<DataSourceColumn> dataSourceColumns,
                                                               List<DataSourceColumnDto> toAdd) {
        toAdd.forEach(dataSourceColumnDto -> {
            DataSourceColumn dataSourceColumn = new DataSourceColumn();
            dataSourceColumn.setName(dataSourceColumnDto.getName());
            dataSourceColumn.setPosition(dataSourceColumnDto.getPosition());
            dataSourceColumn.setDataSource(databaseDataSource);
            dataSourceColumn.setDataValueType(dataSourceColumnDto.getDataValueType());
            dataSourceColumns.add(dataSourceColumn);
        });
    }
}

