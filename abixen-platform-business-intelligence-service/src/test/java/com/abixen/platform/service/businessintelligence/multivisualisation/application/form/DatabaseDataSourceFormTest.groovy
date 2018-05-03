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
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseConnectionDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseDataSourceDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceType
import spock.lang.Specification

class DatabaseDataSourceFormTest extends Specification {

    void "should build DatabaseDataSourceForm from DatabaseDataSourceDto"() {
        given:
        final DatabaseConnectionDto databaseConnectionDto = [] as DatabaseConnectionDto

        final DataSourceColumnDto dataSourceColumnDto = [] as DataSourceColumnDto
        final Set<DataSourceColumnDto> dataSourceColumnDtos = Collections.singleton(dataSourceColumnDto)

        final DatabaseDataSourceDto databaseDataSourceDto = (DatabaseDataSourceDto) new DatabaseDataSourceDto()
                .setDatabaseConnection(databaseConnectionDto)
                .setTable("table")
                .setId(1L)
                .setName("name")
                .setDescription("description")
                .setFilter("filter")
                .setColumns(dataSourceColumnDtos)
                .setDataSourceType(DataSourceType.DB)

        when:
        final DatabaseDataSourceForm databaseDataSourceForm = new DatabaseDataSourceForm(databaseDataSourceDto)

        then:
        databaseDataSourceForm != null
        databaseDataSourceForm.getId() == databaseDataSourceDto.getId()
        databaseDataSourceForm.getName() == databaseDataSourceDto.getName()
        databaseDataSourceForm.getDescription() == databaseDataSourceDto.getDescription()
        databaseDataSourceForm.getFilter() == databaseDataSourceDto.getFilter()
        databaseDataSourceForm.getColumns() == databaseDataSourceDto.getColumns()
        databaseDataSourceForm.getTable() == databaseDataSourceDto.getTable()
        databaseDataSourceForm.getDataSourceType() == databaseDataSourceDto.getDataSourceType()
        databaseDataSourceForm.getDatabaseConnection() == databaseDataSourceDto.getDatabaseConnection()

        0 * _
    }
}
