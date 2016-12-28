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

import com.abixen.platform.module.chart.model.enumtype.DataSourceType;
import com.abixen.platform.module.chart.model.enumtype.DatabaseType;
import com.abixen.platform.module.chart.model.impl.DataSource;
import com.abixen.platform.module.chart.model.impl.DataSourceColumn;
import com.abixen.platform.module.chart.model.impl.DatabaseConnection;
import com.abixen.platform.module.chart.model.impl.DatabaseDataSource;
import com.abixen.platform.module.chart.repository.DataSourceColumnRepository;
import com.abixen.platform.module.chart.repository.DatabaseDataSourceRepository;
import com.abixen.platform.module.chart.service.DatabaseConnectionService;
import com.abixen.platform.module.chart.service.DatabaseDataSourceService;
import com.abixen.platform.module.chart.service.DomainBuilderService;
import com.abixen.platform.module.configuration.PlatformModuleConfiguration;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;



       /*Sample Data:

        com.abixen.platform.module.chart.model.impl.DatabaseDataSource
        Object {
            databaseConnection: com.abixen.platform.module.chart.model.impl.DatabaseConnection
            Object {
                id: 1
                name:  Sample connection to internal h2 db
                description: Connection to the internal h2 db
                databaseType: H2
                databaseHost: file
                databasePort: null
                databaseName: abixen-platform-module-h2-db
                username: sa
                password:
                createdById: null
                createdDate: null
                lastModifiedById: null
                lastModifiedDate: null
            }
            filter: null
            table: SAMPLE_SELLING_DATA
            createdById: null
            createdDate: null
            lastModifiedById: null
            lastModifiedDate: null
        }

        com.abixen.platform.module.chart.model.impl.DatabaseConnection
Object {
    id: 1
    name:  Sample connection to internal h2 db
    description: Connection to the internal h2 db
    databaseType: H2
    databaseHost: file
    databasePort: null
    databaseName: abixen-platform-module-h2-db
    username: sa
    password:
    createdById: null
    createdDate: null
    lastModifiedById: null
    lastModifiedDate: null
}

        */



@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformModuleConfiguration.class)
public class DatabaseDataSourceServiceImplTest {

    static Logger log = Logger.getLogger(DatabaseDataSourceServiceImplTest.class.getName());
    @Resource
    private DatabaseDataSourceRepository databaseDataSourceRepository;

    @Resource
    private DataSourceColumnRepository dataSourceColumnRepository;



    @Autowired
    private DatabaseDataSourceService databaseDataSourceService;

    @Autowired
    private DatabaseConnectionService databaseConnectionService;


    private DomainBuilderService domainBuilderService=new DomainBuilderServiceImpl();


   private DataSourceColumn dataSourceColumn1;

    private DatabaseDataSource databaseDataSource1;


    @Before
    public void setUp() throws Exception {
//        databaseDataSourceRepository.deleteAllInBatch();

        MockitoAnnotations.initMocks(this);
     // databaseDataSourceService.getAllColumns((long) 1);
     /*   System.out.println("\t columns" +databaseDataSourceService.getAllColumns(new Long(1)));
        Page<DatabaseDataSource>  dabasedatasourceCurr =databaseDataSourceService.findAllDataSources(new PageRequest(0,10));

        //Page<DatabaseDataSource>  dabasedatasourceCurr = databaseDataSourceRepository.findAll(new PageRequest(0,10));
        //databaseDataSourceRepository.save(dabasedatasourceCurr);
        System.out.println(dabasedatasourceCurr);
        List<DatabaseDataSource> listval=dabasedatasourceCurr.getContent();
        listval.forEach(u -> System.out.println("\t ds" + u.toString()+"id"+u.getId()));
        listval.forEach(u -> System.out.println("\t db " + u.getDatabaseConnection().toString()));
        for (DatabaseDataSource player : listval) {
            //Hibernate.initialize(player.getColumns());
            System.out.println("\t columns" +player.getColumns());
            Set<DataSourceColumn> set = player.getColumns();
            set.forEach(m -> System.out.println("\t each column name:" + m.getName()+":ds"+m.getDataSource()+"id :" +m.getId()+"pos: "+m.getPosition()+m.getDataSource()));
        }*/



        DatabaseDataSource databaseDataSource = new DatabaseDataSource();
        databaseDataSource.setName("Oracle Datasource");
        databaseDataSource.setDescription("Oracle Datasource name");
        databaseDataSource.setDataSourceType(DataSourceType.DB);
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.setName("Oracle DB");
        databaseConnection.setDatabaseType(DatabaseType.ORACLE);
        databaseConnection.setDatabaseHost("abixenserver");
        databaseConnection.setDatabasePort(1122);


        databaseDataSource.setFilter("Tet1");
        databaseDataSource.setTable("EMPLOYEE");
        DataSourceColumn dataSourceColumn = new DataSourceColumn();
        dataSourceColumn.setDataSource(databaseDataSource);
        //dataSourceColumn.setId(new Long(112233));
        dataSourceColumn.setName("test connection to oracle");
        dataSourceColumn.setPosition(1);
        //databaseDataSource.getColumns().add(dataSourceColumn);
        databaseDataSource.setDescription("Oracle Server");
        DatabaseConnection DatabaseConnection1=databaseConnectionService.createDatabaseConnection(databaseConnection);
        databaseDataSource.setDatabaseConnection(DatabaseConnection1);
        // DataSourceColumn dataSourceColumn1 =dataSourceColumnRepository.save(dataSourceColumn);


        databaseDataSource1 =databaseDataSourceRepository.save(databaseDataSource);
        //databaseDataSourceRepository.flush();
        dataSourceColumn.setDataSource(databaseDataSource1);
        dataSourceColumn1 =dataSourceColumnRepository.save(dataSourceColumn);

        databaseDataSource1.getColumns().add(dataSourceColumn);
        databaseDataSource1 =databaseDataSourceRepository.save(databaseDataSource);

    }

    @After

    public void tearDown() throws Exception {
        dataSourceColumnRepository.delete(dataSourceColumn1);
        dataSourceColumnRepository.flush();
        databaseDataSourceRepository.delete(databaseDataSource1);
        dataSourceColumnRepository.flush();
        //databaseDataSourceRepository.delete(databaseDataSource1);
        // DataSourceColumn dataSourceColumn2 = dataSourceColumnRepository.findOne(dataSourceColumn1.getId());
        // DatabaseDataSource databaseDataSource2 = databaseDataSourceRepository.findOne(databaseDataSource1.getId());


    }

    @Test
    public void getgetAllColumns() throws Exception {
        List<Map<String, Integer>>  columns  = databaseDataSourceService.getAllColumns(dataSourceColumn1.getDataSource().getId());
      //Add products Maps here
        assertNotNull(columns);

        Map<String, Integer> cumulativeMap = new HashMap<String, Integer>();
// Use enhaced for loop for efficiency.
        for(Map<String, Integer> columnMap: columns){
            for(Map.Entry<String, Integer> p: columnMap.entrySet()){
                assertEquals(p.getKey(),dataSourceColumn1.getName());
                assertEquals(p.getValue(),dataSourceColumn1.getPosition());


            }
        }


    }


}