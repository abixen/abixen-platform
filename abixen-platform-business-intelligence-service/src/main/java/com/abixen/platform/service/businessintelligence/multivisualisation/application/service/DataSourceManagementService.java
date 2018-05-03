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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service;

import com.abixen.platform.common.infrastructure.exception.PlatformRuntimeException;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.converter.DataSourceToDataSourceDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DataSourceForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DatabaseDataSourceForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.FileDataSourceForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.database.DatabaseFactory;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.database.DatabaseService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.file.FileDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.DataFileService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.DataSourceService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.DatabaseConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataSourceManagementService {

    private final DataSourceService dataSourceService;
    private final DatabaseConnectionService databaseConnectionService;
    private final DatabaseFactory databaseFactory;
    private final DataFileService dataFileService;
    private final DataSourceToDataSourceDtoConverter dataSourceToDataSourceDtoConverter;

    @Autowired
    public DataSourceManagementService(DataSourceService dataSourceService,
                                       DatabaseConnectionService databaseConnectionService,
                                       DataFileService dataFileService,
                                       DatabaseFactory databaseFactory,
                                       DataSourceToDataSourceDtoConverter dataSourceToDataSourceDtoConverter) {
        this.dataSourceService = dataSourceService;
        this.databaseConnectionService = databaseConnectionService;
        this.dataFileService = dataFileService;
        this.databaseFactory = databaseFactory;
        this.dataSourceToDataSourceDtoConverter = dataSourceToDataSourceDtoConverter;
    }

    public DataSourceDto findDataSource(final Long id) {
        log.debug("findDataSource() - id: {}", id);

        final DataSource dataSource = dataSourceService.find(id);

        return dataSourceToDataSourceDtoConverter.convert(dataSource);
    }

    public Page<DataSourceDto> findAllDataSource(final Pageable pageable, final DataSourceType dataSourceType) {
        log.debug("findAllDataSource() - pageable: {}, dataSourceType: {}", pageable, dataSourceType);

        final Page<DataSource> dataSources = dataSourceService.findAll(pageable, dataSourceType);

        return dataSourceToDataSourceDtoConverter.convertToPage(dataSources);
    }

    public DataSourceForm createDataSource(final DataSourceForm dataSourceForm) {
        log.debug("createDataSource() - dataSourceForm: {}", dataSourceForm);

        final DataSource createdDataSource = dataSourceService.create(buildDataSource(dataSourceForm));
        final DataSourceDto createdDataSourceDto = dataSourceToDataSourceDtoConverter.convert(createdDataSource);

        return new DataSourceForm(createdDataSourceDto);
    }

    public DataSourceForm updateDataSource(final DataSourceForm dataSourceForm) {
        log.debug("updateDataSource() - dataSourceForm: {}", dataSourceForm);

        final DataSource dataSource = dataSourceService.find(dataSourceForm.getId());
        final DataSource updatedDataSource = dataSourceService.update(buildUpdateDataSource(dataSource, dataSourceForm));
        final DataSourceDto updatedDataSourceDto = dataSourceToDataSourceDtoConverter.convert(updatedDataSource);

        return new DataSourceForm(updatedDataSourceDto);
    }

    public void deleteDataSource(final Long id) {
        log.debug("deleteDataSource() - id: {}", id);

        dataSourceService.delete(id);
    }

    public List<Map<String, DataValueDto>> findPreviewData(final DataSourceForm dataSourceForm) {
        log.debug("findPreviewData() - dataSourceForm: {}", dataSourceForm);

        final DatabaseConnectionDto databaseConnection = ((DatabaseDataSourceForm) dataSourceForm).getDatabaseConnection();
        final DatabaseService databaseService = databaseFactory.getDatabaseService(databaseConnection.getDatabaseType());
        final Connection connection = databaseService.getConnection(databaseConnection);
        final List<Map<String, DataValueDto>> dataSourcePreviewData = databaseService.findDataSourcePreview(connection, buildDataSource(dataSourceForm));

        return dataSourcePreviewData;
    }

    private void mapColumnAndAddToResult(List<Map<String, Integer>> result, DataSourceColumn dataSourceColumn) {
        final Map<String, Integer> column = new HashMap<>(1);
        column.put(dataSourceColumn.getName(), dataSourceColumn.getPosition());
        result.add(column);
    }

    private DataSource buildDataSource(DataSourceForm dataSourceForm) {
        DataSource dataSource = null;
        if (dataSourceForm.getId() != null) {
            dataSource = dataSourceService.find(dataSourceForm.getId());
        }
        if (dataSource == null) {
            return buildNewDataSource(dataSourceForm);
        } else {
            return buildUpdateDataSource(dataSource, dataSourceForm);
        }
    }

    private DataSource buildNewDataSource(DataSourceForm dataSourceForm) {
        switch (dataSourceForm.getDataSourceType()) {
            case DB:
                return buildDatabaseDataSource((DatabaseDataSourceForm) dataSourceForm);
            case FILE:
                return buildFileDataSource((FileDataSourceForm) dataSourceForm);
            default:
                throw new PlatformRuntimeException("Type of data source not recognized");
        }
    }

    private DataSource buildUpdateDataSource(DataSource dataSource, DataSourceForm dataSourceForm) {
        switch (dataSourceForm.getDataSourceType()) {
            case DB:
                return updateDatabaseDataSource((DatabaseDataSource) dataSource, (DatabaseDataSourceForm) dataSourceForm);
            case FILE:
                return updateFileDataSource((FileDataSource) dataSource, (FileDataSourceForm) dataSourceForm);
            default:
                throw new PlatformRuntimeException("Type of data source not recognized");
        }
    }

    private DataSource updateFileDataSource(FileDataSource fileDataSource, FileDataSourceForm fileDataSourceForm) {
        fileDataSource.changeDetails(fileDataSourceForm.getName(), fileDataSourceForm.getDescription());
        fileDataSource.changeParameters(fileDataSourceForm.getDataSourceType(), null);
        fileDataSource.changeDataFile(dataFileService.find(fileDataSourceForm.getDataFile().getId()));
        fileDataSource.changeColumns(fileDataSourceForm.getColumns().stream()
                .map(dataSourceColumnDto -> DataSourceColumn.builder()
                        .details(dataSourceColumnDto.getName())
                        .dataSource(fileDataSource)
                        .parameters(dataSourceColumnDto.getDataValueType(), dataSourceColumnDto.getPosition())
                        .build())
                .collect(Collectors.toSet()));
        return fileDataSource;
    }

    private DataSource updateDatabaseDataSource(DatabaseDataSource dataSource, DatabaseDataSourceForm dataSourceForm) {
        dataSource.changeDetails(dataSourceForm.getName(), dataSourceForm.getDescription());
        dataSource.changeFilter(dataSourceForm.getFilter());
        dataSource.changeTable(dataSourceForm.getTable());
        dataSource.changeDatabaseConnection(databaseConnectionService.find(dataSourceForm.getDatabaseConnection().getId()));
        dataSource.changeColumns(dataSourceForm.getColumns().stream()
                .map(dataSourceColumnDto -> DataSourceColumn.builder()
                        .details(dataSourceColumnDto.getName())
                        .parameters(dataSourceColumnDto.getDataValueType(), dataSourceColumnDto.getPosition())
                        .dataSource(dataSource)
                        .build())
                .collect(Collectors.toSet()));
        return dataSource;
    }

    private DataSource buildFileDataSource(FileDataSourceForm fileDataSourceForm) {
        DataSource fileDataSource = FileDataSource.builder()
                .dataFile(dataFileService.find(fileDataSourceForm.getDataFile().getId()))
                .columns(fileDataSourceForm.getColumns().stream()
                        .map(dataSourceColumnDto -> DataSourceColumn.builder()
                                .details(dataSourceColumnDto.getName())
                                .parameters(dataSourceColumnDto.getDataValueType(), dataSourceColumnDto.getPosition())
                                .build())
                        .collect(Collectors.toSet()))
                .details(fileDataSourceForm.getName(), fileDataSourceForm.getDescription())
                .parameters(fileDataSourceForm.getDataSourceType(), null)
                .build();
        fileDataSource.getColumns()
                .forEach(column -> column.changeDataSource(fileDataSource));
        return fileDataSource;
    }

    private DataSource buildDatabaseDataSource(DatabaseDataSourceForm databaseDataSourceForm) {
        DataSource databaseDatasource = DatabaseDataSource.builder()
                .databaseConnection(databaseConnectionService.find(databaseDataSourceForm.getDatabaseConnection().getId()))
                .table(databaseDataSourceForm.getTable())
                .filter(databaseDataSourceForm.getFilter())
                .details(databaseDataSourceForm.getName(), databaseDataSourceForm.getDescription())
                .parameters(databaseDataSourceForm.getDataSourceType(), databaseDataSourceForm.getFilter())
                .columns(databaseDataSourceForm.getColumns().stream()
                        .map(dataSourceColumnDto -> DataSourceColumn.builder()
                                .details(dataSourceColumnDto.getName())
                                .parameters(dataSourceColumnDto.getDataValueType(), dataSourceColumnDto.getPosition())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
        databaseDatasource.getColumns()
                .forEach(column -> column.changeDataSource(databaseDatasource));
        return databaseDatasource;
    }

}