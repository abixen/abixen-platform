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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileColumnDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileDto
import spock.lang.Specification

class DataFileFormTest extends Specification {

    void "should build DataFileForm from DataFileDto"() {
        given:
        final DataFileColumnDto dataFileColumnDto = [] as DataFileColumnDto
        final Set<DataFileColumnDto> dataFileColumnDtos = Collections.singleton(dataFileColumnDto)

        final DataFileDto dataFileDto = new DataFileDto()
                .setId(1L)
                .setName("name")
                .setDescription("description")
                .setColumns(dataFileColumnDtos)

        when:
        final DataFileForm dataFileForm = new DataFileForm(dataFileDto)

        then:
        dataFileForm != null
        dataFileForm.getId() == dataFileDto.getId()
        dataFileForm.getName() == dataFileDto.getName()
        dataFileForm.getDescription() == dataFileDto.getDescription()
        dataFileForm.getColumns() == dataFileDto.getColumns()

        0 * _
    }
}
