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
import com.abixen.platform.common.exception.PlatformRuntimeException;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DatabaseDataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.FileDataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.file.FileDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataSourceToDataSourceDtoConverter extends AbstractConverter<DataSource, DataSourceDto> {

    private final DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter;
    private final DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter;
    private final FileDataSourceRowToFileDataSourceRowDtoConverter fileDataSourceRowToFileDataSourceRowDtoConverter;
    private final DataFileToDataFileDtoConverter dataFileToDataFileDtoConverter;

    @Autowired
    public DataSourceToDataSourceDtoConverter(DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter,
                                              DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter,
                                              FileDataSourceRowToFileDataSourceRowDtoConverter fileDataSourceRowToFileDataSourceRowDtoConverter,
                                              DataFileToDataFileDtoConverter dataFileToDataFileDtoConverter) {
        this.databaseConnectionToDatabaseConnectionDtoConverter = databaseConnectionToDatabaseConnectionDtoConverter;
        this.dataSourceColumnToDataSourceColumnDtoConverter = dataSourceColumnToDataSourceColumnDtoConverter;
        this.fileDataSourceRowToFileDataSourceRowDtoConverter = fileDataSourceRowToFileDataSourceRowDtoConverter;
        this.dataFileToDataFileDtoConverter = dataFileToDataFileDtoConverter;
    }

    @Override
    public DataSourceDto convert(DataSource dataSource, Map<String, Object> parameters) {
        if (dataSource == null) {
            return null;
        }

        switch (dataSource.getDataSourceType()) {
            case DB: return new DatabaseDataSourceDto()
                    .setDatabaseConnection(databaseConnectionToDatabaseConnectionDtoConverter.convert(((DatabaseDataSource) dataSource).getDatabaseConnection()))
                    .setFilter(((DatabaseDataSource) dataSource).getFilter())
                    .setTable(((DatabaseDataSource) dataSource).getTable())
                    .setId(dataSource.getId())
                    .setDataSourceType(dataSource.getDataSourceType())
                    .setColumns(dataSourceColumnToDataSourceColumnDtoConverter.convertToSet(dataSource.getColumns()))
                    .setName(dataSource.getName())
                    .setDescription(dataSource.getDescription());
            case FILE: return new FileDataSourceDto()
                    .setDataFile(dataFileToDataFileDtoConverter.convert(((FileDataSource) dataSource).getDataFile()))
                    .setRows(fileDataSourceRowToFileDataSourceRowDtoConverter.convertToSet(((FileDataSource) dataSource).getRows()))
                    .setFilter(((FileDataSource) dataSource).getFilter())
                    .setId(dataSource.getId())
                    .setDataSourceType(dataSource.getDataSourceType())
                    .setColumns(dataSourceColumnToDataSourceColumnDtoConverter.convertToSet(dataSource.getColumns()))
                    .setName(dataSource.getName())
                    .setDescription(dataSource.getDescription());
            default: throw new PlatformRuntimeException("DataSource type not supported");
        }
    }
}
