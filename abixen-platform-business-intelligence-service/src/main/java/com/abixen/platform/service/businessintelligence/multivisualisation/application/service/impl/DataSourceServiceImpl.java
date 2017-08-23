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
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.file.FileDataSource;
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
        switch (dataSourceForm.getDataSourceType()) {
            case DB: return domainBuilderService.newDatabaseDataSourceBuilderInstance((DatabaseDataSource) dataSource)
                    .base(dataSourceForm.getName(), dataSourceForm.getDescription())
                    .connection(((DatabaseDataSourceForm) dataSourceForm).getDatabaseConnection(), databaseConnectionRepository)
                    .data(((DatabaseDataSourceForm) dataSourceForm).getTable(), ((DatabaseDataSourceForm) dataSourceForm).getFilter())
                    .columns(dataSourceForm.getColumns())
                    .build();
            case FILE: return domainBuilderService.newFileDataSourceBuilderInstance((FileDataSource) dataSource)
                    .base(dataSourceForm.getName(), dataSourceForm.getDescription())
                    .data(dataSourceForm.getColumns(), ((FileDataSourceForm) dataSourceForm).getDataFile(), dataFileRepository)
                    .build();
            default: throw new PlatformRuntimeException("Type of data source not recognized");
        }
    }

}
