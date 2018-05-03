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
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataSetSeriesColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataSetSeriesColumnToDataSetSeriesColumnDtoConverter extends AbstractConverter<DataSetSeriesColumn, DataSetSeriesColumnDto> {

    private final DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter;

    @Autowired
    public DataSetSeriesColumnToDataSetSeriesColumnDtoConverter(DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter) {
        this.dataSourceColumnToDataSourceColumnDtoConverter = dataSourceColumnToDataSourceColumnDtoConverter;
    }

    @Override
    public DataSetSeriesColumnDto convert(DataSetSeriesColumn dataSetSeriesColumn, Map<String, Object> parameters) {
        if (dataSetSeriesColumn == null) {
            return null;
        }

        DataSetSeriesColumnDto dataSetSeriesColumnDto = new DataSetSeriesColumnDto();

        dataSetSeriesColumnDto.setId(dataSetSeriesColumn.getId())
                .setName(dataSetSeriesColumn.getName())
                .setType(dataSetSeriesColumn.getType())
                .setDataSourceColumn(dataSourceColumnToDataSourceColumnDtoConverter.convert(dataSetSeriesColumn.getDataSourceColumn()));

        return dataSetSeriesColumnDto;
    }
}
