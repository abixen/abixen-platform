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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.converter

import com.abixen.platform.common.application.converter.AuditingModelToSimpleAuditingDtoConverter
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.FileDataSourceDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.FileDataSourceRowDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceType
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceColumn
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.file.FileDataSource
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.file.FileDataSourceRow
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFile
import spock.lang.Specification

class FileDataSourceToFileDataSourceDtoConverterTest extends Specification {

    private AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter
    private FileDataSourceRowToFileDataSourceRowDtoConverter fileDataSourceRowToFileDataSourceRowDtoConverter
    private DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter
    private DataFileToDataFileDtoConverter dataFileToDataFileDtoConverter
    private FileDataSourceToFileDataSourceDtoConverter fileDataSourceToFileDataSourceDtoConverter

    void setup() {
        auditingModelToSimpleAuditingDtoConverter = Mock()
        fileDataSourceRowToFileDataSourceRowDtoConverter = Mock()
        dataSourceColumnToDataSourceColumnDtoConverter = Mock()
        dataFileToDataFileDtoConverter = Mock()
        fileDataSourceToFileDataSourceDtoConverter = new FileDataSourceToFileDataSourceDtoConverter(auditingModelToSimpleAuditingDtoConverter,
                                                                                                    fileDataSourceRowToFileDataSourceRowDtoConverter,
                                                                                                    dataSourceColumnToDataSourceColumnDtoConverter,
                                                                                                    dataFileToDataFileDtoConverter)
    }

    void "should return null when FileDataSourceRow is null"() {
        given:
        final FileDataSource fileDataSource = null

        when:
        final FileDataSourceDto fileDataSourceDto = fileDataSourceToFileDataSourceDtoConverter.convert(fileDataSource)

        then:
        fileDataSourceDto == null
    }

    void "should convert FileDataSource to FileDataSourceDto"() {
        given:
        final FileDataSourceRow fileDataSourceRow = [] as FileDataSourceRow
        final Set<FileDataSourceRow> fileDataSourceRows = Collections.singleton(fileDataSourceRow)

        final DataFile dataFile = [] as DataFile

        final DataSourceColumn dataSourceColumn = [] as DataSourceColumn
        final Set<DataSourceColumn> dataSourceColumns = Collections.singleton(dataSourceColumn)

        final FileDataSource fileDataSource = FileDataSource.builder()
            .rows(fileDataSourceRows)
            .dataFile(dataFile)
            .details("name", "description")
            .parameters(DataSourceType.FILE, "filter")
            .columns(dataSourceColumns)
            .build()

        final FileDataSourceRowDto fileDataSourceRowDto = [] as FileDataSourceRowDto
        final Set<FileDataSourceRowDto> fileDataSourceRowDtos = Collections.singleton(fileDataSourceRowDto)

        final DataFileDto dataFileDto = [] as DataFileDto

        final DataSourceColumnDto dataSourceColumnDto = [] as DataSourceColumnDto
        final Set<DataSourceColumnDto> dataSourceColumnDtos = Collections.singleton(dataSourceColumnDto)

        fileDataSourceRowToFileDataSourceRowDtoConverter.convertToSet(fileDataSourceRows) >> fileDataSourceRowDtos
        dataFileToDataFileDtoConverter.convert(dataFile) >> dataFileDto
        dataSourceColumnToDataSourceColumnDtoConverter.convertToSet(dataSourceColumns) >> dataSourceColumnDtos

        when:
        final FileDataSourceDto fileDataSourceDto = fileDataSourceToFileDataSourceDtoConverter.convert(fileDataSource)

        then:
        fileDataSourceDto != null
        fileDataSourceDto.getId() == fileDataSource.getId()
        fileDataSourceDto.getName() == fileDataSource.getName()
        fileDataSourceDto.getDescription() == fileDataSource.getDescription()
        fileDataSourceDto.getDataSourceType() == fileDataSource.getDataSourceType()
        fileDataSourceDto.getFilter() == fileDataSource.getFilter()
        fileDataSourceDto.getRows() == fileDataSourceRowDtos
        fileDataSourceDto.getDataFile() == dataFileDto
        fileDataSourceDto.getColumns() == dataSourceColumnDtos

        1 * fileDataSourceRowToFileDataSourceRowDtoConverter.convertToSet(fileDataSourceRows) >> fileDataSourceRowDtos
        1 * dataFileToDataFileDtoConverter.convert(dataFile) >> dataFileDto
        1 * dataSourceColumnToDataSourceColumnDtoConverter.convertToSet(dataSourceColumns) >> dataSourceColumnDtos
        1 * auditingModelToSimpleAuditingDtoConverter.convert(_, _)
        0 * _
    }
}


