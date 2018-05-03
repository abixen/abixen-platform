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
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataSetSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataSetSeriesToDataSetSeriesDtoConverter extends AbstractConverter<DataSetSeries, DataSetSeriesDto> {

    private final DataSetSeriesColumnToDataSetSeriesColumnDtoConverter dataSetSeriesColumnToDataSetSeriesColumnDtoConverter;

    @Autowired
    public DataSetSeriesToDataSetSeriesDtoConverter(DataSetSeriesColumnToDataSetSeriesColumnDtoConverter dataSetSeriesColumnToDataSetSeriesColumnDtoConverter) {
        this.dataSetSeriesColumnToDataSetSeriesColumnDtoConverter = dataSetSeriesColumnToDataSetSeriesColumnDtoConverter;
    }

    @Override
    public DataSetSeriesDto convert(DataSetSeries dataSetSeries, Map<String, Object> parameters) {
        if (dataSetSeries == null) {
            return null;
        }

        DataSetSeriesDto dataSetSeriesDto = new DataSetSeriesDto();

        dataSetSeriesDto.setId(dataSetSeries.getId())
                .setName(dataSetSeries.getName())
                .setFilter(dataSetSeries.getFilter())
                .setValueSeriesColumn(dataSetSeriesColumnToDataSetSeriesColumnDtoConverter.convert(dataSetSeries.getValueSeriesColumn()));

        return dataSetSeriesDto;
    }
}
