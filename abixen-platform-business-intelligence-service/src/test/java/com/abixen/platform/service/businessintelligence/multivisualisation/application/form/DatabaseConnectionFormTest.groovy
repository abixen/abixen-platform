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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.form

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseConnectionDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.connection.DatabaseType
import spock.lang.Specification

class DatabaseConnectionFormTest extends Specification {

    void "should build DatabaseConnectionForm from DatabaseConnectionDto"() {
        given:
        final DatabaseConnectionDto databaseConnectionDto = new DatabaseConnectionDto()
                .setId(1L)
                .setName("name")
                .setDescription("description")
                .setDatabaseHost("databaseHost")
                .setDatabaseName("databaseName")
                .setDatabasePort(3423)
                .setDatabaseType(DatabaseType.H2)
                .setUsername("username")
                .setPassword("password")

        when:
        final DatabaseConnectionForm databaseConnectionForm = new DatabaseConnectionForm(databaseConnectionDto)

        then:
        databaseConnectionForm != null
        databaseConnectionForm.getId() == databaseConnectionDto.getId()
        databaseConnectionForm.getName() == databaseConnectionDto.getName()
        databaseConnectionForm.getDescription() == databaseConnectionDto.getDescription()
        databaseConnectionForm.getDatabaseHost() == databaseConnectionDto.getDatabaseHost()
        databaseConnectionForm.getDatabaseName() == databaseConnectionDto.getDatabaseName()
        databaseConnectionForm.getDatabasePort() == databaseConnectionDto.getDatabasePort()
        databaseConnectionForm.getDatabaseType() == databaseConnectionDto.getDatabaseType()
        databaseConnectionForm.getUsername() == databaseConnectionDto.getUsername()
        databaseConnectionForm.getPassword() == databaseConnectionDto.getPassword()

        0 * _
    }
}
