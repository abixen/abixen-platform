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

package com.abixen.platform.service.businessintelligence.multivisualisation.converter;

import com.abixen.platform.common.converter.AbstractConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DatabaseDataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.database.DatabaseDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DatabaseDataSourceToDatabaseDataSourceDtoConverter extends AbstractConverter<DatabaseDataSource, DatabaseDataSourceDto> {

    private DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter;
    private DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter;

    @Autowired
    public DatabaseDataSourceToDatabaseDataSourceDtoConverter(DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter,
                                                              DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter) {
        this.databaseConnectionToDatabaseConnectionDtoConverter = databaseConnectionToDatabaseConnectionDtoConverter;
        this.dataSourceColumnToDataSourceColumnDtoConverter = dataSourceColumnToDataSourceColumnDtoConverter;
    }

    @Override
    public DatabaseDataSourceDto convert(DatabaseDataSource databaseDataSource, Map<String, Object> parameters) {
        if (databaseDataSource == null) {
            return null;
        }

        DatabaseDataSourceDto databaseDataSourceDto = new DatabaseDataSourceDto();
        databaseDataSourceDto.setId(databaseDataSource.getId());
        databaseDataSourceDto.setName(databaseDataSource.getName());
        databaseDataSourceDto.setFilter(databaseDataSource.getFilter());
        databaseDataSourceDto.setDescription(databaseDataSource.getDescription());
        databaseDataSourceDto.setColumns(dataSourceColumnToDataSourceColumnDtoConverter.convertToSet(databaseDataSource.getColumns()));
        databaseDataSourceDto.setDatabaseConnection(databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseDataSource.getDatabaseConnection()));
        databaseDataSourceDto.setFilter(databaseDataSource.getFilter());
        databaseDataSourceDto.setTable(databaseDataSource.getTable());
        return databaseDataSourceDto;
    }
}
