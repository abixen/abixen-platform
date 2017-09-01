package com.abixen.platform.service.businessintelligence.multivisualisation.application.service;

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DataSourceForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataSourceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DataSourceManagementService {

    DataSourceDto findDataSource(final Long id);

    Page<DataSourceDto> findAllDataSource(final Pageable pageable, final DataSourceType dataSourceType);

    DataSourceDto createDataSource(final DataSourceForm dataSourceForm);

    DataSourceDto updateDataSource(final DataSourceForm dataSourceForm);

    void deleteDataSource(final Long dataSourceId);

    List<Map<String, DataValueDto>> findPreviewData(final DataSourceForm dataSourceForm);

    List<Map<String, Integer>> findAllColumnsInDataSource(final Long dataSourceId);

}
