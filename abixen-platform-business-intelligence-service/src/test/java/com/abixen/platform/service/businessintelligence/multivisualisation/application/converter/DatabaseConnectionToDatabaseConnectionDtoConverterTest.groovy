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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.converter

import com.abixen.platform.common.application.converter.AuditingModelToSimpleAuditingDtoConverter
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseConnectionDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.connection.DatabaseType
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.connection.DatabaseConnection
import spock.lang.Specification

class DatabaseConnectionToDatabaseConnectionDtoConverterTest extends Specification {

    private AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter
    private DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter;

    void setup() {
        auditingModelToSimpleAuditingDtoConverter = Mock()
        databaseConnectionToDatabaseConnectionDtoConverter = new DatabaseConnectionToDatabaseConnectionDtoConverter(auditingModelToSimpleAuditingDtoConverter)
    }

    void "should return null when DatabaseConnection is null"() {
        given:
        final DatabaseConnection databaseConnection = null

        when:
        final DatabaseConnectionDto databaseConnectionDto = databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection)

        then:
        databaseConnectionDto == null
    }

    void "should convert DatabaseConnection to DatabaseConnectionDto"() {
        given:
        final DatabaseConnection databaseConnection = DatabaseConnection.builder()
            .credentials("username", "password")
            .database(DatabaseType.H2, "databaseHost", 1, "databaseName")
            .details("name", "description")
            .build()

        when:
        final DatabaseConnectionDto databaseConnectionDto = databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection)

        then:
        databaseConnectionDto != null
        databaseConnectionDto.getId() == databaseConnection.getId()
        databaseConnectionDto.getName() == databaseConnection.getName()
        databaseConnectionDto.getDescription() == databaseConnection.getDescription()
        databaseConnectionDto.getDatabaseHost() == databaseConnection.getDatabaseHost()
        databaseConnectionDto.getDatabaseName() == databaseConnection.getDatabaseName()
        databaseConnectionDto.getDatabasePort() == databaseConnection.getDatabasePort()
        databaseConnectionDto.getDatabaseType() == databaseConnection.getDatabaseType()
        databaseConnectionDto.getUsername() == databaseConnection.getUsername()
        databaseConnectionDto.getPassword() == databaseConnection.getPassword()

        1 * auditingModelToSimpleAuditingDtoConverter.convert(_ , _)
        0 * _
    }
}
