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
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseConnectionDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseDataSourceDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceType
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.connection.DatabaseConnection
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceColumn
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.database.DatabaseDataSource
import spock.lang.Specification

class DatabaseDataSourceToDatabaseDataSourceDtoConverterTest extends Specification {

    private AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter
    private DatabaseConnectionToDatabaseConnectionDtoConverter databaseConnectionToDatabaseConnectionDtoConverter
    private DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter
    private DatabaseDataSourceToDatabaseDataSourceDtoConverter databaseDataSourceToDatabaseDataSourceDtoConverter

    void setup(){
        auditingModelToSimpleAuditingDtoConverter = Mock()
        databaseConnectionToDatabaseConnectionDtoConverter = Mock()
        dataSourceColumnToDataSourceColumnDtoConverter = Mock()
        databaseDataSourceToDatabaseDataSourceDtoConverter = new DatabaseDataSourceToDatabaseDataSourceDtoConverter(auditingModelToSimpleAuditingDtoConverter,
                                                                                                                    databaseConnectionToDatabaseConnectionDtoConverter,
                                                                                                                    dataSourceColumnToDataSourceColumnDtoConverter)
    }

    void "should return null when DatabaseDataSource is null"() {
        given:
        final DatabaseDataSource databaseDataSource = null

        when:
        final DatabaseDataSourceDto databaseDataSourceDto = databaseDataSourceToDatabaseDataSourceDtoConverter.convert(databaseDataSource)

        then:
        databaseDataSourceDto == null
    }

    void "should convert DatabaseDataSource to DatabaseDataSourceDto"() {
        given:
        final DatabaseConnection databaseConnection = [] as DatabaseConnection

        final DataSourceColumn dataSourceColumn = [] as DataSourceColumn
        final Set<DataSourceColumn> dataSourceColumns = Collections.singleton(dataSourceColumn)

        final DatabaseDataSource databaseDataSource = DatabaseDataSource.builder()
                .databaseConnection(databaseConnection)
                .filter("filter")
                .table("table")
                .details("name", "description")
                .parameters(DataSourceType.DB, "filter")
                .columns(dataSourceColumns)
                .build()

        final DatabaseConnectionDto databaseConnectionDto = [] as DatabaseConnectionDto

        final DataSourceColumnDto dataSourceColumnDto = [] as DataSourceColumnDto
        final Set<DataSourceColumnDto> dataSourceColumnDtos = Collections.singleton(dataSourceColumnDto)

        databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection) >> databaseConnectionDto
        dataSourceColumnToDataSourceColumnDtoConverter.convertToSet(dataSourceColumns) >> dataSourceColumnDtos

        when:
        final DatabaseDataSourceDto databaseDataSourceDto = databaseDataSourceToDatabaseDataSourceDtoConverter.convert(databaseDataSource)

        then:
        databaseDataSourceDto != null
        databaseDataSourceDto.getId() == databaseDataSource.getId()
        databaseDataSourceDto.getName() == databaseDataSource.getName()
        databaseDataSourceDto.getDescription() == databaseDataSource.getDescription()
        databaseDataSourceDto.getDataSourceType() == databaseDataSource.getDataSourceType()
        databaseDataSourceDto.getFilter() == databaseDataSource.getFilter()
        databaseDataSourceDto.getTable() == databaseDataSource.getTable()
        databaseDataSourceDto.getDatabaseConnection() == databaseConnectionDto
        databaseDataSourceDto.getColumns() == dataSourceColumnDtos

        1 * databaseConnectionToDatabaseConnectionDtoConverter.convert(databaseConnection) >> databaseConnectionDto
        1 * dataSourceColumnToDataSourceColumnDtoConverter.convertToSet(dataSourceColumns) >> dataSourceColumnDtos
        1 * auditingModelToSimpleAuditingDtoConverter.convert(_, _)
        0 * _
    }

}
