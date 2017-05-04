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

import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.form.DatabaseDataSourceForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.database.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataSourceColumnWeb;
import com.abixen.platform.service.businessintelligence.multivisualisation.repository.DatabaseDataSourceRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class DatabaseDataSourceServiceImpl extends DataSourceServiceImpl implements DatabaseDataSourceService {

    @Resource
    private DatabaseDataSourceRepository databaseDataSourceRepository;

    @Autowired
    private DatabaseConnectionService databaseConnectionService;

    @Autowired
    private DomainBuilderService domainBuilderService;

    @Autowired
    private DatabaseFactory databaseFactory;

    @Override
    public Set<DataSourceColumn> getDataSourceColumns(Long dataSourceId) {
        Set<DataSourceColumn> result;
        DatabaseDataSource databaseDataSource = databaseDataSourceRepository.getOne(dataSourceId);
        result = databaseDataSource.getColumns();
        return result;
    }

    @Deprecated
    public Page<DatabaseDataSource> getDatabaseDataSources(String jsonCriteria, Pageable pageable) {
        Page<DatabaseDataSource> result;
        //TODO - needs with criteria?
        //result = databaseDataSourceRepository.findAllByJsonCriteria(jsonCriteria, pageable);
        result = databaseDataSourceRepository.findAll(pageable);
        return result;
    }

    @Override
    public Page<DatabaseDataSource> findAllDataSources(Pageable pageable) {
        return databaseDataSourceRepository.findAll(pageable);
    }

    @Override
    public List<Map<String, Integer>> getAllColumns(Long dataSourceId) {

        List<Map<String, Integer>> result = new ArrayList<>();
        DatabaseDataSource databaseDataSource = databaseDataSourceRepository.getOne(dataSourceId);

        for (DataSourceColumn dataSourceColumn : databaseDataSource.getColumns()) {
            String name = dataSourceColumn.getName();
            Integer position = dataSourceColumn.getPosition();
            Map<String, Integer> column = new HashMap<>(1);
            column.put(name, position);
            result.add(column);
        }
        return result;
    }

    @Override
    public DatabaseDataSource buildDataSource(DatabaseDataSourceForm databaseDataSourceForm) {
        log.debug("buildDataSource() - databaseDataSourceForm: " + databaseDataSourceForm);
        DatabaseConnection databaseConnection = databaseConnectionService.findDatabaseConnection(databaseDataSourceForm.getDatabaseConnection().getId());
        return domainBuilderService.newDatabaseDataSourceBuilderInstance()
                .base(databaseDataSourceForm.getName(), databaseDataSourceForm.getDescription())
                .connection(databaseConnection)
                .data(databaseDataSourceForm.getTable(), databaseDataSourceForm.getFilter())
                .columns(databaseDataSourceForm.getColumns())
                .build();
    }

    @Override
    public DatabaseDataSourceForm createDataSource(DatabaseDataSourceForm databaseDataSourceForm) {
        DatabaseDataSource databaseDataSource = buildDataSource(databaseDataSourceForm);
        return new DatabaseDataSourceForm(updateDataSource(createDataSource(databaseDataSource)));
    }

    @Override
    public DatabaseDataSource createDataSource(DatabaseDataSource databaseDataSource) {
        log.debug("createDataSource() - databaseDataSource: " + databaseDataSource);
        DatabaseDataSource createdDatabaseDataSource = databaseDataSourceRepository.save(databaseDataSource);
        /*aclService.insertDefaultAcl(createdPage, new ArrayList<PermissionName>() {{
            add(PermissionName.PAGE_VIEW);
            add(PermissionName.PAGE_EDIT);
            add(PermissionName.PAGE_DELETE);
            add(PermissionName.PAGE_CONFIGURATION);
            add(PermissionName.PAGE_PERMISSION);
        }});*/
        return createdDatabaseDataSource;
    }

    @Override
    public DatabaseDataSourceForm updateDataSource(DatabaseDataSourceForm databaseDataSourceForm) {
        log.debug("updateDataSource() - databaseDataSourceForm: " + databaseDataSourceForm);

        DatabaseDataSource databaseDataSource = findDatabaseDataSource(databaseDataSourceForm.getId());
        databaseDataSource.setName(databaseDataSourceForm.getName());
        databaseDataSource.setDatabaseConnection(databaseConnectionService.findDatabaseConnection(databaseDataSourceForm.getDatabaseConnection().getId()));
        databaseDataSource.setFilter(databaseDataSourceForm.getFilter());
        databaseDataSource.setTable(databaseDataSourceForm.getTable());
        databaseDataSource.setDescription(databaseDataSourceForm.getDescription());
        Set<DataSourceColumn> dataSourceColumns = new HashSet<>();
        List<String> oldColumnNames = databaseDataSource.getColumns().stream()
                        .map(DataSourceColumn::getName)
                        .peek(s -> s = s.toUpperCase())
                        .collect(Collectors.toList());
        List<String> newColumnNames = databaseDataSourceForm.getColumns().stream()
                        .map(DataSourceColumnWeb::getName)
                        .peek(s -> s = s.toUpperCase())
                        .collect(Collectors.toList());
        newColumnNames.replaceAll(String::toUpperCase);
        oldColumnNames.replaceAll(String::toUpperCase);
        List<DataSourceColumn> toRemove = databaseDataSource.getColumns().stream().filter(dataSourceColumn -> !newColumnNames.contains(dataSourceColumn.getName().toUpperCase())).collect(Collectors.toList());
        List<DataSourceColumnWeb> toAdd = databaseDataSourceForm.getColumns().stream().filter(dataSourceColumn -> !oldColumnNames.contains(dataSourceColumn.getName().toUpperCase())).collect(Collectors.toList());
        if (!toRemove.isEmpty()) {
            databaseDataSource.removeColumns(new HashSet<>(toRemove));
        }
        if (!toAdd.isEmpty()) {
            convertDataSourceColumnWebToDataSourceColumn(databaseDataSource, dataSourceColumns, toAdd);
            databaseDataSource.addColumns(dataSourceColumns);
        }
        return new DatabaseDataSourceForm(updateDataSource(databaseDataSource));
    }

    private void convertDataSourceColumnWebToDataSourceColumn(DatabaseDataSource databaseDataSource, Set<DataSourceColumn> dataSourceColumns, List<DataSourceColumnWeb> toAdd) {
        for (DataSourceColumnWeb dataSourceColumnWeb : toAdd) {
            DataSourceColumn dataSourceColumn = new DataSourceColumn();
            dataSourceColumn.setName(dataSourceColumnWeb.getName());
            dataSourceColumn.setPosition(dataSourceColumnWeb.getPosition());
            dataSourceColumn.setDataSource(databaseDataSource);
            dataSourceColumn.setDataValueType(dataSourceColumnWeb.getDataValueType());
            dataSourceColumns.add(dataSourceColumn);
        }
    }

    @Override
    public DatabaseDataSource updateDataSource(DatabaseDataSource databaseDataSource) {
        log.debug("updateDataSource() - databaseDataSource: " + databaseDataSource);
        return databaseDataSourceRepository.save(databaseDataSource);
    }

    @Override
    public DatabaseDataSource findDatabaseDataSource(Long id) {
        log.debug("findPage() - id: " + id);
        return databaseDataSourceRepository.findOne(id);
    }

    @Override
    public void delateDataBaseDataSource(Long id) {
        databaseDataSourceRepository.delete(id);
    }

    @Override
    public List<Map<String, DataValueDto>> getPreviewData(DatabaseDataSourceForm databaseDataSourceForm) {
        DatabaseConnection databaseConnection = databaseConnectionService.findDatabaseConnection(databaseDataSourceForm.getDatabaseConnection().getId());
        DatabaseService databaseService = databaseFactory.getDatabaseService(databaseDataSourceForm.getDatabaseConnection().getDatabaseType());
        Connection connection = databaseService.getConnection(databaseConnection);
        List<Map<String, DataValueDto>> dataSourcePreviewData = databaseService.getDataSourcePreview(connection, buildDataSource(databaseDataSourceForm));
        return dataSourcePreviewData;
    }
}
