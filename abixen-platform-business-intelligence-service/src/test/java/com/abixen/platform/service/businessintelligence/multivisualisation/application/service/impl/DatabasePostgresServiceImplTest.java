/*
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 *
 *  This library is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU Lesser General Public License as published by the Free
 *  Software Foundation; either version 2.1 of the License, or (at your option)
 *  any later version.
 *
 *  This library is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 *  details.
 *
 */

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service.impl;

import com.abixen.platform.service.businessintelligence.multivisualisation.infrastructure.exception.DatabaseConnectionException;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DatabaseConnectionForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DatabaseType;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.database.DatabaseService;
import com.abixen.platform.service.businessintelligence.infrastructure.configuration.PlatformModuleConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.sql.Connection;
import static org.junit.Assert.assertTrue;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformModuleConfiguration.class)
public class DatabasePostgresServiceImplTest {

    @Autowired
    @Qualifier("databasePostgresService")
    private DatabaseService databaseService;
    private DatabaseConnectionForm databaseConnectionForm;

    @Before
    public void createDatabaseConnectionForm(){
        log.debug("createDummyDatabaseConnectionForm()");
        databaseConnectionForm = new DatabaseConnectionForm();
        databaseConnectionForm.setName("Test connection");
        databaseConnectionForm.setDescription("Test connection description");
        databaseConnectionForm.setDatabaseType(DatabaseType.POSTGRES);
        databaseConnectionForm.setDatabaseHost("localhost");
        databaseConnectionForm.setDatabasePort(5432);
        databaseConnectionForm.setDatabaseName("abixen_platform_modules_data");
        databaseConnectionForm.setUsername("postgres");
        databaseConnectionForm.setPassword("postgres");
    }

    @Test
    public void testGetConnectionFail(){
        Connection connection = null;
        Throwable thrownExceptionInGetConnection = null;
        try {
            connection = databaseService.getConnection(databaseConnectionForm);
        } catch (Exception ex) {
            thrownExceptionInGetConnection = ex;
            log.debug(ex.getMessage());
        }

        assertTrue(thrownExceptionInGetConnection instanceof DatabaseConnectionException);
    }
}