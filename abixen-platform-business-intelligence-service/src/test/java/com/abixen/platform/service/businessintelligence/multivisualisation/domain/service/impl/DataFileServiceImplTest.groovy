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

import com.abixen.platform.common.infrastructure.exception.PlatformRuntimeException
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.file.FileDataSource
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFile
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFileColumn
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataFileRepository
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.DataFileService
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.FileDataSourceService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification


class DataFileServiceImplTest extends Specification {

    private DataFileRepository dataFileRepository
    private FileDataSourceService fileDataSourceService
    private DataFileService dataFileService

    void setup() {
        dataFileRepository = Mock()
        fileDataSourceService = Mock()
        dataFileService = new DataFileService(fileDataSourceService, dataFileRepository)
    }

    void "should find DataFile"() {
        given:
        final Long id = 1L

        final DataFile dataFile = [] as DataFile

        dataFileRepository.findOne(id) >> dataFile

        when:
        final DataFile foundDataFile = dataFileService.find(id)

        then:
        foundDataFile != null
        foundDataFile == dataFile

        1 * dataFileRepository.findOne(id) >> dataFile
        0 * _
    }

    void "should findAll DataFiles"() {
        given:
        final Pageable pageable = new PageRequest(0, 1)

        final DataFile dataFile = [] as DataFile
        final Page<DataFile> dataFiles = new PageImpl<DataFile>([dataFile])

        dataFileRepository.findAll(pageable) >> dataFiles

        when:
        final Page<DataFile> foundDataFiles = dataFileService.findAll(pageable)

        then:
        foundDataFiles != null
        foundDataFiles == dataFiles

        1 * dataFileRepository.findAll(pageable) >> dataFiles
        0 * _
    }

    void "should create DataFile"() {
        given:
        final DataFileColumn dataFileColumn = [] as DataFileColumn
        final Set<DataFileColumn> dataFileColumns = Collections.singleton(dataFileColumn)
        final DataFile dataFile = DataFile.builder()
                .details("name", "description")
                .columns(dataFileColumns)
                .build()

        dataFileRepository.save(dataFile) >> dataFile

        when:
        final DataFile createdDataFile = dataFileService.create(dataFile)

        then:
        createdDataFile != null
        createdDataFile.getId() == dataFile.getId()
        createdDataFile.getName() == dataFile.getName()
        createdDataFile.getDescription() == dataFile.getDescription()
        createdDataFile.getColumns() == dataFile.getColumns()

        1 * dataFileRepository.save(dataFile) >> dataFile
        0 * _
    }

    void "should update DataFile"() {
        given:
        final DataFileColumn dataFileColumn = [] as DataFileColumn
        final Set<DataFileColumn> dataFileColumns = Collections.singleton(dataFileColumn)
        final DataFile dataFile = DataFile.builder()
                .details("name", "description")
                .columns(dataFileColumns)
                .build()

        dataFileRepository.save(dataFile) >> dataFile

        when:
        final DataFile createdDataFile = dataFileService.update(dataFile)

        then:
        createdDataFile != null
        createdDataFile.getId() == dataFile.getId()
        createdDataFile.getName() == dataFile.getName()
        createdDataFile.getDescription() == dataFile.getDescription()
        createdDataFile.getColumns() == dataFile.getColumns()

        1 * dataFileRepository.save(dataFile) >> dataFile
        0 * _
    }

    void "should delete DataFile when list of related file data sources is empty"() {
        given:
        final Long id = 1L

        final DataFile dataFile = [] as DataFile
        final List<FileDataSource> relatedFileDataSources = Collections.emptyList()

        dataFileRepository.findOne(id) >> dataFile
        fileDataSourceService.find(dataFile) >> relatedFileDataSources

        when:
        dataFileService.delete(id)

        then:
        1 * dataFileRepository.findOne(id) >> dataFile
        1 * fileDataSourceService.find(dataFile) >> relatedFileDataSources
        1 * dataFileRepository.delete(id)
        0 * _
    }

    void "should throw exception on delete DataFile when list of related file data sources is not empty"() {
        given:
        final Long id = 1L

        final DataFile dataFile = [] as DataFile
        final FileDataSource fileDataSource = [] as FileDataSource
        final List<FileDataSource> relatedFileDataSources = Collections.singletonList(fileDataSource)

        dataFileRepository.findOne(id) >> dataFile
        fileDataSourceService.find(dataFile) >> relatedFileDataSources

        when:
        dataFileService.delete(id)

        then:
        final PlatformRuntimeException platformRuntimeException = thrown()

        1 * dataFileRepository.findOne(id) >> dataFile
        1 * fileDataSourceService.find(dataFile) >> relatedFileDataSources
        0 * _
    }

}