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

import com.abixen.platform.service.businessintelligence.multivisualisation.facade.DataSourceFacade;
import com.abixen.platform.service.businessintelligence.multivisualisation.converter.DataSourceToDataSourceDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.form.DataSourceForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DataSourceType;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class DataSourceFacadeImpl implements DataSourceFacade {

    private final DataSourceService dataSourceService;
    private final DataSourceToDataSourceDtoConverter dataSourceToDataSourceDtoConverter;

    @Autowired
    public DataSourceFacadeImpl(DataSourceService dataSourceService,
                                DataSourceToDataSourceDtoConverter dataSourceToDataSourceDtoConverter) {
        this.dataSourceService = dataSourceService;
        this.dataSourceToDataSourceDtoConverter = dataSourceToDataSourceDtoConverter;
    }

    @Override
    public DataSourceDto findDataSource(Long dataSourceId) {
        DataSource dataSource = dataSourceService.findDataSource(dataSourceId);
        DataSourceDto dataSourceDto = dataSourceToDataSourceDtoConverter.convert(dataSource);
        return dataSourceDto;
    }

    @Override
    public Page<DataSourceDto> findAllDataSources(Pageable pageable, DataSourceType dataSourceType) {
        Page<DataSource> dataSources = dataSourceService.findAllDataSources(pageable, dataSourceType);
        Page<DataSourceDto> dataSourceDtos = dataSourceToDataSourceDtoConverter.convertToPage(dataSources);
        return dataSourceDtos;
    }

    @Override
    public DataSourceDto createDataSource(DataSourceForm dataSourceForm) {
        DataSource dataSource = dataSourceService.createDataSource(dataSourceForm);
        DataSourceDto dataSourceDto = dataSourceToDataSourceDtoConverter.convert(dataSource);
        return dataSourceDto;
    }

    @Override
    public DataSourceDto updateDataSource(DataSourceForm dataSourceForm) {
        DataSource dataSource = dataSourceService.updateDataSource(dataSourceForm);
        DataSourceDto dataSourceDto = dataSourceToDataSourceDtoConverter.convert(dataSource);
        return dataSourceDto;
    }

    @Override
    public List<Map<String, DataValueDto>> getPreviewData(DataSourceForm dataSourceForm) {
        return dataSourceService.getPreviewData(dataSourceForm);
    }

    @Override
    public void deleteDataSource(Long dataSourceId) {
        dataSourceService.deleteDataSource(dataSourceId);
    }
}
