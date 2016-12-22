package com.abixen.platform.module.chart.service.impl;

import com.abixen.platform.module.chart.exception.DatabaseConnectionException;
import com.abixen.platform.module.chart.form.DatabaseConnectionForm;
import com.abixen.platform.module.chart.model.enumtype.DatabaseType;
import com.abixen.platform.module.chart.service.DatabaseService;
import com.abixen.platform.module.configuration.PlatformModuleConfiguration;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformModuleConfiguration.class)
public class DatabasePostgresServiceImplTest {
    private static Logger log = Logger.getLogger(DatabasePostgresServiceImplTest.class.getName());

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
    public void testGetConnection() {
        Connection connection = null;
        Throwable thrownExceptionInGetConnection = null;
        try {
            connection = databaseService.getConnection(databaseConnectionForm);
        } catch (Exception ex) {
            thrownExceptionInGetConnection = ex;
            log.debug(ex.getMessage());
        }

        if (connection == null)
            assertTrue(thrownExceptionInGetConnection instanceof DatabaseConnectionException);
        else
            assertNotNull(connection);
    }
}