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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.impl

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.connection.DatabaseType
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.connection.DatabaseConnection
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DatabaseConnectionRepository
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.DatabaseConnectionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class DatabaseConnectionServiceImplTest extends Specification {

    private DatabaseConnectionRepository databaseConnectionRepository
    private DatabaseConnectionService databaseConnectionService

    void setup() {
        databaseConnectionRepository = Mock()
        databaseConnectionService = new DatabaseConnectionService(databaseConnectionRepository)
    }

    void "should find DatabaseConnection"() {
        given:
        final Long id = 1L

        final DatabaseConnection databaseConnection = [] as DatabaseConnection

        databaseConnectionRepository.findOne(id) >> databaseConnection

        when:
        final DatabaseConnection foundDatabaseConnection = databaseConnectionService.find(id)

        then:
        foundDatabaseConnection != null
        foundDatabaseConnection == databaseConnection

        1 * databaseConnectionRepository.findOne(id) >> databaseConnection
        0 * _
    }

    void "should findAll DatabaseConnection"() {
        given:
        final Pageable pageable = new PageRequest(0, 1)

        final DatabaseConnection databaseConnection = [] as DatabaseConnection
        final Page<DatabaseConnection> databaseConnections = new PageImpl<DatabaseConnection>([databaseConnection])

        databaseConnectionRepository.findAll(pageable) >> databaseConnections

        when:
        final Page<DatabaseConnection> foundDatabaseConnections = databaseConnectionService.findAll(pageable)

        then:
        foundDatabaseConnections != null
        foundDatabaseConnections == databaseConnections

        1 * databaseConnectionRepository.findAll(pageable) >> databaseConnections
        0 * _
    }

    void "should create DatabaseConnection"() {
        given:
        final DatabaseConnection databaseConnection = DatabaseConnection.builder()
                .credentials("username", "password")
                .database(DatabaseType.H2, "databaseHost", 1, "databaseName")
                .details("name", "description")
                .build()

        databaseConnectionRepository.save(databaseConnection) >> databaseConnection

        when:
        final DatabaseConnection createdDatabaseConnection = databaseConnectionService.create(databaseConnection)

        then:
        createdDatabaseConnection != null
        createdDatabaseConnection.getId() == databaseConnection.getId()
        createdDatabaseConnection.getName() == databaseConnection.getName()
        createdDatabaseConnection.getDescription() == databaseConnection.getDescription()
        createdDatabaseConnection.getDatabaseHost() == databaseConnection.getDatabaseHost()
        createdDatabaseConnection.getDatabaseName() == databaseConnection.getDatabaseName()
        createdDatabaseConnection.getDatabasePort() == databaseConnection.getDatabasePort()
        createdDatabaseConnection.getDatabaseType() == databaseConnection.getDatabaseType()
        createdDatabaseConnection.getUsername() == databaseConnection.getUsername()
        createdDatabaseConnection.getPassword() == databaseConnection.getPassword()

        1 * databaseConnectionRepository.save(databaseConnection) >> databaseConnection
        0 * _
    }

    void "should update DatabaseConnection"() {
        given:
        final DatabaseConnection databaseConnection = DatabaseConnection.builder()
                .credentials("username", "password")
                .database(DatabaseType.H2, "databaseHost", 1, "databaseName")
                .details("name", "description")
                .build()

        databaseConnectionRepository.save(databaseConnection) >> databaseConnection

        when:
        final DatabaseConnection createdDatabaseConnection = databaseConnectionService.update(databaseConnection)

        then:
        createdDatabaseConnection != null
        createdDatabaseConnection.getId() == databaseConnection.getId()
        createdDatabaseConnection.getName() == databaseConnection.getName()
        createdDatabaseConnection.getDescription() == databaseConnection.getDescription()
        createdDatabaseConnection.getDatabaseHost() == databaseConnection.getDatabaseHost()
        createdDatabaseConnection.getDatabaseName() == databaseConnection.getDatabaseName()
        createdDatabaseConnection.getDatabasePort() == databaseConnection.getDatabasePort()
        createdDatabaseConnection.getDatabaseType() == databaseConnection.getDatabaseType()
        createdDatabaseConnection.getUsername() == databaseConnection.getUsername()
        createdDatabaseConnection.getPassword() == databaseConnection.getPassword()

        1 * databaseConnectionRepository.save(databaseConnection) >> databaseConnection
        0 * _
    }

    void "should delete DatabaseConnection"() {
        given:
        final Long id = 1L


        when:
        databaseConnectionService.delete(id)

        then:
        1 * databaseConnectionRepository.delete(id)
        0 * _
    }
}
