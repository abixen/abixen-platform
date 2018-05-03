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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service;

import com.abixen.platform.service.businessintelligence.multivisualisation.application.converter.DataValueToDataValueDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.file.FileDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFile;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFileColumn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("fileService")
public class FileService {

    private static final int PREVIEW_LIST_SIZE = 10;

    private final DataValueToDataValueDtoConverter dataValueToDataValueDtoConverter;

    @Autowired
    public FileService(DataValueToDataValueDtoConverter dataValueToDataValueDtoConverter) {
        this.dataValueToDataValueDtoConverter = dataValueToDataValueDtoConverter;
    }

    public List<Map<String, DataValueDto>> findChartData(FileDataSource fileDataSource, ChartConfigurationForm chartConfigurationForm, String seriesName) {
        return seriesName != null ? getChartDataPreview(fileDataSource, chartConfigurationForm, seriesName)
                : getChartData(fileDataSource, chartConfigurationForm);
    }

    private List<Map<String, DataValueDto>> getChartData(FileDataSource fileDataSource, ChartConfigurationForm chartConfigurationForm) {
        DataFile dataFile = fileDataSource.getDataFile();
        int size = dataFile.getColumns().stream().findFirst().get().getValues().size();
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
        columnNames.add(chartConfigurationForm.getDataSet().getDomainXSeriesColumn().getDataSourceColumn().getName().toUpperCase());
        chartConfigurationForm.getDataSet().getDataSetSeries().forEach(dataSetSeries -> {
            columnNames.add(dataSetSeries.getValueSeriesColumn().getDataSourceColumn().getName().toUpperCase());
        });
        return columnNames;
    }

    private List<String> getColumnNames(ChartConfigurationForm chartConfigurationForm, String seriesName) {
        List<String> columnNames = new ArrayList<>();
        columnNames.add(chartConfigurationForm.getDataSet().getDomainXSeriesColumn().getDataSourceColumn().getName().toUpperCase());
        chartConfigurationForm.getDataSet().getDataSetSeries().forEach(dataSetSeries -> {
            if (dataSetSeries.getName().toUpperCase().equals(seriesName.toUpperCase())) {
                columnNames.add(dataSetSeries.getValueSeriesColumn().getDataSourceColumn().getName().toUpperCase());
            }
        });
        return columnNames;
    }
}
