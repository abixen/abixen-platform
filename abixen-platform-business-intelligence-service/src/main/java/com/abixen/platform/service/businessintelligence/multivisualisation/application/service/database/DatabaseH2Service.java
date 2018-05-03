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
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DatabaseConnectionForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.infrastructure.exception.DatabaseConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("databaseH2Service")
public class DatabaseH2Service extends AbstractDatabaseService implements DatabaseService {

    private static final String LOCALHOST_FOR_INTERNAL_DB = "file";

    private static final List<String> SYSTEM_TABLE_LIST = new ArrayList<>(Arrays.asList(
            "databasechangelog",
            "databasechangeloglock"));

    @Autowired
    public DatabaseH2Service(EntityManagerFactory factory) {
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
    public Connection getConnection(DatabaseConnectionDto databaseConnection) {
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
                        "jdbc:h2:file:" +
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

}