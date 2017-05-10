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
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataSourceColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DataSourceType;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.file.FileDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.repository.DataFileRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.util.FileDataSourceBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class FileDataSourceBuilderImpl extends EntityBuilder<FileDataSource> implements FileDataSourceBuilder {

    public FileDataSourceBuilderImpl() {
    }


    public FileDataSourceBuilderImpl(FileDataSource fileDataSource) {
        if (fileDataSource != null) {
            this.product = fileDataSource;
        }
    }

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
    public FileDataSourceBuilder data(Set<DataSourceColumnDto> columns, DataFileDto dataFileDto, DataFileRepository dataFileRepository) {
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
        List<DataSourceColumn> toRemove =  this.product.getColumns().stream()
                .filter(dataSourceColumn -> !newColumnNames.contains(dataSourceColumn.getName().toUpperCase()))
                .collect(Collectors.toList());
        List<DataSourceColumnDto> toAdd = columns.stream()
                .filter(dataSourceColumn -> !oldColumnNames.contains(dataSourceColumn.getName().toUpperCase()))
                .collect(Collectors.toList());
        if (!toRemove.isEmpty()) {
            this.product.removeColumns(new HashSet<>(toRemove));
        }
        if (!toAdd.isEmpty()) {
            convertDataSourceColumnFormToDataSourceColumn(this.product, dataSourceColumns, toAdd);
            this.product.addColumns(dataSourceColumns);
        }
        this.product.setDataFile(dataFileRepository.findOne(dataFileDto.getId()));
        return this;
    }

    @Override
    public FileDataSourceBuilder filter(String filter) {
        this.product.setFilter(filter);
        return this;
    }

    private void convertDataSourceColumnFormToDataSourceColumn(FileDataSource databaseDataSource,
                                                               Set<DataSourceColumn> dataSourceColumns,
                                                               List<DataSourceColumnDto> toAdd) {
        for (DataSourceColumnDto dataSourceColumnDto : toAdd) {
            DataSourceColumn dataSourceColumn = new DataSourceColumn();
            dataSourceColumn.setName(dataSourceColumnDto.getName());
            dataSourceColumn.setPosition(dataSourceColumnDto.getPosition());
            dataSourceColumn.setDataSource(databaseDataSource);
            dataSourceColumn.setDataValueType(dataSourceColumnDto.getDataValueType());
            dataSourceColumns.add(dataSourceColumn);
        }
    }
}
