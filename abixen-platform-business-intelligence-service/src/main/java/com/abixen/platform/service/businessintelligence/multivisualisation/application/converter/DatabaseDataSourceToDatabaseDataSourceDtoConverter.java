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
import com.abixen.platform.common.infrastructure.exception.PlatformRuntimeException;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseDataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.database.DatabaseDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DatabaseDataSourceToDatabaseDataSourceDtoConverter extends AbstractConverter<DatabaseDataSource, DatabaseDataSourceDto> {

    private final AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter;
    private final DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter;
    private final DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter;

    @Autowired
    public DatabaseDataSourceToDatabaseDataSourceDtoConverter(AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter,
                                                              DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter,
                                                              DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter) {
        this.auditingModelToSimpleAuditingDtoConverter = auditingModelToSimpleAuditingDtoConverter;
        this.databaseConnectionToDatabaseConnectionDtoConverter = databaseConnectionToDatabaseConnectionDtoConverter;
        this.dataSourceColumnToDataSourceColumnDtoConverter = dataSourceColumnToDataSourceColumnDtoConverter;
    }

    @Override
    public DatabaseDataSourceDto convert(DatabaseDataSource databaseDataSource, Map<String, Object> parameters) {
        if (databaseDataSource == null) {
            return null;
        }

        if (!DataSourceType.DB.equals(databaseDataSource.getDataSourceType())) {
            throw new PlatformRuntimeException("Invalid DataSource type for DatabaseDataSource");
        }

        final DatabaseDataSourceDto databaseDataSourceDto = new DatabaseDataSourceDto();
                        databaseDataSourceDto.setDatabaseConnection(databaseConnectionToDatabaseConnectionDtoConverter.convert(((DatabaseDataSource) databaseDataSource).getDatabaseConnection()))
                        .setFilter(databaseDataSource.getFilter())
                        .setTable(databaseDataSource.getTable())
                        .setId(databaseDataSource.getId())
                        .setDataSourceType(databaseDataSource.getDataSourceType())
                        .setColumns(dataSourceColumnToDataSourceColumnDtoConverter.convertToSet(databaseDataSource.getColumns()))
                        .setName(databaseDataSource.getName())
                        .setDescription(databaseDataSource.getDescription());

        auditingModelToSimpleAuditingDtoConverter.convert(databaseDataSource, databaseDataSourceDto);

        return databaseDataSourceDto;
    }
}