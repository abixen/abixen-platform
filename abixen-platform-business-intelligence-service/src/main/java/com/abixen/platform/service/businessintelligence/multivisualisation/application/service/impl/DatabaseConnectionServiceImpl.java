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

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.database.DatabaseConnectionBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.web.facade.converter.DatabaseConnectionToDatabaseConnectionDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DatabaseConnectionForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.database.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DatabaseConnectionRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DatabaseConnectionService;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DatabaseFactory;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DatabaseService;
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

    private final DatabaseConnectionRepository dataSourceConnectionRepository;
    private final DatabaseFactory databaseFactory;
    private final DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter;

    @Autowired
    public DatabaseConnectionServiceImpl(DatabaseConnectionRepository dataSourceConnectionRepository, DatabaseFactory databaseFactory, DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter) {
        this.dataSourceConnectionRepository = dataSourceConnectionRepository;
        this.databaseFactory = databaseFactory;
        this.databaseConnectionToDatabaseConnectionDtoConverter = databaseConnectionToDatabaseConnectionDtoConverter;
    }

    @Override
    public DatabaseConnection find(Long id) {
        return dataSourceConnectionRepository.getOne(id);
    }

    @Override
    public Page<DatabaseConnection> findAll(Pageable pageable) {
        return dataSourceConnectionRepository.findAll(pageable);
    }

    @Override
    public DatabaseConnection create(DatabaseConnectionForm databaseConnectionForm) {
        DatabaseConnection databaseConnection = build(databaseConnectionForm);
        return update(create(databaseConnection));
    }

    @Override
    public DatabaseConnection create(DatabaseConnection databaseConnection) {
        log.debug("create() - databaseConnection: " + databaseConnection);
        return dataSourceConnectionRepository.save(databaseConnection);
    }

    @Override
    public DatabaseConnection update(DatabaseConnectionForm databaseConnectionForm) {
        log.debug("update() - databaseConnectionForm: " + databaseConnectionForm);

        DatabaseConnection databaseConnection = dataSourceConnectionRepository.findOne(databaseConnectionForm.getId());

        databaseConnection.changeCredentials(databaseConnectionForm.getUsername(), databaseConnectionForm.getPassword());
        databaseConnection.changeDatabase(databaseConnectionForm.getDatabaseType(),
                databaseConnectionForm.getDatabaseHost(),
                databaseConnectionForm.getDatabasePort(),
                databaseConnectionForm.getDatabaseName());
        databaseConnection.changeDetails(databaseConnectionForm.getName(), databaseConnectionForm.getDescription());

        return update(databaseConnection);
    }

    @Override
    public DatabaseConnection update(DatabaseConnection databaseConnection) {
        log.debug("update() - databaseConnection: " + databaseConnection);
        return dataSourceConnectionRepository.save(databaseConnection);
    }

    @Override
    public void delete(Long id) {
        dataSourceConnectionRepository.delete(id);
    }

    @Override
    public DatabaseConnection build(DatabaseConnectionForm databaseConnectionForm) {
        log.debug("build() - databaseConnectionForm: " + databaseConnectionForm);

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
    public void testConnection(DatabaseConnectionForm databaseConnectionForm) {
        databaseFactory.getDatabaseService(databaseConnectionForm.getDatabaseType()).getConnection(databaseConnectionForm);
    }

    @Override
    public List<String> getTables(Long databaseConnectionId) {
        DatabaseConnection databaseConnection = dataSourceConnectionRepository.findOne(databaseConnectionId);
        DatabaseService databaseService = databaseFactory.getDatabaseService(databaseConnection.getDatabaseType());
        Connection connection = databaseService.getConnection(databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection));
        return databaseService.getTables(connection);
    }

    @Override
    public List<DataSourceColumn> getTableColumns(Long databaseConnectionId, String table) {
        DatabaseConnection databaseConnection = dataSourceConnectionRepository.findOne(databaseConnectionId);
        DatabaseService databaseService = databaseFactory.getDatabaseService(databaseConnection.getDatabaseType());
        Connection connection = databaseService.getConnection(databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection));
        return databaseService.getColumns(connection, table);
    }

}
