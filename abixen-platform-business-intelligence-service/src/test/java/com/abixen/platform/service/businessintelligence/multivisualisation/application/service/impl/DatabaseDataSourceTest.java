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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DatabaseConnectionForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DatabaseType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.database.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DatabaseConnectionRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DatabaseConnectionService;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DatabaseService;
import com.abixen.platform.service.businessintelligence.infrastructure.configuration.PlatformModuleConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformModuleConfiguration.class)
public class DatabaseDataSourceTest {

    @Autowired
    private DatabaseConnectionService databaseConnectionService;

    @Autowired
    @Qualifier("databasePostgresService")
    private DatabaseService databasePostgresService;

    @Resource
    private DatabaseConnectionRepository databaseConnectionRepository;


    @Test
    public void createDataSourceConnection() {
        log.debug("createDataSourceConnection()");

        DatabaseConnectionForm databaseConnectionForm = new DatabaseConnectionForm();

        databaseConnectionForm.setName("Test connection");
        databaseConnectionForm.setDescription("Test connection description");
        databaseConnectionForm.setDatabaseType(DatabaseType.POSTGRES);
        databaseConnectionForm.setDatabaseHost("localhost");
        databaseConnectionForm.setDatabasePort(5432);
        databaseConnectionForm.setDatabaseName("abixen_platform_modules_data");
        databaseConnectionForm.setUsername("postgres");
        databaseConnectionForm.setPassword("postgres");

        DatabaseConnection createdDatabaseConnection = databaseConnectionService.createDatabaseConnection(databaseConnectionForm);

        assertNotNull(createdDatabaseConnection);
    }

    //FIXME
    /*@Test
    public void getConnection() {
        log.debug("createDataSourceConnection()");

        DatabaseConnection databaseConnection = databaseConnectionRepository.findOne(1L);

        Connection connection = databaseService.getConnection(databaseConnection);

        List<String> tables = databaseService.getTables(connection);

        tables.forEach(log::debug);

        List<String> columns = databaseService.getColumns(connection, "products_sale");

        columns.forEach(log::debug);

        assertNotNull(connection);
    }*/

}
