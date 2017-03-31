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

import com.abixen.platform.service.businessintelligence.multivisualisation.form.DatabaseDataSourceForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DataSourceType;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DataValueType;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DatabaseType;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.database.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataSourceColumnWeb;
import com.abixen.platform.service.businessintelligence.multivisualisation.repository.DataSourceColumnRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.repository.DatabaseConnectionRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.repository.DatabaseDataSourceRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DatabaseConnectionService;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DatabaseDataSourceService;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DomainBuilderService;
import com.abixen.platform.service.businessintelligence.configuration.PlatformModuleConfiguration;
import com.abixen.platform.service.businessintelligence.multivisualisation.util.DatabaseConnectionPasswordEncryption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

 @Slf4j
@Service
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformModuleConfiguration.class)

public class DatabaseDataSourceServiceImplTest {

    @Resource
    private DatabaseDataSourceRepository databaseDataSourceRepository;

    @Resource
    private DataSourceColumnRepository dataSourceColumnRepository;

    @Autowired
    private DatabaseDataSourceService databaseDataSourceService;

    @Autowired
    private DatabaseConnectionService databaseConnectionService;

    @Autowired
    private DatabaseConnectionPasswordEncryption databaseConnectionPasswordEncryption;

    @Resource
    private DatabaseConnectionRepository dataSourceConnectionRepository;

    private DomainBuilderService domainBuilderService = new DomainBuilderServiceImpl();

    private DataSourceColumn dataSourceColumnAfterSave;

    private DatabaseDataSource databaseDataSourceAfterSave;

    private DatabaseConnection DatabaseConnectionAfterSave;

    private DatabaseDataSourceForm databaseDataSourceFormSave;

    private DatabaseDataSourceForm databaseDataSourceFormSaveNew;

    /**
     * Set up  method for creating  new data for unit test
     */
    @Before
    public void setUp()  {
        MockitoAnnotations.initMocks(this);
        DatabaseDataSource databaseDataSource = new DatabaseDataSource();
        databaseDataSource.setName("Oracle Datasource");
        databaseDataSource.setDescription("Oracle Datasource name");
        databaseDataSource.setDataSourceType(DataSourceType.DB);
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.setName("Oracle DB");
        databaseConnection.setDatabaseType(DatabaseType.ORACLE);
        databaseConnection.setDatabaseHost("abixenserver");
        databaseConnection.setDatabasePort(1122);
        databaseConnection.setDatabaseName("abixen_bi");
        databaseConnection.setUsername("system");
        databaseConnection.setPassword(databaseConnectionPasswordEncryption.encryptPassword("passowrd"));
        databaseDataSource.setFilter("Tet1");
        databaseDataSource.setTable("EMPLOYEE");
        DataSourceColumn dataSourceColumn = new DataSourceColumn();
        dataSourceColumn.setDataSource(databaseDataSource);
        dataSourceColumn.setName("test connection to oracle");
        dataSourceColumn.setPosition(1);
        dataSourceColumn.setDataValueType(DataValueType.STRING);
        databaseDataSource.setDescription("Oracle Server");
        DatabaseConnectionAfterSave = databaseConnectionService.createDatabaseConnection(databaseConnection);
        databaseDataSource.setDatabaseConnection(DatabaseConnectionAfterSave);
        databaseDataSourceAfterSave = databaseDataSourceRepository.save(databaseDataSource);
        dataSourceColumn.setDataSource(databaseDataSourceAfterSave);
        dataSourceColumnAfterSave = dataSourceColumnRepository.save(dataSourceColumn);
        databaseDataSourceAfterSave.getColumns().add(dataSourceColumn);
        databaseDataSourceAfterSave = databaseDataSourceRepository.save(databaseDataSource);
        DatabaseDataSourceForm databaseDataSourceForm = new DatabaseDataSourceForm();
        databaseDataSourceForm.setName("New Form");
        databaseDataSourceForm.setDescription("New Form Desc");
        databaseDataSourceForm.setDatabaseConnection(DatabaseConnectionAfterSave);
        databaseDataSourceForm.setTable("NEW_TABLE");
        Set<DataSourceColumnWeb> columns = new HashSet<DataSourceColumnWeb>();
        columns.add(dataSourceColumnAfterSave);
        databaseDataSourceForm.setColumns(columns);
        databaseDataSourceFormSave = databaseDataSourceService.createDataSource(databaseDataSourceForm);
    }
    /**
     * Tear down  method for clearing records created for unit test
     */
    @After
    public void tearDown()  {
        dataSourceColumnRepository.delete(dataSourceColumnAfterSave);
        dataSourceColumnRepository.flush();
        databaseDataSourceRepository.delete(databaseDataSourceAfterSave);
        dataSourceColumnRepository.flush();
        databaseDataSourceRepository.delete(databaseDataSourceFormSave.getId());
        if (databaseDataSourceFormSaveNew != null) {
            databaseDataSourceRepository.delete(databaseDataSourceFormSaveNew.getId());
        }
        dataSourceConnectionRepository.delete(DatabaseConnectionAfterSave);
        dataSourceColumnRepository.flush();
    }

    /**
     * Test method for getAllColumns in DatabaseDataSourceServiceImpl
     */
    @Test
    public void getAllColumns()  {
        List<Map<String, Integer>> columns = databaseDataSourceService.getAllColumns(dataSourceColumnAfterSave.getDataSource().getId());
        assertNotNull(columns);

        Map<String, Integer> cumulativeMap = new HashMap<String, Integer>();
        for (Map<String, Integer> columnMap : columns) {
            for (Map.Entry<String, Integer> p : columnMap.entrySet()) {
                assertEquals(p.getKey(), dataSourceColumnAfterSave.getName());
                assertEquals(p.getValue(), dataSourceColumnAfterSave.getPosition());
            }
        }
    }

    /**
     * Test method for getDataSourceColumns in DatabaseDataSourceServiceImpl
     */
    @Test
    public void getDataSourceColumns()  {
        Set<DataSourceColumn> columns = databaseDataSourceService.getDataSourceColumns(dataSourceColumnAfterSave.getDataSource().getId());
        assertNotNull(columns);
        for (DataSourceColumn column : columns) {
                assertEquals(column.getName(), dataSourceColumnAfterSave.getName());
                assertEquals(column.getPosition(), dataSourceColumnAfterSave.getPosition());
        }
    }

    @Test
    /**
     * Test method for getDatabaseDataSources in DatabaseDataSourceServiceImpl
     */
    public void getDataSources() {
        Page<DatabaseDataSource> page = databaseDataSourceService.getDatabaseDataSources(null, new PageRequest(0, 10));
        assertTrue(page.getTotalElements() > 0);
        assertEquals(page.getTotalElements(),5);
    }

    /**
     * Test method for findAllDataSources in DatabaseDataSourceServiceImpl
     */
    @Test
    public void findAllDataSources()  {
        Page<DatabaseDataSource> page = databaseDataSourceService.findAllDataSources(new PageRequest(0, 10));
        assertTrue(page.getTotalElements() > 0);
        assertEquals(page.getTotalElements(),5);
    }

    /**
     * Test method for buildDataSource in DatabaseDataSourceServiceImpl
     */
    @Test
    public void buildDataSource() {
        DatabaseDataSourceForm databaseDataSourceForm = new DatabaseDataSourceForm();
        databaseDataSourceForm.setName("New Form");
        databaseDataSourceForm.setDescription("New Form Desc");
        databaseDataSourceForm.setDatabaseConnection(DatabaseConnectionAfterSave);
        databaseDataSourceForm.setTable("NEW_TABLE");
        Set<DataSourceColumnWeb> columns = new HashSet<DataSourceColumnWeb>();
        columns.add(dataSourceColumnAfterSave);
        databaseDataSourceForm.setColumns(columns);
        DatabaseDataSource databaseDataSource = databaseDataSourceService.buildDataSource(databaseDataSourceForm);
        assertNotNull(databaseDataSource.getDescription());
        assertEquals(databaseDataSource.getDescription(),"New Form Desc");
    }

    /**
     * Test method for createDataSource in DatabaseDataSourceServiceImpl
     */
    @Test
    public void createDataSource()  {
        DatabaseDataSourceForm databaseDataSourceForm = new DatabaseDataSourceForm();
        databaseDataSourceForm.setName("New Form");
        databaseDataSourceForm.setDescription("New Form Desc");
        databaseDataSourceForm.setDatabaseConnection(DatabaseConnectionAfterSave);
        databaseDataSourceForm.setTable("NEW_TABLE");
        Set<DataSourceColumnWeb> columns = new HashSet<DataSourceColumnWeb>();
        columns.add(dataSourceColumnAfterSave);
        databaseDataSourceForm.setColumns(columns);
        databaseDataSourceFormSaveNew = databaseDataSourceService.createDataSource(databaseDataSourceForm);
        assertNotNull(databaseDataSourceFormSaveNew.getId());
    }

    /**
     * Test method for updateDataSourceWithDataSourceForm in DatabaseDataSourceServiceImpl
     */
    @Test
    public void updateDataSourceWithDataSourceForm()  {
        databaseDataSourceFormSave.setName("New Form1");
        databaseDataSourceFormSave.setDescription("New Form Desc1");
        DatabaseDataSourceForm databaseDataSourceFormupdate = databaseDataSourceService.updateDataSource(databaseDataSourceFormSave);
        assertTrue(databaseDataSourceFormupdate.getName().equals("New Form1"));
    }

    /**
     * Test method for updateDataSourceWithDataSource in DatabaseDataSourceServiceImpl
     */
    @Test
    public void updateDataSourceWithDataSource() {
        databaseDataSourceAfterSave.setName("New Form1");
        databaseDataSourceAfterSave.setDescription("New Form Desc1");
        DatabaseDataSource databaseDataSourceUpdate = databaseDataSourceService.updateDataSource(databaseDataSourceAfterSave);
        assertTrue(databaseDataSourceUpdate.getName().equals("New Form1"));
    }

    /**
     * Test method for findDatabaseDataSource in DatabaseDataSourceServiceImpl
     */
    @Test
    public void findDataSource()  {
        DatabaseDataSource databaseDataSourceUpdate = databaseDataSourceService.findDatabaseDataSource(databaseDataSourceAfterSave.getId());
        assertTrue(databaseDataSourceUpdate.getId().equals(databaseDataSourceAfterSave.getId()));
    }

}