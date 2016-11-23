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

package com.abixen.platform.module.chart.service.impl;

import com.abixen.platform.module.chart.exception.DatabaseConnectionException;
import com.abixen.platform.module.chart.form.DatabaseConnectionForm;
import com.abixen.platform.module.chart.model.impl.DatabaseConnection;
import com.abixen.platform.module.chart.service.DatabaseService;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service("databaseH2Service")
public class DatabaseH2ServiceImpl extends AbstractDatabaseService implements DatabaseService {

    private final Logger log = LoggerFactory.getLogger(DatabasePostgresServiceImpl.class);

    private static final String LOCALHOST_FOR_INTERNAL_DB = "file";

    private static final List<String> SYSTEM_TABLE_LIST = new ArrayList<>(Arrays.asList(
            "databasechangelog",
            "databasechangeloglock"));

    @Autowired
    public DatabaseH2ServiceImpl(EntityManagerFactory factory) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        loadSystemTableList(factory.unwrap(SessionFactory.class));
    }

    private void loadSystemTableList(SessionFactory sessionFactory) {
        Map<String, ClassMetadata> allClassMetadata = sessionFactory.getAllClassMetadata();
        allClassMetadata.forEach((key, value) -> {
            AbstractEntityPersister abstractEntityPersister = (AbstractEntityPersister) value;
            SYSTEM_TABLE_LIST.add(abstractEntityPersister.getTableName());
        });
    }

    @Override
    public Connection getConnection(DatabaseConnection databaseConnection) {
        return getConnection(new DatabaseConnectionForm(databaseConnection));
    }

    @Override
    public Connection getConnection(DatabaseConnectionForm databaseConnectionForm) {
        try {

            Class.forName("org.h2.Driver");

        } catch (ClassNotFoundException exception) {

            log.error("Where is your H2 JDBC Driver? "
                    + "Include in your library path!");
            throw new DatabaseConnectionException(exception.getMessage());

        }

        log.info("H2 JDBC Driver Registered!");

        Connection connection;

        try {
            Boolean isInternalDatabase = databaseConnectionForm.getDatabaseHost().equalsIgnoreCase(LOCALHOST_FOR_INTERNAL_DB);
            if (isInternalDatabase) {
                connection = DriverManager.getConnection(
                        "jdbc:h2:file:~/" +
                                databaseConnectionForm.getDatabaseName(),
                        databaseConnectionForm.getUsername(),
                        databaseConnectionForm.getPassword());
            } else {
                connection = DriverManager.getConnection(
                        "jdbc:h2:tcp:" +
                                databaseConnectionForm.getDatabaseHost() + ":" +
                                databaseConnectionForm.getDatabasePort() + "/" +
                                databaseConnectionForm.getDatabaseName(),
                        databaseConnectionForm.getUsername(),
                        databaseConnectionForm.getPassword());
            }

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
    protected ResultSet getTablesAsResultSet(Connection connection) throws SQLException {
        PreparedStatement st = connection.prepareStatement("SELECT * FROM INFORMATION_SCHEMA.TABLES");
        st.execute();
        return st.getResultSet();
    }

    @Override
    protected boolean isAllowedTable(String tableName) {
        return !SYSTEM_TABLE_LIST.contains(tableName.toLowerCase());
    }
}
