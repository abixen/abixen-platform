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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.impl

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataSourceType
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumn
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.file.FileDataSourceBuilder
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataSourceRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class DataSourceServiceImplTest extends Specification {

    private DataSourceRepository dataSourceRepository
    private DataSourceServiceImpl dataSourceService

    void setup() {
        dataSourceRepository = Mock()
        dataSourceService = new DataSourceServiceImpl(dataSourceRepository)
    }

    void "should find DataSource"() {
        given:
        final Long id = 1L

        final DataSource dataSource = [] as DataSource

        dataSourceRepository.findOne(id) >> dataSource

        when:
        final DataSource foundDataSource = dataSourceService.find(id)

        then:
        foundDataSource != null
        foundDataSource == dataSource

        1 * dataSourceRepository.findOne(id) >> dataSource
        0 * _
    }

    void "should findAll Database DataSource when dataSourceType is not null"() {
        given:
        final Pageable pageable = new PageRequest(0, 1)

        final DataSourceType dataSourceType = DataSourceType.DB

        final DataSource dataSource = [] as DataSource
        final Page<DataSource> dataSources = new PageImpl<DataSource>([dataSource])

        dataSourceRepository.findByDataSourceType(dataSourceType, pageable) >> dataSources

        when:
        final Page<DataSource> foundDataSources = dataSourceService.findAll(pageable, dataSourceType)

        then:
        foundDataSources != null
        foundDataSources == dataSources

        1 * dataSourceRepository.findByDataSourceType(dataSourceType, pageable) >> dataSources
        0 * _
    }

    void "should findAll DataSource when dataSourceType is null"() {
        given:
        final Pageable pageable = new PageRequest(0, 1)

        final DataSourceType dataSourceType = null

        final DataSource dataSource = [] as DataSource
        final Page<DataSource> dataSources = new PageImpl<DataSource>([dataSource])

        dataSourceRepository.findAll(pageable) >> dataSources

        when:
        final Page<DataSource> foundDataSources = dataSourceService.findAll(pageable, dataSourceType)

        then:
        foundDataSources != null
        foundDataSources == dataSources

        1 * dataSourceRepository.findAll(pageable) >> dataSources
        0 * _
    }

    void "should create DataSource"() {
        given:
        final DataSourceColumn dataSourceColumn = [] as DataSourceColumn
        final Set<DataSourceColumn> dataSourceColumns = Collections.singleton(dataSourceColumn)

        final DataSource dataSource = new FileDataSourceBuilder()
                .paramters(DataSourceType.FILE, "filter")
                .details("name", "description")
                .columns(dataSourceColumns)
                .build()

        dataSourceRepository.save(dataSource) >> dataSource

        when:
        final DataSource createdDataSource = dataSourceService.create(dataSource)

        then:
        createdDataSource != null
        createdDataSource.getId() == dataSource.getId()
        createdDataSource.getName() == dataSource.getName()
        createdDataSource.getDescription() == dataSource.getDescription()
        createdDataSource.getDataSourceType() == dataSource.getDataSourceType()
        createdDataSource.getFilter() == dataSource.getFilter()
        createdDataSource.getColumns() == dataSource.getColumns()

        1 * dataSourceRepository.save(dataSource) >> dataSource
        0 * _
    }

    void "should update DataSource"() {
        given:
        final DataSourceColumn dataSourceColumn = [] as DataSourceColumn
        final Set<DataSourceColumn> dataSourceColumns = Collections.singleton(dataSourceColumn)

        final DataSource dataSource = new FileDataSourceBuilder()
                .paramters(DataSourceType.FILE, "filter")
                .details("name", "description")
                .columns(dataSourceColumns)
                .build()

        dataSourceRepository.save(dataSource) >> dataSource

        when:
        final DataSource createdDataSource = dataSourceService.update(dataSource)

        then:
        createdDataSource != null
        createdDataSource.getId() == dataSource.getId()
        createdDataSource.getName() == dataSource.getName()
        createdDataSource.getDescription() == dataSource.getDescription()
        createdDataSource.getDataSourceType() == dataSource.getDataSourceType()
        createdDataSource.getFilter() == dataSource.getFilter()
        createdDataSource.getColumns() == dataSource.getColumns()

        1 * dataSourceRepository.save(dataSource) >> dataSource
        0 * _
    }

    void "should DataSource DatabaseConnection"() {
        given:
        final Long id = 1L

        when:
        dataSourceService.delete(id)

        then:
        1 * dataSourceRepository.delete(id)
        0 * _
    }
}
