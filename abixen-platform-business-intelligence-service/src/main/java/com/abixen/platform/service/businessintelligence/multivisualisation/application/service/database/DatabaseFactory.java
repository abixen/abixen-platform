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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service.database;

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.connection.DatabaseType;
import com.abixen.platform.service.businessintelligence.multivisualisation.infrastructure.exception.DatabaseConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DatabaseFactory {

    private final DatabaseService databasePostgresService;
    private final DatabaseService databaseH2Service;
    private final DatabaseService databaseMySQLService;
    private final DatabaseService databaseOracleService;
    private final DatabaseService databaseMSSQLService;

    @Autowired
    public DatabaseFactory(@Qualifier("databasePostgresService") DatabaseService databasePostgresService,
                           @Qualifier("databaseH2Service") DatabaseService databaseH2Service,
                           @Qualifier("databaseMySQLService") DatabaseService databaseMySQLService,
                           @Qualifier("databaseOracleService") DatabaseService databaseOracleService,
                           @Qualifier("databaseMSSQLService") DatabaseService databaseMSSQLService) {
        this.databasePostgresService = databasePostgresService;
        this.databaseH2Service = databaseH2Service;
        this.databaseMySQLService = databaseMySQLService;
        this.databaseOracleService = databaseOracleService;
        this.databaseMSSQLService = databaseMSSQLService;
    }

    public DatabaseService getDatabaseService(DatabaseType databaseType) {
        switch (databaseType) {
            case POSTGRES:
                return databasePostgresService;
            case H2:
                return databaseH2Service;
            case MYSQL:
                return databaseMySQLService;
            case ORACLE:
                return databaseOracleService;
            case MSSQL:
                return databaseMSSQLService;
            default:
                throw new DatabaseConnectionException("Invalid database type");
        }
    }

}