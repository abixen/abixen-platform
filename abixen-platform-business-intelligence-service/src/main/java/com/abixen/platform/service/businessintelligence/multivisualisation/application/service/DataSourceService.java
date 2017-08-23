package com.abixen.platform.service.businessintelligence.multivisualisation.application.service;

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DataSourceForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataSourceType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DataSourceService {

    DataSource findDataSource(Long id);

    Page<DataSource> findAllDataSources(Pageable pageable, DataSourceType dataSourceType);

    DataSource createDataSource(DataSourceForm dataSourceForm);

    DataSource updateDataSource(DataSourceForm dataSourceForm);

    List<Map<String, DataValueDto>> getPreviewData(DataSourceForm dataSourceForm);

    void deleteDataSource(Long dataSourceId);

    List<Map<String, Integer>> getAllColumns(Long dataSourceId);

}
