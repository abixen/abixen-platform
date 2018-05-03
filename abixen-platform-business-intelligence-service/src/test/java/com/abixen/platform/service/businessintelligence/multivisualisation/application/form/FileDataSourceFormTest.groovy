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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.form

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.FileDataSourceDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceType
import spock.lang.Specification

class FileDataSourceFormTest extends Specification {

    void "should build DataSourceForm from DataSourceDto"() {
        given:
        final DataFileDto dataFileDto = [] as DataFileDto

        final DataSourceColumnDto dataSourceColumnDto = [] as DataSourceColumnDto
        final Set<DataSourceColumnDto> dataSourceColumnDtos = Collections.singleton(dataSourceColumnDto)

        final FileDataSourceDto fileDataSourceDto = new FileDataSourceDto()
                .setDataFile(dataFileDto)
                .setId(1L)
                .setName("name")
                .setDescription("description")
                .setColumns(dataSourceColumnDtos)
                .setDataSourceType(DataSourceType.DB)

        when:
        final FileDataSourceForm fileDataSourceForm = new FileDataSourceForm(fileDataSourceDto)

        then:
        fileDataSourceForm != null
        fileDataSourceForm.getId() == fileDataSourceDto.getId()
        fileDataSourceForm.getName() == fileDataSourceDto.getName()
        fileDataSourceForm.getDescription() == fileDataSourceDto.getDescription()
        fileDataSourceForm.getColumns() == fileDataSourceDto.getColumns()
        fileDataSourceForm.getDataSourceType() == fileDataSourceDto.getDataSourceType()
        fileDataSourceForm.getDataFile() == fileDataSourceDto.getDataFile()

        0 * _
    }
}
