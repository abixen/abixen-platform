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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service.impl;

import com.abixen.platform.service.businessintelligence.multivisualisation.application.converter.DataSourceColumnToDataSourceColumnDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DatabaseConnectionManagementService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.database.DatabaseConnectionBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.converter.DatabaseConnectionToDatabaseConnectionDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DatabaseConnectionForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.database.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.database.DatabaseFactory;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.database.DatabaseService;
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
public class DatabaseConnectionManagementServiceImpl implements DatabaseConnectionManagementService {

    private final DatabaseConnectionService databaseConnectionService;
    private final DatabaseFactory databaseFactory;
    private final DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter;
    private final DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter;

    @Autowired
    public DatabaseConnectionManagementServiceImpl(DatabaseConnectionService databaseConnectionService,
                                                   DatabaseFactory databaseFactory,
                                                   DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter,
                                                   DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter) {
        this.databaseConnectionService = databaseConnectionService;
        this.databaseFactory = databaseFactory;
        this.databaseConnectionToDatabaseConnectionDtoConverter = databaseConnectionToDatabaseConnectionDtoConverter;
        this.dataSourceColumnToDataSourceColumnDtoConverter = dataSourceColumnToDataSourceColumnDtoConverter;
    }

    @Override
    public DatabaseConnectionDto findDatabaseConnection(final Long id) {
        log.debug("findDatabaseConnection() - id: {}", id);

        final DatabaseConnection databaseConnection = databaseConnectionService.find(id);

        return databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection);
    }

    @Override
    public Page<DatabaseConnectionDto> findAllDatabaseConnections(final Pageable pageable) {
        log.debug("findAllDatabaseConnections() - pageable: {}", pageable);

        final Page<DatabaseConnection> databaseConnections = databaseConnectionService.findAll(pageable);

        return databaseConnectionToDatabaseConnectionDtoConverter.convertToPage(databaseConnections);
    }

    @Override
    public DatabaseConnectionDto createDatabaseConnection(final DatabaseConnectionForm databaseConnectionForm) {
        log.debug("createDatabaseConnection() - databaseConnectionForm: {}", databaseConnectionForm);

        final DatabaseConnection databaseConnection = databaseConnectionService.create(build(databaseConnectionForm));

        return databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection);
    }

    @Override
    public DatabaseConnectionDto updateDatabaseConnection(final DatabaseConnectionForm databaseConnectionForm) {
        log.debug("updateDatabaseConnection() - databaseConnectionForm: {}", databaseConnectionForm);

        final DatabaseConnection databaseConnection = databaseConnectionService.find(databaseConnectionForm.getId());

        databaseConnection.changeCredentials(databaseConnectionForm.getUsername(), databaseConnectionForm.getPassword());
        databaseConnection.changeDatabase(databaseConnectionForm.getDatabaseType(),
                databaseConnectionForm.getDatabaseHost(),
                databaseConnectionForm.getDatabasePort(),
                databaseConnectionForm.getDatabaseName());
        databaseConnection.changeDetails(databaseConnectionForm.getName(), databaseConnectionForm.getDescription());

        final DatabaseConnection updatedDatabaseConnection = databaseConnectionService.update(databaseConnection);

        return databaseConnectionToDatabaseConnectionDtoConverter.convert(updatedDatabaseConnection);
    }

    @Override
    public void deleteDatabaseConnection(final Long id) {
        log.debug("deleteDatabaseConnection() - id: {}", id);

        databaseConnectionService.delete(id);
    }

    public DatabaseConnection build(final DatabaseConnectionForm databaseConnectionForm) {
        log.debug("build() - databaseConnectionForm: {}", databaseConnectionForm);

        return new DatabaseConnectionBuilder()
                .credentials(databaseConnectionForm.getUsername(), databaseConnectionForm.getPassword())
                .database(databaseConnectionForm.getDatabaseType(),
                        databaseConnectionForm.getDatabaseHost(),
                        databaseConnectionForm.getDatabasePort(),
                        databaseConnectionForm.getDatabaseName())
                .details(databaseConnectionForm.getName(), databaseConnectionForm.getDescription())
                .build();
    }

    @Override
    public void testDatabaseConnection(final DatabaseConnectionForm databaseConnectionForm) {
        log.debug("testDatabaseConnection() - databaseConnectionForm: {}", databaseConnectionForm);
        databaseFactory.getDatabaseService(databaseConnectionForm.getDatabaseType()).getConnection(databaseConnectionForm);
    }

    @Override
    public List<String> findTablesInDatabase(final Long databaseConnectionId) {
        log.debug("findTablesInDatabase() - databaseConnectionId: {}", databaseConnectionId);

        final DatabaseConnection databaseConnection = databaseConnectionService.find(databaseConnectionId);
        final DatabaseService databaseService = databaseFactory.getDatabaseService(databaseConnection.getDatabaseType());
        final Connection connection = databaseService.getConnection(databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection));

        return databaseService.findTables(connection);
    }

    @Override
    public List<DataSourceColumnDto> findTableColumnsFromDatabase(final Long databaseConnectionId, final String table) {
        log.debug("findTableColumnsFromDatabase() - databaseConnectionId: {}, table: {}", databaseConnectionId, table);

        final DatabaseConnection databaseConnection = databaseConnectionService.find(databaseConnectionId);
        final DatabaseService databaseService = databaseFactory.getDatabaseService(databaseConnection.getDatabaseType());
        final Connection connection = databaseService.getConnection(databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection));
        final List<DataSourceColumn> tableColumns = databaseService.findColumns(connection, table);

        return dataSourceColumnToDataSourceColumnDtoConverter.convertToList(tableColumns);
    }

}
