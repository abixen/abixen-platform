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
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFile
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFileBuilder
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFileColumn
import spock.lang.Specification

class DataFileToDataFileDtoConverterTest extends Specification {

    private static final String NAME = "name"
    public static final HashSet<DataFileColumn> COLUMNS = new HashSet<DataFileColumn>()
    public static final String DESCRIPTION = "description"


    private DataFileColumnToDataFileColumnDtoConverter dataFileColumnToDataFileColumnDtoConverter;
    private AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter;
    private DataFileToDataFileDtoConverter dataFileToDataFileDtoConverter;

    void setup() {
        auditingModelToSimpleAuditingDtoConverter = Mock();
        dataFileColumnToDataFileColumnDtoConverter = Mock();
        dataFileToDataFileDtoConverter = new DataFileToDataFileDtoConverter(dataFileColumnToDataFileColumnDtoConverter, auditingModelToSimpleAuditingDtoConverter);
    }

    void "should return null when dataFile is null"() {
        given:
        final DataFile dataFile = null;

        when:
        final DataFileDto result = dataFileToDataFileDtoConverter.convert(dataFile)

        then:
        result == null;
    }

    void "should convert DataFile to DataFileDto"() {
        given:
        final DataFile dataFile = new DataFileBuilder()
                .details(NAME, DESCRIPTION)
                .columns(COLUMNS)
                .build()



        final Set<DataFileColumnDto> dataFileColumnDtoSet = dataFileColumnToDataFileColumnDtoConverter.convertToSet(COLUMNS)

        when:
        final DataFileDto result = dataFileToDataFileDtoConverter.convert(dataFile);

        then:
        result.id == dataFile.id;
        result.name == dataFile.name;
        result.description == dataFile.description;
        result.columns == dataFileColumnDtoSet;
    }
}
