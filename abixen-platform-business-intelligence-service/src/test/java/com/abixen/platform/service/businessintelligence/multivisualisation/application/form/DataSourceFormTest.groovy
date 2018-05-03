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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceType
import spock.lang.Specification

class DataSourceFormTest extends Specification {

    void "should build DataSourceForm from DataSourceDto"() {
        given:
        final DataSourceColumnDto dataSourceColumnDto = [] as DataSourceColumnDto
        final Set<DataSourceColumnDto> dataSourceColumnDtos = Collections.singleton(dataSourceColumnDto)

        final DataSourceDto dataSourceDto = new DataSourceDto()
                .setId(1L)
                .setName("name")
                .setDescription("description")
                .setColumns(dataSourceColumnDtos)
                .setDataSourceType(DataSourceType.DB)

        when:
        final DataSourceForm dataSourceForm = new DataSourceForm(dataSourceDto)

        then:
        dataSourceForm != null
        dataSourceForm.getId() == dataSourceDto.getId()
        dataSourceForm.getName() == dataSourceDto.getName()
        dataSourceForm.getDescription() == dataSourceDto.getDescription()
        dataSourceForm.getColumns() == dataSourceDto.getColumns()
        dataSourceForm.getDataSourceType() == dataSourceDto.getDataSourceType()

        0 * _
    }
}
