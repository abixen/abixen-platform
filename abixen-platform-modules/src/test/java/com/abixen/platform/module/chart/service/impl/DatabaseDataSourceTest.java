/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * <p>
 * This file contains proprietary information of Abixen Systems.
 * Copying or reproduction without prior written approval is prohibited.
 */

package com.abixen.platform.module.chart.service.impl;

import com.abixen.platform.core.util.PlatformProfiles;
import com.abixen.platform.module.chart.form.DatabaseConnectionForm;
import com.abixen.platform.module.chart.model.enumtype.DatabaseType;
import com.abixen.platform.module.chart.model.impl.DatabaseConnection;
import com.abixen.platform.module.chart.model.impl.FileDataSource;
import com.abixen.platform.module.chart.repository.DatabaseConnectionRepository;
import com.abixen.platform.module.chart.repository.DatabaseDataSourceRepository;
import com.abixen.platform.module.chart.service.DatabaseConnectionService;
import com.abixen.platform.module.chart.service.DatabaseService;
import com.abixen.platform.module.configuration.PlatformModuleDataSourceConfiguration;
import com.abixen.platform.module.configuration.PlatformModuleJpaConfiguration;
import com.abixen.platform.module.configuration.PlatformModuleSecurityConfiguration;
import com.abixen.platform.module.configuration.PlatformModuleServiceConfiguration;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.assertNotNull;


@ActiveProfiles(PlatformProfiles.TEST)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {PlatformModuleJpaConfiguration.class, PlatformModuleDataSourceConfiguration.class,
        PlatformModuleServiceConfiguration.class, PlatformModuleSecurityConfiguration.class, FileDataSource.class,
        DatabaseDataSourceRepository.class
})
public class DatabaseDataSourceTest {

    static Logger log = Logger.getLogger(DatabaseDataSourceTest.class.getName());

    @Autowired
    private DatabaseConnectionService databaseConnectionService;

    @Autowired
    private DatabaseService databaseService;

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

        DatabaseConnectionForm createdDatabaseConnectionForm = databaseConnectionService.createDatabaseConnection(databaseConnectionForm);

        assertNotNull(createdDatabaseConnectionForm);
    }

    @Test
    public void getConnection() {
        log.debug("createDataSourceConnection()");

        DatabaseConnection databaseConnection = databaseConnectionRepository.findOne(1L);

        Connection connection = databaseService.getConnection(databaseConnection);

        List<String> tables = databaseService.getTables(connection);

        tables.forEach(log::debug);

        List<String> columns = databaseService.getColumns(connection, "products_sale");

        columns.forEach(log::debug);

        assertNotNull(connection);
    }

}
