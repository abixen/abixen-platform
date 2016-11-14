/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.module.chart.service.impl;

import com.abixen.platform.module.chart.form.DatabaseConnectionForm;
import com.abixen.platform.module.chart.model.enumtype.DatabaseType;
import com.abixen.platform.module.chart.model.impl.DatabaseConnection;
import com.abixen.platform.module.chart.repository.DatabaseConnectionRepository;
import com.abixen.platform.module.chart.service.DatabaseConnectionService;
import com.abixen.platform.module.chart.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.util.List;

import static com.abixen.platform.module.chart.model.enumtype.DatabaseType.POSTGRES;


@Service
public class DatabaseConnectionServiceImpl implements DatabaseConnectionService {

    private final Logger log = LoggerFactory.getLogger(DatabaseConnectionServiceImpl.class);

    @Resource
    private DatabaseConnectionRepository dataSourceConnectionRepository;

    @Autowired
    @Qualifier("POSTGRES")
    private DatabaseService databasePostgresService;

    @Autowired
    @Qualifier("H2")
    private DatabaseService databaseH2Service;

    //@Autowired
    //KpiChartConfigurationDomainBuilderService kpiChartConfigurationDomainBuilderService;

    @Override
    public Page<DatabaseConnection> findAllDatabaseConnections(Pageable pageable) {
        return dataSourceConnectionRepository.findAll(pageable);
    }

    @Override
    public DatabaseConnection findDatabaseConnection(Long id) {
        return dataSourceConnectionRepository.getOne(id);
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
        DatabaseConnection createdDatabaseConnection = dataSourceConnectionRepository.save(databaseConnection);
        return createdDatabaseConnection;
    }

    @Override
    public DatabaseConnection updateDatabaseConnection(DatabaseConnection databaseConnection) {
        log.debug("updateDatabaseConnection() - databaseConnection: " + databaseConnection);
        return dataSourceConnectionRepository.save(databaseConnection);
    }

    @Override
    public void testDatabaseConnection(DatabaseConnectionForm databaseConnectionForm) {
        chooseDatabaseService(databaseConnectionForm).getConnection(databaseConnectionForm);
    }

    @Override
    public List<String> getTables(Long databaseConnectionId) {
        DatabaseConnection databaseConnection = dataSourceConnectionRepository.findOne(databaseConnectionId);
        DatabaseService databaseService = chooseDatabaseService(databaseConnection);
        Connection connection = databaseService.getConnection(databaseConnection);
        return databaseService.getTables(connection);
    }

    @Override
    public List<String> getTableColumns(Long databaseConnectionId, String table) {
        DatabaseConnection databaseConnection = dataSourceConnectionRepository.findOne(databaseConnectionId);
        DatabaseService databaseService = chooseDatabaseService(databaseConnection);
        Connection connection = databaseService.getConnection(databaseConnection);
        return databaseService.getColumns(connection, table);
    }

    private DatabaseService chooseDatabaseService(DatabaseConnectionForm databaseConnectionForm) {
        return chooseDatabaseServiceByType(databaseConnectionForm.getDatabaseType());
    }

    private DatabaseService chooseDatabaseService(DatabaseConnection databaseConnection) {
        return chooseDatabaseServiceByType(databaseConnection.getDatabaseType());
    }

    private DatabaseService chooseDatabaseServiceByType(DatabaseType databaseType) {
        switch (databaseType) {
            case POSTGRES:
                return databasePostgresService;
            case H2:
                return databaseH2Service;
            default: return null;
        }
    }
}
