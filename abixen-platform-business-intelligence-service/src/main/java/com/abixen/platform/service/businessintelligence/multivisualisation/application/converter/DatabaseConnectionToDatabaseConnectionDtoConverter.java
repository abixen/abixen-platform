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
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.connection.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DatabaseConnectionToDatabaseConnectionDtoConverter extends AbstractConverter<DatabaseConnection, DatabaseConnectionDto> {

    private final AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter;

    @Autowired
    public DatabaseConnectionToDatabaseConnectionDtoConverter(AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter) {
        this.auditingModelToSimpleAuditingDtoConverter = auditingModelToSimpleAuditingDtoConverter;
    }

    @Override
    public DatabaseConnectionDto convert(DatabaseConnection databaseConnection, Map<String, Object> parameters) {
        if (databaseConnection == null) {
            return null;
        }

        DatabaseConnectionDto databaseConnectionDto = new DatabaseConnectionDto();

        databaseConnectionDto
                .setId(databaseConnection.getId())
                .setName(databaseConnection.getName())
                .setDatabaseHost(databaseConnection.getDatabaseHost())
                .setDatabaseName(databaseConnection.getDatabaseName())
                .setDatabasePort(databaseConnection.getDatabasePort())
                .setDatabaseType(databaseConnection.getDatabaseType())
                .setDescription(databaseConnection.getDescription())
                .setUsername(databaseConnection.getUsername())
                .setPassword(databaseConnection.getPassword());

        auditingModelToSimpleAuditingDtoConverter.convert(databaseConnection, databaseConnectionDto);

        return databaseConnectionDto;
    }
}
