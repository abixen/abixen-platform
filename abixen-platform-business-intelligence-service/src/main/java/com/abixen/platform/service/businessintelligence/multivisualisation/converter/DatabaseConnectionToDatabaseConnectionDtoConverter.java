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
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.database.DatabaseConnection;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DatabaseConnectionToDatabaseConnectionDtoConverter extends AbstractConverter<DatabaseConnection, DatabaseConnectionDto> {

    @Override
    public DatabaseConnectionDto convert(DatabaseConnection databaseConnection, Map<String, Object> parameters) {
        return new DatabaseConnectionDto()
                .setId(databaseConnection.getId())
                .setName(databaseConnection.getName())
                .setDatabaseHost(databaseConnection.getDatabaseHost())
                .setDatabaseName(databaseConnection.getDatabaseName())
                .setDatabasePort(databaseConnection.getDatabasePort())
                .setDatabaseType(databaseConnection.getDatabaseType())
                .setDescription(databaseConnection.getDescription())
                .setUsername(databaseConnection.getUsername())
                .setPassword(databaseConnection.getPassword());
    }
}
