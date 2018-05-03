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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.converter;

import com.abixen.platform.common.application.converter.AbstractConverter;
import com.abixen.platform.common.infrastructure.exception.PlatformRuntimeException;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.file.FileDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataSourceToDataSourceDtoConverter extends AbstractConverter<DataSource, DataSourceDto> {

    private final DatabaseDataSourceToDatabaseDataSourceDtoConverter databaseDataSourceToDatabaseDataSourceDtoConverter;
    private final FileDataSourceToFileDataSourceDtoConverter fileDataSourceToFileDataSourceDtoConverter;

    @Autowired
    public DataSourceToDataSourceDtoConverter(DatabaseDataSourceToDatabaseDataSourceDtoConverter databaseDataSourceToDatabaseDataSourceDtoConverter,
                                              FileDataSourceToFileDataSourceDtoConverter fileDataSourceToFileDataSourceDtoConverter) {
        this.databaseDataSourceToDatabaseDataSourceDtoConverter = databaseDataSourceToDatabaseDataSourceDtoConverter;
        this.fileDataSourceToFileDataSourceDtoConverter = fileDataSourceToFileDataSourceDtoConverter;
    }

    @Override
    public DataSourceDto convert(DataSource dataSource, Map<String, Object> parameters) {
        if (dataSource == null) {
            return null;
        }

        switch (dataSource.getDataSourceType()) {
            case DB: return databaseDataSourceToDatabaseDataSourceDtoConverter.convert((DatabaseDataSource) dataSource);
            case FILE: return fileDataSourceToFileDataSourceDtoConverter.convert((FileDataSource) dataSource);
            default:
                throw new PlatformRuntimeException("DataSource type not supported");
        }
    }
}