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

package com.abixen.platform.service.businessintelligence.multivisualisation.service.impl;

import com.abixen.platform.service.businessintelligence.multivisualisation.converter.DatabaseConnectionToDatabaseConnectionDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.form.DatabaseConnectionForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.database.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataSourceColumnWeb;
import com.abixen.platform.service.businessintelligence.multivisualisation.repository.DatabaseConnectionRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DatabaseConnectionService;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DatabaseFactory;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;


@Slf4j
@Service
public class DatabaseConnectionServiceImpl implements DatabaseConnectionService {

    private DatabaseConnectionRepository dataSourceConnectionRepository;
    private DatabaseFactory databaseFactory;
    private DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter;

    @Autowired
    public DatabaseConnectionServiceImpl(DatabaseConnectionRepository dataSourceConnectionRepository, DatabaseFactory databaseFactory, DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter) {
        this.dataSourceConnectionRepository = dataSourceConnectionRepository;
        this.databaseFactory = databaseFactory;
        this.databaseConnectionToDatabaseConnectionDtoConverter = databaseConnectionToDatabaseConnectionDtoConverter;
    }

    @Override
    public Page<DatabaseConnection> findAllDatabaseConnections(Pageable pageable) {
        return dataSourceConnectionRepository.findAll(pageable);
    }

    @Override
    public Page<DatabaseConnectionDto> findAllDatabaseConnectionsAsDto(Pageable pageable) {
        return databaseConnectionToDatabaseConnectionDtoConverter.convertToPage(findAllDatabaseConnections(pageable));
    }

    @Override
    public DatabaseConnection findDatabaseConnection(Long id) {
       return dataSourceConnectionRepository.getOne(id);
    }

    @Override
    public DatabaseConnectionDto findDatabaseConnectionAsDto(Long id) {
        return databaseConnectionToDatabaseConnectionDtoConverter.convert(findDatabaseConnection(id));
    }

    @Override
    public void deleteDatabaseConnection(Long id) {
        dataSourceConnectionRepository.delete(id);
    }

    @Override
    public DatabaseConnection buildDatabaseConnection(DatabaseConnectionForm databaseConnectionForm) {
        log.debug("buildDatabaseConnection() - databaseConnectionForm: " + databaseConnectionForm);

        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.setName(databaseConnectionForm.getName());
        databaseConnection.setDatabaseType(databaseConnectionForm.getDatabaseType());
        databaseConnection.setDatabaseHost(databaseConnectionForm.getDatabaseHost());
        databaseConnection.setDatabasePort(databaseConnectionForm.getDatabasePort());
        databaseConnection.setDatabaseName(databaseConnectionForm.getDatabaseName());
        databaseConnection.setDescription(databaseConnectionForm.getDescription());
        databaseConnection.setPassword(databaseConnectionForm.getPassword());
        databaseConnection.setUsername(databaseConnectionForm.getUsername());

        return databaseConnection;
    }

    @Override
    public DatabaseConnectionForm createDatabaseConnection(DatabaseConnectionForm databaseConnectionForm) {
        DatabaseConnection databaseConnection = buildDatabaseConnection(databaseConnectionForm);
        return new DatabaseConnectionForm(updateDatabaseConnection(createDatabaseConnection(databaseConnection)));
    }

    @Override
    public DatabaseConnectionForm updateDatabaseConnection(DatabaseConnectionForm databaseConnectionForm) {
        log.debug("updateDatabaseConnection() - databaseConnectionForm: " + databaseConnectionForm);

        DatabaseConnection databaseConnection = dataSourceConnectionRepository.findOne(databaseConnectionForm.getId());
        databaseConnection.setName(databaseConnectionForm.getName());
        databaseConnection.setDatabaseType(databaseConnectionForm.getDatabaseType());
        databaseConnection.setDatabaseHost(databaseConnectionForm.getDatabaseHost());
        databaseConnection.setDatabasePort(databaseConnectionForm.getDatabasePort());
        databaseConnection.setDatabaseName(databaseConnectionForm.getDatabaseName());
        databaseConnection.setDescription(databaseConnectionForm.getDescription());
        databaseConnection.setPassword(databaseConnectionForm.getPassword());
        databaseConnection.setUsername(databaseConnectionForm.getUsername());

        return new DatabaseConnectionForm(updateDatabaseConnection(databaseConnection));
    }

    @Override
    public DatabaseConnection createDatabaseConnection(DatabaseConnection databaseConnection) {
        log.debug("createDatabaseConnection() - databaseConnection: " + databaseConnection);
        return dataSourceConnectionRepository.save(databaseConnection);
    }

    @Override
    public DatabaseConnection updateDatabaseConnection(DatabaseConnection databaseConnection) {
        log.debug("updateDatabaseConnection() - databaseConnection: " + databaseConnection);
        return dataSourceConnectionRepository.save(databaseConnection);
    }

    @Override
    public void testDatabaseConnection(DatabaseConnectionForm databaseConnectionForm) {
        databaseFactory.getDatabaseService(databaseConnectionForm.getDatabaseType()).getConnection(databaseConnectionForm);
    }

    @Override
    public List<String> getTables(Long databaseConnectionId) {
        DatabaseConnection databaseConnection = dataSourceConnectionRepository.findOne(databaseConnectionId);
        DatabaseService databaseService = databaseFactory.getDatabaseService(databaseConnection.getDatabaseType());
        Connection connection = databaseService.getConnection(databaseConnection);
        return databaseService.getTables(connection);
    }

    @Override
    public List<DataSourceColumnWeb> getTableColumns(Long databaseConnectionId, String table) {
        DatabaseConnection databaseConnection = dataSourceConnectionRepository.findOne(databaseConnectionId);
        DatabaseService databaseService = databaseFactory.getDatabaseService(databaseConnection.getDatabaseType());
        Connection connection = databaseService.getConnection(databaseConnection);
        return databaseService.getColumns(connection, table);
    }

}
