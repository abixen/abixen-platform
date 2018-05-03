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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.infrastructure.exception.DatabaseConnectionException;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DatabaseConnectionForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;


@Slf4j
@Service("databasePostgresService")
class DatabasePostgresService extends AbstractDatabaseService implements DatabaseService {


    @Override
    public Connection getConnection(DatabaseConnectionDto databaseConnection) {
        return getConnection(new DatabaseConnectionForm(databaseConnection));
    }

    @Override
    public Connection getConnection(DatabaseConnectionForm databaseConnectionForm) {
        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException exception) {

            log.error("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            throw new DatabaseConnectionException(exception.getMessage());

        }

        log.info("PostgreSQL JDBC Driver Registered!");

        Connection connection;

        try {

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + databaseConnectionForm.getDatabaseHost() +
                            ":" + databaseConnectionForm.getDatabasePort() + "/" +
                            databaseConnectionForm.getDatabaseName(), databaseConnectionForm.getUsername(),
                    databaseConnectionForm.getPassword());

        } catch (SQLException exception) {
            log.error("Connection Failed! Check output console");
            throw new DatabaseConnectionException(exception.getMessage());
        }

        if (connection != null) {
            log.info("You made it, take control your database now!");
        } else {
            log.error("Failed to make connection!");
        }

        return connection;
    }

    @Override
    protected Map<String, String> getNativeTypeMapping(Map<String, String> databaseTypeOnApplicationType) {
        databaseTypeOnApplicationType.put("INT8", "INTEGER");
        return databaseTypeOnApplicationType;
    }

}