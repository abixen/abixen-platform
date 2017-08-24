package com.abixen.platform.service.businessintelligence.multivisualisation.application.service.impl;

import com.abixen.platform.common.exception.PlatformRuntimeException;
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
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataSourceRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DatabaseConnectionRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DataSourceService;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DatabaseFactory;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DatabaseService;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DomainBuilderService;
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

@Service
public class DataSourceServiceImpl implements DataSourceService {

    private final DataSourceRepository dataSourceRepository;
    private final DomainBuilderService domainBuilderService;
    private final DatabaseConnectionRepository databaseConnectionRepository;
    private final DataFileRepository dataFileRepository;
    private final DatabaseFactory databaseFactory;

    @Autowired
    public DataSourceServiceImpl(DataSourceRepository dataSourceRepository,
                                 DomainBuilderService domainBuilderService,
                                 DatabaseConnectionRepository databaseConnectionRepository,
                                 DataFileRepository dataFileRepository,
                                 DatabaseFactory databaseFactory) {
        this.dataSourceRepository = dataSourceRepository;
        this.domainBuilderService = domainBuilderService;
        this.databaseConnectionRepository = databaseConnectionRepository;
        this.dataFileRepository = dataFileRepository;
        this.databaseFactory = databaseFactory;
    }

    @Override
    public DataSource findDataSource(Long id) {
        return dataSourceRepository.findOne(id);
    }

    @Override
    public Page<DataSource> findAllDataSources(Pageable pageable, DataSourceType dataSourceType) {
        if (dataSourceType != null) {
            return dataSourceRepository.findByDataSourceType(dataSourceType, pageable);
        } else {
            return dataSourceRepository.findAll(pageable);
        }
    }

    @Override
    public DataSource createDataSource(DataSourceForm dataSourceForm) {
        return dataSourceRepository.save(buildDataSource(dataSourceForm));
    }

    @Override
    public DataSource updateDataSource(DataSourceForm dataSourceForm) {
        return dataSourceRepository.save(buildDataSource(dataSourceForm));
    }

    @Override
    public List<Map<String, DataValueDto>> getPreviewData(DataSourceForm dataSourceForm) {
        DatabaseConnectionDto databaseConnection = ((DatabaseDataSourceForm) dataSourceForm).getDatabaseConnection();
        DatabaseService databaseService = databaseFactory.getDatabaseService(databaseConnection.getDatabaseType());
        Connection connection = databaseService.getConnection(databaseConnection);
        List<Map<String, DataValueDto>> dataSourcePreviewData = databaseService.getDataSourcePreview(connection, buildDataSource(dataSourceForm));
        return dataSourcePreviewData;
    }

    @Override
    public void deleteDataSource(Long dataSourceId) {
        dataSourceRepository.delete(dataSourceId);
    }

    @Override
    public List<Map<String, Integer>> getAllColumns(Long dataSourceId) {
        List<Map<String, Integer>> result = new ArrayList<>();
        dataSourceRepository.findOne(dataSourceId)
                .getColumns().forEach(dataSourceColumn -> mapColumnAndAddToResult(result, dataSourceColumn));
        return result;
    }

    private void mapColumnAndAddToResult(List<Map<String, Integer>> result, DataSourceColumn dataSourceColumn) {
        Map<String, Integer> column = new HashMap<>(1);
        column.put(dataSourceColumn.getName(), dataSourceColumn.getPosition());
        result.add(column);
    }

    private DataSource buildDataSource(DataSourceForm dataSourceForm) {
        DataSource dataSource = null;
        if (dataSourceForm.getId() != null) {
            dataSource = dataSourceRepository.findOne(dataSourceForm.getId());
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
                databaseDataSource.changeDatabaseConnection(databaseConnectionRepository.findOne(((DatabaseDataSourceForm) dataSourceForm).getDatabaseConnection().getId()));
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
                        .databaseConnection(((DatabaseDataSourceForm) dataSourceForm).getDatabaseConnection().getId(), databaseConnectionRepository)
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
