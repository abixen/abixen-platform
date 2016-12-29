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

package com.abixen.platform.businessintelligence.chart.service.impl;

import com.abixen.platform.core.exception.DataSourceImportDataException;
import com.abixen.platform.businessintelligence.chart.dto.DataSourceCsvParametersDTO;
import com.abixen.platform.businessintelligence.chart.model.enumtype.DataOrientation;
import com.abixen.platform.businessintelligence.chart.model.enumtype.DataValueType;
import com.abixen.platform.businessintelligence.chart.model.impl.DataSourceColumn;
import com.abixen.platform.businessintelligence.chart.model.impl.DataSourceColumnFile;
import com.abixen.platform.businessintelligence.chart.model.impl.DataSourceValue;
import com.abixen.platform.businessintelligence.chart.model.impl.FileDataSource;
import com.abixen.platform.businessintelligence.chart.repository.FileDataSourceRepository;
import com.abixen.platform.businessintelligence.chart.service.DataSourceImportDataService;
import com.abixen.platform.businessintelligence.chart.service.DomainBuilderService;
import com.abixen.platform.businessintelligence.chart.util.DataSourceColumnFileBuilder;
import com.abixen.platform.businessintelligence.chart.util.DataSourceValueBuilder;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


@Slf4j
@Service
@Transactional
public class DataSourceImportDataServiceCsvImpl implements DataSourceImportDataService<DataSourceCsvParametersDTO> {


    @Autowired
    private DomainBuilderService domainBuilderService;

    private DataSourceCsvParametersDTO parameters;

    private ColumnValues columnValues;

    @Resource
    private FileDataSourceRepository fileDataSourceRepository;


    private void fetchDataByRows() {
        log.debug("fetchDataByRows start");

        if (columnValues == null || columnValues.isEmpty()) {
            throw new DataSourceImportDataException("Configuration is not set");
        }

        try {
            char separator = parameters.getSeparator();
            CSVReader csvReader = new CSVReader(new FileReader(parameters.getCsvFile().getAbsoluteFile()), separator);
            String[] data;
            int rowId = 0;

            while ((data = csvReader.readNext()) != null) {
                if (data.length > 1) {
                    for (int i = 1; i < data.length; i++) {
                        Map columnValue = columnValues.get(i);
                        if (columnValue != null) {
                            columnValue.put(rowId, data[i]);
                        } else {
                            log.info(" Column " + i + " is not used");
                        }
                    }
                }
                rowId++;
            }
        } catch (IOException ioe) {
            throw new DataSourceImportDataException(ioe);
        }
        log.debug("fetchDataByRows finish");
    }


    private void setValuesFromColumn(DataSourceColumnFile dataSourceColumnFile, Integer indexColumn) {
        List<DataSourceValue> result = new ArrayList<>();
        Map<Integer, String> values = columnValues.get(indexColumn);
        DataValueType dataValueType = columnValues.getValueType(indexColumn);
        log.debug("dataValueType : " + dataValueType);

        DataSourceValueBuilder dataSourceValueBuilder = domainBuilderService.newDataSourceValueBuilderInstance(dataValueType);

        for (Map.Entry<Integer, String> valueEntry : values.entrySet()) {

            Integer position = valueEntry.getKey();
            log.debug("position:" + position);

            String value = valueEntry.getValue();
            log.debug("value:" + value);
//

            dataSourceValueBuilder = dataSourceValueBuilder.valueString(value);
            dataSourceValueBuilder = dataSourceValueBuilder.position(position);
            DataSourceValue dataSourceValue = dataSourceValueBuilder.build();

            result.add(dataSourceValue);

        }
        dataSourceColumnFile.setValues(result);
    }


    @Override
    public FileDataSource saveData() {
        log.debug("Start saveData");
        FileDataSource fileDataSource = fileDataSourceRepository.getOne(parameters.getFileDataSource().getId());
        log.debug("fileDataSource " + fileDataSource);

        Map<String, Integer> columns = parameters.getColumns();
        log.debug("iterating over Columns, columnValues.size(): " + columnValues.getItems().size());

        Set<DataSourceColumn> dataSourceColumnFiles = new HashSet<>(1);

        for (Map.Entry<String, Integer> column : columns.entrySet()) {
            DataSourceColumnFileBuilder dataSourceColumnFileBuilder = domainBuilderService.newDataSourceColumnFileBuilderInstance();
            DataSourceColumnFile dataSourceColumnFile = dataSourceColumnFileBuilder.create().build();
            setValuesFromColumn(dataSourceColumnFile, column.getValue());
            dataSourceColumnFile.setName(column.getKey());
            dataSourceColumnFile.setPosition(column.getValue());
            dataSourceColumnFile.setDataSource(fileDataSource);
            dataSourceColumnFiles.add(dataSourceColumnFile);
        }
        fileDataSource.setColumns(dataSourceColumnFiles);
        log.debug("iterating over Series");
        fileDataSourceRepository.save(fileDataSource);
        log.debug("Finish saveData");
        return fileDataSource;


    }

    /**
     *
     * initial setup columnValues by using parameters input configuration
     */
    void initColumnValues() {
        //column index - values
        log.debug("Start initialization columnValues");
        columnValues = new ColumnValues();
        columnValues.setValueTypes(parameters.getValueTypes());
        log.debug("initSeries iterate over columns");
        Map<String, Integer> columns = parameters.getColumns();
        for (Map.Entry<String, Integer> column : columns.entrySet()) {
            columnValues.addEmptyValue(column.getValue());
        }
        log.debug("Finish initialization columnValues");
    }

    @Override
    public void init(DataSourceCsvParametersDTO parameters) throws DataSourceImportDataException {
        log.debug("Start initialization, parameters:" + parameters);

        this.parameters = parameters;

        if (parameters.getOrientation() == DataOrientation.Rows) {
            initColumnValues();
            fetchDataByRows();
        }
        log.debug("Finish initialization");
    }

    @Override
    public Boolean verify() {
        //todo
        return true;
    }

}


/**
 * Class used to gather values for given column index
 * items [ key => index Column, values => [ key => position, values => real value ]
 */
final class ColumnValues {

    public Map<Integer, Map<Integer, String>> getItems() {
        return items;
    }

    private final Map<Integer, Map<Integer, String>> items = new HashMap<>();
    private List<DataValueType> valueTypes;


    ColumnValues() {

    }

    @Override
    public String toString() {
        return "ColumnValue{" +
                ", items=" + items +
                '}';
    }

    DataValueType getValueType(int columnIdx) {
        return valueTypes.get(columnIdx);
    }

    void setValueTypes(List<DataValueType> valueTypes) {
        this.valueTypes = valueTypes;
    }

    void addEmptyValue(Integer index) {
        if (!items.containsKey(index)) {
            items.put(index, new HashMap<>());
        }
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public Map get(int i) {
        return this.items.get(i);
    }
}
