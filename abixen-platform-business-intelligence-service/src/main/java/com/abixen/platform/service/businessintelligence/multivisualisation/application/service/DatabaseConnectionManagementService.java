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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service;

import com.abixen.platform.service.businessintelligence.multivisualisation.application.converter.DataSourceColumnToDataSourceColumnDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.converter.DatabaseConnectionToDatabaseConnectionDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DatabaseConnectionForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.database.DatabaseFactory;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.database.DatabaseService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.connection.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.DatabaseConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;


@Slf4j
@Service
public class DatabaseConnectionManagementService {

    private final DatabaseConnectionService databaseConnectionService;
    private final DatabaseFactory databaseFactory;
    private final DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter;
    private final DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter;

    @Autowired
    public DatabaseConnectionManagementService(DatabaseConnectionService databaseConnectionService,
                                               DatabaseFactory databaseFactory,
                                               DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter,
                                               DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter) {
        this.databaseConnectionService = databaseConnectionService;
        this.databaseFactory = databaseFactory;
        this.databaseConnectionToDatabaseConnectionDtoConverter = databaseConnectionToDatabaseConnectionDtoConverter;
        this.dataSourceColumnToDataSourceColumnDtoConverter = dataSourceColumnToDataSourceColumnDtoConverter;
    }

    public DatabaseConnectionDto findDatabaseConnection(final Long id) {
        log.debug("findDatabaseConnection() - id: {}", id);

        final DatabaseConnection databaseConnection = databaseConnectionService.find(id);

        return databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection);
    }

    public Page<DatabaseConnectionDto> findAllDatabaseConnections(final Pageable pageable) {
        log.debug("findAllDatabaseConnections() - pageable: {}", pageable);

        final Page<DatabaseConnection> databaseConnections = databaseConnectionService.findAll(pageable);

        return databaseConnectionToDatabaseConnectionDtoConverter.convertToPage(databaseConnections);
    }

    public DatabaseConnectionForm createDatabaseConnection(final DatabaseConnectionForm databaseConnectionForm) {
        log.debug("createDatabaseConnection() - databaseConnectionForm: {}", databaseConnectionForm);

        final DatabaseConnection databaseConnection = DatabaseConnection.builder()
                .credentials(databaseConnectionForm.getUsername(), databaseConnectionForm.getPassword())
                .database(databaseConnectionForm.getDatabaseType(),
                        databaseConnectionForm.getDatabaseHost(),
                        databaseConnectionForm.getDatabasePort(),
                        databaseConnectionForm.getDatabaseName())
                .details(databaseConnectionForm.getName(), databaseConnectionForm.getDescription())
                .build();

        final DatabaseConnection createdDatabaseConnection = databaseConnectionService.create(databaseConnection);
        final DatabaseConnectionDto createdDatabaseConnectionDto = databaseConnectionToDatabaseConnectionDtoConverter.convert(createdDatabaseConnection);

        return new DatabaseConnectionForm(createdDatabaseConnectionDto);
    }

    public DatabaseConnectionForm updateDatabaseConnection(final DatabaseConnectionForm databaseConnectionForm) {
        log.debug("updateDatabaseConnection() - databaseConnectionForm: {}", databaseConnectionForm);

        final DatabaseConnection databaseConnection = databaseConnectionService.find(databaseConnectionForm.getId());

        databaseConnection.changeCredentials(databaseConnectionForm.getUsername(), databaseConnectionForm.getPassword());
        databaseConnection.changeDatabase(databaseConnectionForm.getDatabaseType(),
                databaseConnectionForm.getDatabaseHost(),
                databaseConnectionForm.getDatabasePort(),
                databaseConnectionForm.getDatabaseName());
        databaseConnection.changeDetails(databaseConnectionForm.getName(), databaseConnectionForm.getDescription());

        final DatabaseConnection updatedDatabaseConnection = databaseConnectionService.update(databaseConnection);
        final DatabaseConnectionDto updatedDatabaseConnectionDto = databaseConnectionToDatabaseConnectionDtoConverter.convert(updatedDatabaseConnection);

        return new DatabaseConnectionForm(updatedDatabaseConnectionDto);
    }

    public void deleteDatabaseConnection(final Long id) {
        log.debug("deleteDatabaseConnection() - id: {}", id);

        databaseConnectionService.delete(id);
    }

    public void testDatabaseConnection(final DatabaseConnectionForm databaseConnectionForm) {
        log.debug("testDatabaseConnection() - databaseConnectionForm: {}", databaseConnectionForm);
        databaseFactory.getDatabaseService(databaseConnectionForm.getDatabaseType()).getConnection(databaseConnectionForm);
    }

    public List<String> findTablesInDatabase(final Long databaseConnectionId) {
        log.debug("findTablesInDatabase() - databaseConnectionId: {}", databaseConnectionId);

        final DatabaseConnection databaseConnection = databaseConnectionService.find(databaseConnectionId);
        final DatabaseService databaseService = databaseFactory.getDatabaseService(databaseConnection.getDatabaseType());
        final Connection connection = databaseService.getConnection(databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection));

        return databaseService.findTables(connection);
    }

    public List<DataSourceColumnDto> findTableColumnsFromDatabase(final Long databaseConnectionId, final String table) {
        log.debug("findTableColumnsFromDatabase() - databaseConnectionId: {}, table: {}", databaseConnectionId, table);

        final DatabaseConnection databaseConnection = databaseConnectionService.find(databaseConnectionId);
        final DatabaseService databaseService = databaseFactory.getDatabaseService(databaseConnection.getDatabaseType());
        final Connection connection = databaseService.getConnection(databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection));
        final List<DataSourceColumn> tableColumns = databaseService.findColumns(connection, table);

        return dataSourceColumnToDataSourceColumnDtoConverter.convertToList(tableColumns);
    }

}