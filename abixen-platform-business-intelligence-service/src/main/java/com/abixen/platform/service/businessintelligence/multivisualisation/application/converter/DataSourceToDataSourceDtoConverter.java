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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.converter;

import com.abixen.platform.common.application.converter.AbstractConverter;
import com.abixen.platform.common.application.converter.AuditingModelToSimpleAuditingDtoConverter;
import com.abixen.platform.common.exception.PlatformRuntimeException;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseDataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.FileDataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.file.FileDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataSourceToDataSourceDtoConverter extends AbstractConverter<DataSource, DataSourceDto> {

    private final AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter;
    private final DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter;
    private final DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter;
    private final FileDataSourceRowToFileDataSourceRowDtoConverter fileDataSourceRowToFileDataSourceRowDtoConverter;
    private final DataFileToDataFileDtoConverter dataFileToDataFileDtoConverter;

    @Autowired
    public DataSourceToDataSourceDtoConverter(AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter,
                                              DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter,
                                              DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter,
                                              FileDataSourceRowToFileDataSourceRowDtoConverter fileDataSourceRowToFileDataSourceRowDtoConverter,
                                              DataFileToDataFileDtoConverter dataFileToDataFileDtoConverter) {
        this.auditingModelToSimpleAuditingDtoConverter = auditingModelToSimpleAuditingDtoConverter;
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

        DataSourceDto dataSourceDto = null;

        switch (dataSource.getDataSourceType()) {
            case DB:
                dataSourceDto = new DatabaseDataSourceDto();
                ((DatabaseDataSourceDto) dataSourceDto).setDatabaseConnection(databaseConnectionToDatabaseConnectionDtoConverter.convert(((DatabaseDataSource) dataSource).getDatabaseConnection()))
                        .setFilter((dataSource).getFilter())
                        .setTable(((DatabaseDataSource) dataSource).getTable())
                        .setId(dataSource.getId())
                        .setDataSourceType(dataSource.getDataSourceType())
                        .setColumns(dataSourceColumnToDataSourceColumnDtoConverter.convertToSet(dataSource.getColumns()))
                        .setName(dataSource.getName())
                        .setDescription(dataSource.getDescription());
                break;
            case FILE:
                dataSourceDto = new FileDataSourceDto();
                ((FileDataSourceDto) dataSourceDto).setDataFile(dataFileToDataFileDtoConverter.convert(((FileDataSource) dataSource).getDataFile()))
                        .setRows(fileDataSourceRowToFileDataSourceRowDtoConverter.convertToSet(((FileDataSource) dataSource).getRows()))
                        .setFilter((dataSource).getFilter())
                        .setId(dataSource.getId())
                        .setDataSourceType(dataSource.getDataSourceType())
                        .setColumns(dataSourceColumnToDataSourceColumnDtoConverter.convertToSet(dataSource.getColumns()))
                        .setName(dataSource.getName())
                        .setDescription(dataSource.getDescription());
                break;
            default:
                throw new PlatformRuntimeException("DataSource type not supported");
        }
        auditingModelToSimpleAuditingDtoConverter.convert(dataSource, dataSourceDto);

        return dataSourceDto;
    }
}