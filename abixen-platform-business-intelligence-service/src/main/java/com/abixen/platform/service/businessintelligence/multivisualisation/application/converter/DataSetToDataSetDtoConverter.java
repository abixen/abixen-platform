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
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetSeriesColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataSetToDataSetDtoConverter extends AbstractConverter<DataSet, DataSetDto> {

    private final DataSetSeriesColumnToDataSetSeriesColumnDtoConverter dataSetSeriesColumnToDataSetSeriesColumnDtoConverter;
    private final DataSetSeriesToDataSetSeriesDtoConverter dataSetSeriesToDataSetSeriesDtoConverter;

    @Autowired
    public DataSetToDataSetDtoConverter(DataSetSeriesColumnToDataSetSeriesColumnDtoConverter dataSetSeriesColumnToDataSetSeriesColumnDtoConverter,
                                        DataSetSeriesToDataSetSeriesDtoConverter dataSetSeriesToDataSetSeriesDtoConverter) {
        this.dataSetSeriesColumnToDataSetSeriesColumnDtoConverter = dataSetSeriesColumnToDataSetSeriesColumnDtoConverter;
        this.dataSetSeriesToDataSetSeriesDtoConverter = dataSetSeriesToDataSetSeriesDtoConverter;
    }

    @Override
    public DataSetDto convert(final DataSet dataSet, final Map<String, Object> parameters) {
        if (dataSet == null) {
            return null;
        }

        final DataSetSeriesColumnDto domainXSeriesColumn = dataSetSeriesColumnToDataSetSeriesColumnDtoConverter.convert(dataSet.getDomainXSeriesColumn());
        final DataSetSeriesColumnDto domainZSeriesColumn = dataSetSeriesColumnToDataSetSeriesColumnDtoConverter.convert(dataSet.getDomainZSeriesColumn());

        final DataSetDto dataSetDto = DataSetDto.builder()
                .id(dataSet.getId())
                .domainXSeriesColumn(domainXSeriesColumn)
                .domainZSeriesColumn(domainZSeriesColumn)
                .dataSetSeries(dataSetSeriesToDataSetSeriesDtoConverter.convertToSet(dataSet.getDataSetSeries()))
                .build();

        return dataSetDto;
    }
}