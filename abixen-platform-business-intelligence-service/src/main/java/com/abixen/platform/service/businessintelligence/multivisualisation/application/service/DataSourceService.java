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

    DataSource find(Long id);

    Page<DataSource> findAll(Pageable pageable, DataSourceType dataSourceType);

    DataSource create(DataSourceForm dataSourceForm);

    DataSource update(DataSourceForm dataSourceForm);

    void delete(Long dataSourceId);

    List<Map<String, DataValueDto>> findPreviewData(DataSourceForm dataSourceForm);

    List<Map<String, Integer>> getAllColumns(Long dataSourceId);

}
