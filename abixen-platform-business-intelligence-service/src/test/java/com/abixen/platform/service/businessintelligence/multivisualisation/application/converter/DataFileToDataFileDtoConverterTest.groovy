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
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileColumnDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFile
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFileColumn
import spock.lang.Specification

class DataFileToDataFileDtoConverterTest extends Specification {

    private AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter
    private DataFileColumnToDataFileColumnDtoConverter dataFileColumnToDataFileColumnDtoConverter
    private DataFileToDataFileDtoConverter dataFileToDataFileDtoConverter

    void setup() {
        dataFileColumnToDataFileColumnDtoConverter = Mock()
        auditingModelToSimpleAuditingDtoConverter = Mock()
        dataFileToDataFileDtoConverter = new DataFileToDataFileDtoConverter(dataFileColumnToDataFileColumnDtoConverter, auditingModelToSimpleAuditingDtoConverter)
    }

    void "should return null when DataFile is null"() {
        given:
        final DataFile dataFile = null

        when:
        final DataFileDto dataFileDto = dataFileToDataFileDtoConverter.convert(dataFile)

        then:
        dataFileDto == null
    }

    void "should convert DataFile to DataFileDto"() {
        given:
        final DataFileColumn dataFileColumn = [] as DataFileColumn
        final Set<DataFileColumn> dataFileColumns = Collections.singleton(dataFileColumn)

        final DataFile dataFile = DataFile.builder()
                .details("name", "description")
                .columns(dataFileColumns)
                .build()

        final DataFileColumnDto dataFileColumnDto = new DataFileColumnDto()
        final Set<DataFileColumnDto> dataFileColumnDtos = Collections.singleton(dataFileColumnDto)

        dataFileColumnToDataFileColumnDtoConverter.convertToSet(dataFileColumns) >> dataFileColumnDtos

        when:
        final DataFileDto dataFileDto = dataFileToDataFileDtoConverter.convert(dataFile)

        then:
        dataFileDto != null
        dataFileDto.getId() == dataFile.getId()
        dataFileDto.getName() == dataFile.getName()
        dataFileDto.getDescription() == dataFile.getDescription()
        dataFileDto.getColumns() == dataFileColumnDtos

        1 * dataFileColumnToDataFileColumnDtoConverter.convertToSet(dataFileColumns) >> dataFileColumnDtos
        1 * auditingModelToSimpleAuditingDtoConverter.convert(_, _)
        0 * _
    }

}