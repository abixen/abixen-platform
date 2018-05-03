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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.FileDataSourceRowDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.file.FileDataSourceRow

import spock.lang.Specification

class FileDataSourceRowToFileDataSourceRowDtoConverterTest extends Specification {

    private FileDataSourceRowToFileDataSourceRowDtoConverter fileDataSourceRowToFileDataSourceRowDtoConverter;

    void setup(){
        fileDataSourceRowToFileDataSourceRowDtoConverter = new FileDataSourceRowToFileDataSourceRowDtoConverter();
    }

    void "should return null when FileDataSourceRow is null"() {
        given:
        final FileDataSourceRow fileDataSourceRow = null

        when:
        final FileDataSourceRowDto fileDataSourceRowDto = fileDataSourceRowToFileDataSourceRowDtoConverter.convert(fileDataSourceRow)

        then:
        fileDataSourceRowDto == null
    }

    void "should convert FileDataSourceRow to FileDataSourceRowDto"() {
        given:
        final FileDataSourceRow fileDataSourceRow = [rowNumber:1] as FileDataSourceRow

        when:
        final FileDataSourceRowDto fileDataSourceRowDto = fileDataSourceRowToFileDataSourceRowDtoConverter.convert(fileDataSourceRow)

        then:
        fileDataSourceRowDto != null
        fileDataSourceRowDto.getId() == fileDataSourceRow.getId()
        fileDataSourceRowDto.getRowNumber() == fileDataSourceRow.getRowNumber()

        0 * _
    }
}
