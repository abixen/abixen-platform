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

package com.abixen.platform.service.businessintelligence.multivisualisation.facade.impl;

import com.abixen.platform.service.businessintelligence.multivisualisation.converter.DataSourceColumnToDataSourceColumnDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.converter.DatabaseConnectionToDatabaseConnectionDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataSourceColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.facade.DatabaseConnectionFacade;
import com.abixen.platform.service.businessintelligence.multivisualisation.form.DatabaseConnectionForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.database.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DatabaseConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class DatabaseConnectionFacadeImpl implements DatabaseConnectionFacade {

    private final DatabaseConnectionService databaseConnectionService;
    private final DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter;
    private final DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter;

    @Autowired
    public DatabaseConnectionFacadeImpl(DatabaseConnectionService databaseConnectionService,
                                        DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter,
                                        DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter) {
        this.databaseConnectionService = databaseConnectionService;
        this.databaseConnectionToDatabaseConnectionDtoConverter = databaseConnectionToDatabaseConnectionDtoConverter;
        this.dataSourceColumnToDataSourceColumnDtoConverter = dataSourceColumnToDataSourceColumnDtoConverter;
    }

    @Override
    public Page<DatabaseConnectionDto> findAllDatabaseConnections(Pageable pageable) {
        Page<DatabaseConnection> databaseConnections = databaseConnectionService.findAllDatabaseConnections(pageable);
        Page<DatabaseConnectionDto> databaseConnectionDtos = databaseConnectionToDatabaseConnectionDtoConverter.convertToPage(databaseConnections);
        return databaseConnectionDtos;
    }

    @Override
    public DatabaseConnectionDto findDatabaseConnection(Long id) {
        DatabaseConnection databaseConnection = databaseConnectionService.findDatabaseConnection(id);
        DatabaseConnectionDto databaseConnectionDto = databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection);
        return databaseConnectionDto;
    }

    @Override
    public void deleteDatabaseConnection(Long id) {
        databaseConnectionService.deleteDatabaseConnection(id);
    }

    @Override
    public DatabaseConnectionDto createDatabaseConnection(DatabaseConnectionForm databaseConnectionForm) {
        DatabaseConnection databaseConnection = databaseConnectionService.createDatabaseConnection(databaseConnectionForm);
        DatabaseConnectionDto databaseConnectionDto = databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection);
        return databaseConnectionDto;
    }

    @Override
    public DatabaseConnectionDto updateDatabaseConnection(DatabaseConnectionForm databaseConnectionForm) {
        DatabaseConnection databaseConnection = databaseConnectionService.updateDatabaseConnection(databaseConnectionForm);
        DatabaseConnectionDto databaseConnectionDto = databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection);
        return databaseConnectionDto;
    }

    @Override
    public void testDatabaseConnection(DatabaseConnectionForm databaseConnectionForm) {
        databaseConnectionService.testDatabaseConnection(databaseConnectionForm);
    }

    @Override
    public List<String> getTables(Long databaseConnectionId) {
        return databaseConnectionService.getTables(databaseConnectionId);
    }

    @Override
    public List<DataSourceColumnDto> getTableColumns(Long databaseConnectionId, String table) {
        List<DataSourceColumn> tableColumns = databaseConnectionService.getTableColumns(databaseConnectionId, table);
        List<DataSourceColumnDto> dataSourceColumnDtos = dataSourceColumnToDataSourceColumnDtoConverter.convertToList(tableColumns);
        return dataSourceColumnDtos;
    }
}
