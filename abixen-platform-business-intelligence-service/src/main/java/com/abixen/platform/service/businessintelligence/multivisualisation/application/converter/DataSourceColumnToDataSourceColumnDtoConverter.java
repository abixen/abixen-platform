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
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceColumn;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataSourceColumnToDataSourceColumnDtoConverter extends AbstractConverter<DataSourceColumn, DataSourceColumnDto> {
    @Override
    public DataSourceColumnDto convert(DataSourceColumn dataSourceColumn, Map<String, Object> parameters) {
        if (dataSourceColumn == null) {
            return null;
        }

        DataSourceColumnDto dataSourceColumnDto = new DataSourceColumnDto();

        dataSourceColumnDto.setId(dataSourceColumn.getId())
                .setName(dataSourceColumn.getName())
                .setPosition(dataSourceColumn.getPosition())
                .setDataValueType(dataSourceColumn.getDataValueType());

        return dataSourceColumnDto;
    }
}
