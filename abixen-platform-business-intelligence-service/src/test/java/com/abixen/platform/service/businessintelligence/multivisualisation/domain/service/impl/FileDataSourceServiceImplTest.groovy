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

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.file.FileDataSource
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFile
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.FileDataSourceRepository
import spock.lang.Specification

class FileDataSourceServiceImplTest extends Specification {

    private FileDataSourceRepository fileDataSourceRepository
    private FileDataSourceServiceImpl fileDataSourceService

    void setup() {
        fileDataSourceRepository = Mock()
        fileDataSourceService = new FileDataSourceServiceImpl(fileDataSourceRepository)
    }

    def "should find FileDataSource"() {
        given:
        final DataFile dataFile = [] as DataFile

        final FileDataSource fileDataSource = [] as FileDataSource
        final List<FileDataSource> fileDataSources = Collections.singletonList(fileDataSource)

        fileDataSourceRepository.findByDataFile(dataFile) >> fileDataSources

        when:
        final List<FileDataSource> foundFileDataSources = fileDataSourceService.find(dataFile)

        then:
        foundFileDataSources != null
        foundFileDataSources == fileDataSources

        1 * fileDataSourceRepository.findByDataFile(dataFile) >> fileDataSources
        0 * _
    }
}
