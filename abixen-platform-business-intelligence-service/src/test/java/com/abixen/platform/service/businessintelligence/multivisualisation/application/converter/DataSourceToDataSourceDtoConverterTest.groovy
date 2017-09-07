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

import com.abixen.platform.common.infrastructure.exception.PlatformRuntimeException
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseDataSourceDto
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.FileDataSourceDto
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataSourceType
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceBuilder
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.database.DatabaseDataSource
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.database.DatabaseDataSourceBuilder
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.file.FileDataSource
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.file.FileDataSourceBuilder
import spock.lang.Specification

class DataSourceToDataSourceDtoConverterTest extends Specification {

    private DatabaseDataSourceToDatabaseDataSourceDtoConverter databaseDataSourceToDatabaseDataSourceDtoConverter;
    private FileDataSourceToFileDataSourceDtoConverter fileDataSourceToFileDataSourceDtoConverter;
    private DataSourceToDataSourceDtoConverter dataSourceToDataSourceDtoConverter;

    void setup() {
        databaseDataSourceToDatabaseDataSourceDtoConverter = Mock()
        fileDataSourceToFileDataSourceDtoConverter = Mock()
        dataSourceToDataSourceDtoConverter = new DataSourceToDataSourceDtoConverter(databaseDataSourceToDatabaseDataSourceDtoConverter,
                                                                                    fileDataSourceToFileDataSourceDtoConverter)
    }

    void "should return null when DataSource is null"() {
        given:
        final DataSource dataSource = null

        when:
        final DataSourceDto dataSourceDto = dataSourceToDataSourceDtoConverter.convert(dataSource)

        then:
        dataSourceDto == null
    }

    void "should return result from DatabaseDataSourceConverter when dataSourceType is DB"() {
        given:
        final DataSource dataSource = new DatabaseDataSourceBuilder()
            .paramters(DataSourceType.DB, "filter")
            .build()

        final DatabaseDataSourceDto databaseDataSourceDto = [] as DatabaseDataSourceDto

        databaseDataSourceToDatabaseDataSourceDtoConverter.convert((DatabaseDataSource) dataSource) >> databaseDataSourceDto

        when:
        final DataSourceDto dataSourceDto = dataSourceToDataSourceDtoConverter.convert(dataSource)

        then:
        dataSourceDto != null
        dataSourceDto instanceof DatabaseDataSourceDto

        1 * databaseDataSourceToDatabaseDataSourceDtoConverter.convert((DatabaseDataSource) dataSource) >> databaseDataSourceDto
        0 * _
    }

    void "should return result from FileDataSourceConverter when dataSourceType is FILE"() {
        given:
        final DataSource dataSource = new FileDataSourceBuilder()
                .paramters(DataSourceType.FILE, "filter")
                .build()

        final FileDataSourceDto fileDataSourceDto = [] as FileDataSourceDto

        fileDataSourceToFileDataSourceDtoConverter.convert((FileDataSource) dataSource) >> fileDataSourceDto

        when:
        final DataSourceDto dataSourceDto = dataSourceToDataSourceDtoConverter.convert(dataSource)

        then:
        dataSourceDto != null
        dataSourceDto instanceof FileDataSourceDto

        1 * fileDataSourceToFileDataSourceDtoConverter.convert((FileDataSource) dataSource) >> fileDataSourceDto
        0 * _
    }
}
