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

import com.abixen.platform.service.businessintelligence.multivisualisation.exception.DatabaseConnectionException;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DatabaseType;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DatabaseFactory;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DatabaseFactoryImpl implements DatabaseFactory {

    @Autowired
    @Qualifier("databasePostgresService")
    private DatabaseService databasePostgresService;

    @Autowired
    @Qualifier("databaseH2Service")
    private DatabaseService databaseH2Service;

    @Autowired
    @Qualifier("databaseMySQLService")
    private DatabaseService databaseMySQLService;

    @Autowired
    @Qualifier("databaseOracleService")
    private DatabaseService databaseOracleService;

    @Autowired
    @Qualifier("databaseMSSQLService")
    private DatabaseService databaseMSSQLService;

    @Override
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
            default: throw new DatabaseConnectionException("Invalid database type");
        }
    }
}
