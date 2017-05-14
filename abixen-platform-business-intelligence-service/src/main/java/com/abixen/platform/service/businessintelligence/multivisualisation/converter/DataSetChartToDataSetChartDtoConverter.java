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

package com.abixen.platform.service.businessintelligence.multivisualisation.converter;

import com.abixen.platform.common.converter.AbstractConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataSetChartDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.DataSetChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataSetChartToDataSetChartDtoConverter extends AbstractConverter<DataSetChart, DataSetChartDto> {

    private final DataSetSeriesColumnToDataSetSeriesColumnDtoConverter dataSetSeriesColumnToDataSetSeriesColumnDtoConverter;
    private final DataSetSeriesToDataSetSeriesDtoConverter dataSetSeriesToDataSetSeriesDtoConverter;

    @Autowired
    public DataSetChartToDataSetChartDtoConverter(DataSetSeriesColumnToDataSetSeriesColumnDtoConverter dataSetSeriesColumnToDataSetSeriesColumnDtoConverter,
                                                  DataSetSeriesToDataSetSeriesDtoConverter dataSetSeriesToDataSetSeriesDtoConverter) {
        this.dataSetSeriesColumnToDataSetSeriesColumnDtoConverter = dataSetSeriesColumnToDataSetSeriesColumnDtoConverter;
        this.dataSetSeriesToDataSetSeriesDtoConverter = dataSetSeriesToDataSetSeriesDtoConverter;
    }

    @Override
    public DataSetChartDto convert(DataSetChart dataSetChart, Map<String, Object> parameters) {
        if (dataSetChart == null) {
            return null;
        }

        DataSetChartDto dataSetChartDto = new DataSetChartDto();
        dataSetChartDto.setId(dataSetChart.getId());
        dataSetChartDto.setDomainXSeriesColumn(dataSetSeriesColumnToDataSetSeriesColumnDtoConverter.convert(dataSetChart.getDomainXSeriesColumn()));
        dataSetChartDto.setDomainZSeriesColumn(dataSetSeriesColumnToDataSetSeriesColumnDtoConverter.convert(dataSetChart.getDomainZSeriesColumn()));
        dataSetChartDto.setDataSetSeries(dataSetSeriesToDataSetSeriesDtoConverter.convertToSet(dataSetChart.getDataSetSeries()));

        return dataSetChartDto;
    }
}
