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

import com.abixen.platform.common.infrastructure.exception.PlatformRuntimeException;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.converter.DataSourceToDataSourceDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DataSourceForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DatabaseDataSourceForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.FileDataSourceForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataSourceType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumnBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.database.DatabaseDataSourceBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.file.FileDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.file.FileDataSourceBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataFileRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DataSourceManagementService;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DatabaseFactory;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DatabaseService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.DataSourceService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.DatabaseConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataSourceManagementServiceImpl implements DataSourceManagementService {

    private final DataSourceService dataSourceService;
    private final DatabaseConnectionService databaseConnectionService;
    private final DatabaseFactory databaseFactory;
    private final DataFileRepository dataFileRepository;
    private final DataSourceToDataSourceDtoConverter dataSourceToDataSourceDtoConverter;

    @Autowired
    public DataSourceManagementServiceImpl(DataSourceService dataSourceService,
                                           DatabaseConnectionService databaseConnectionService,
                                           DataFileRepository dataFileRepository,
                                           DatabaseFactory databaseFactory,
                                           DataSourceToDataSourceDtoConverter dataSourceToDataSourceDtoConverter) {
        this.dataSourceService = dataSourceService;
        this.databaseConnectionService = databaseConnectionService;
        this.dataFileRepository = dataFileRepository;
        this.databaseFactory = databaseFactory;
        this.dataSourceToDataSourceDtoConverter = dataSourceToDataSourceDtoConverter;
    }

    @Override
    public DataSourceDto findDataSource(final Long id) {
        log.debug("findDataSource() - id: {}", id);

        final DataSource dataSource = dataSourceService.find(id);

        return dataSourceToDataSourceDtoConverter.convert(dataSource);
    }

    @Override
    public Page<DataSourceDto> findAllDataSource(final Pageable pageable, final DataSourceType dataSourceType) {
        log.debug("findAllDataSource() - pageable: {}, dataSourceType: {}", pageable, dataSourceType);

        final Page<DataSource> dataSources = dataSourceService.findAll(pageable, dataSourceType);

        return dataSourceToDataSourceDtoConverter.convertToPage(dataSources);
    }

    @Override
    public DataSourceDto createDataSource(final DataSourceForm dataSourceForm) {
        log.debug("createDataSource() - dataSourceForm: {}", dataSourceForm);

        final DataSource dataSource = dataSourceService.create(buildDataSource(dataSourceForm));

        return dataSourceToDataSourceDtoConverter.convert(dataSource);
    }

    @Override
    public DataSourceDto updateDataSource(final DataSourceForm dataSourceForm) {
        log.debug("updateDataSource() - dataSourceForm: {}", dataSourceForm);

        final DataSource dataSource = dataSourceService.find(dataSourceForm.getId());
        final DataSource updatedDataSource = dataSourceService.update(buildUpdateDataSource(dataSource, dataSourceForm));

        return dataSourceToDataSourceDtoConverter.convert(updatedDataSource);
    }

    @Override
    public void deleteDataSource(final Long id) {
        log.debug("deleteDataSource() - id: {}", id);

        dataSourceService.delete(id);
    }

    @Override
    public List<Map<String, Integer>> findAllColumnsInDataSource(final Long dataSourceId) {
        log.debug("findAllColumnsInDataSource() - dataSourceId: {}", dataSourceId);

        final List<Map<String, Integer>> result = new ArrayList<>();
        dataSourceService.find(dataSourceId)
                .getColumns().forEach(dataSourceColumn -> mapColumnAndAddToResult(result, dataSourceColumn));

        return result;
    }

    @Override
    public List<Map<String, DataValueDto>> findPreviewData(final DataSourceForm dataSourceForm) {
        log.debug("findPreviewData() - dataSourceForm: {}", dataSourceForm);

        final DatabaseConnectionDto databaseConnection = ((DatabaseDataSourceForm) dataSourceForm).getDatabaseConnection();
        final DatabaseService databaseService = databaseFactory.getDatabaseService(databaseConnection.getDatabaseType());
        final Connection connection = databaseService.getConnection(databaseConnection);
        final List<Map<String, DataValueDto>> dataSourcePreviewData = databaseService.getDataSourcePreview(connection, buildDataSource(dataSourceForm));

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

    private DataSource buildUpdateDataSource(DataSource dataSource, DataSourceForm dataSourceForm) {
        switch (dataSourceForm.getDataSourceType()) {
            case DB:
                DatabaseDataSource databaseDataSource = (DatabaseDataSource) dataSource;
                databaseDataSource.changeDetails(dataSourceForm.getName(), dataSourceForm.getDescription());
                databaseDataSource.changeFilter(((DatabaseDataSourceForm) dataSourceForm).getFilter());
                databaseDataSource.changeTable(((DatabaseDataSourceForm) dataSourceForm).getTable());
                databaseDataSource.changeDatabaseConnection(databaseConnectionService.find(((DatabaseDataSourceForm) dataSourceForm).getDatabaseConnection().getId()));
                databaseDataSource.changeColumns(dataSourceForm.getColumns().stream()
                        .map(dataSourceColumnDto -> new DataSourceColumnBuilder()
                                .details(dataSourceColumnDto.getName())
                                .paramters(dataSourceColumnDto.getDataValueType(), dataSourceColumnDto.getPosition())
                                .dataSource(databaseDataSource)
                                .build())
                        .collect(Collectors.toSet()));
                return databaseDataSource;
            case FILE:
                FileDataSource fileDataSource = (FileDataSource) dataSource;
                FileDataSourceForm fileDataSourceForm = (FileDataSourceForm) dataSourceForm;
                fileDataSource.changeDetails(fileDataSourceForm.getName(), fileDataSourceForm.getDescription());
                fileDataSource.changeParameters(fileDataSourceForm.getDataSourceType(), null);
                fileDataSource.changeDataFile(dataFileRepository.findOne(fileDataSourceForm.getDataFile().getId()));
                fileDataSource.changeColumns(fileDataSourceForm.getColumns().stream()
                        .map(dataSourceColumnDto -> new DataSourceColumnBuilder()
                                .details(dataSourceColumnDto.getName())
                                .dataSource(fileDataSource)
                                .paramters(dataSourceColumnDto.getDataValueType(), dataSourceColumnDto.getPosition())
                                .build())
                        .collect(Collectors.toSet()));
                return fileDataSource;
            default: throw new PlatformRuntimeException("Type of data source not recognized");
        }
    }

    private DataSource buildNewDataSource(DataSourceForm dataSourceForm) {
        switch (dataSourceForm.getDataSourceType()) {
            case DB:
                DataSource databaseDatasource = new DatabaseDataSourceBuilder()
                        .databaseConnection(databaseConnectionService.find(((DatabaseDataSourceForm) dataSourceForm).getDatabaseConnection().getId()))
                        .table(((DatabaseDataSourceForm) dataSourceForm).getTable())
                        .filter(((DatabaseDataSourceForm) dataSourceForm).getFilter())
                        .details(dataSourceForm.getName(), dataSourceForm.getDescription())
                        .paramters(dataSourceForm.getDataSourceType(), ((DatabaseDataSourceForm) dataSourceForm).getFilter())
                        .columns(dataSourceForm.getColumns().stream()
                                .map(dataSourceColumnDto -> new DataSourceColumnBuilder()
                                        .details(dataSourceColumnDto.getName())
                                        .paramters(dataSourceColumnDto.getDataValueType(), dataSourceColumnDto.getPosition())
                                        .build())
                                .collect(Collectors.toSet()))
                        .build();
                databaseDatasource.getColumns()
                        .forEach(column -> column.changeDataSource(databaseDatasource));
                return databaseDatasource;
            case FILE:
                DataSource fileDataSource = new FileDataSourceBuilder()
                        .dataFile(((FileDataSourceForm) dataSourceForm).getDataFile().getId(), dataFileRepository)
                        .columns(dataSourceForm.getColumns().stream()
                                .map(dataSourceColumnDto -> new DataSourceColumnBuilder()
                                        .details(dataSourceColumnDto.getName())
                                        .paramters(dataSourceColumnDto.getDataValueType(), dataSourceColumnDto.getPosition())
                                        .build())
                                .collect(Collectors.toSet()))
                        .details(dataSourceForm.getName(), dataSourceForm.getDescription())
                        .paramters(dataSourceForm.getDataSourceType(), null)
                        .build();
                fileDataSource.getColumns()
                    .forEach(column -> column.changeDataSource(fileDataSource));
                return fileDataSource;
            default: throw new PlatformRuntimeException("Type of data source not recognized");
        }
    }

}
