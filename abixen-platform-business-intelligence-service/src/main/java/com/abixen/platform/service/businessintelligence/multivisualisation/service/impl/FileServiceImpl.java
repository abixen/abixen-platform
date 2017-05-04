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

package com.abixen.platform.service.businessintelligence.multivisualisation.service.impl;

import com.abixen.platform.service.businessintelligence.multivisualisation.converter.DataValueToDataValueDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.file.FileDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFile;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFileColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.*;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service("fileService")
public class FileServiceImpl implements FileService {

    private static final int INDEX_OF_FIRST_ELEMENT = 0;
    private static final int PREVIEW_LIST_SIZE = 10;

    private DataValueToDataValueDtoConverter dataValueToDataValueDtoConverter;

    @Autowired
    public FileServiceImpl(DataValueToDataValueDtoConverter dataValueToDataValueDtoConverter) {
        this.dataValueToDataValueDtoConverter = dataValueToDataValueDtoConverter;
    }

    @Override
    public List<Map<String, DataValueDto>> getChartData(FileDataSource fileDataSource, ChartConfigurationForm chartConfigurationForm, String seriesName) {
        return seriesName != null ? getChartDataPreview(fileDataSource, chartConfigurationForm, seriesName)
                : getChartData(fileDataSource, chartConfigurationForm);
    }

    private List<Map<String, DataValueDto>> getChartData(FileDataSource fileDataSource, ChartConfigurationForm chartConfigurationForm) {
        DataFile dataFile = fileDataSource.getDataFile();
        int size = dataFile.getColumns().get(INDEX_OF_FIRST_ELEMENT).getValues().size();
        List<String> columnNames = getColumnNames(chartConfigurationForm);
        return getData(dataFile, size, columnNames);
    }

    private List<Map<String, DataValueDto>> getChartDataPreview(FileDataSource fileDataSource, ChartConfigurationForm chartConfigurationForm, String seriesName) {
        return getData(fileDataSource.getDataFile(), PREVIEW_LIST_SIZE, getColumnNames(chartConfigurationForm, seriesName));
    }

    private List<Map<String, DataValueDto>> getData(DataFile dataFile, int size, List<String> columnNames) {
        List<Map<String, DataValueDto>> data = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            data.add(getDataFromRow(dataFile, columnNames, rowIndex));
        }
        return data;
    }

    private Map<String, DataValueDto> getDataFromRow(DataFile dataFile, List<String> columnNames, Integer rowIndex) {
        Map<String, DataValueDto> rowMap = new HashMap<>();
        for (DataFileColumn dataFileColumn : dataFile.getColumns()) {
            if (columnNames.contains(dataFileColumn.getName().toUpperCase())) {
                rowMap.put(dataFileColumn.getName(), dataValueToDataValueDtoConverter.convert(dataFileColumn.getValues().get(rowIndex)));
            }
        }
        return rowMap;
    }

    private List<String> getColumnNames(ChartConfigurationForm chartConfigurationForm) {
        List<String> columnNames = new ArrayList<>();
        columnNames.add(chartConfigurationForm.getDataSetChart().getDomainXSeriesColumn().getDataSourceColumn().getName().toUpperCase());
        chartConfigurationForm.getDataSetChart().getDataSetSeries().forEach(dataSetSeries -> {
            columnNames.add(dataSetSeries.getValueSeriesColumn().getDataSourceColumn().getName().toUpperCase());
        });
        return columnNames;
    }

    private List<String> getColumnNames(ChartConfigurationForm chartConfigurationForm, String seriesName) {
        List<String> columnNames = new ArrayList<>();
        columnNames.add(chartConfigurationForm.getDataSetChart().getDomainXSeriesColumn().getDataSourceColumn().getName().toUpperCase());
        chartConfigurationForm.getDataSetChart().getDataSetSeries().forEach(dataSetSeries -> {
            if (dataSetSeries.getName().toUpperCase().equals(seriesName.toUpperCase())) {
                columnNames.add(dataSetSeries.getValueSeriesColumn().getDataSourceColumn().getName().toUpperCase());
            }
        });
        return columnNames;
    }
}
