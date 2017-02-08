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

import com.abixen.platform.service.businessintelligence.multivisualisation.form.DataFileForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.data.DataValue;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.data.DataValueDouble;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.data.DataValueInteger;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.data.DataValueString;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFile;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFileColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.repository.DataFileRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DataFileService;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DomainBuilderService;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Slf4j
@Service
public class DataFileServiceImpl implements DataFileService {

    @Resource
    private DataFileRepository dataFileRepository;

    @Autowired
    private DomainBuilderService domainBuilderService;

    @Override
    public Page<DataFile> getDataFile(String jsonCriteria, Pageable pageable) {
        Page<DataFile> result;
        result = dataFileRepository.findAll(pageable);
        return result;
    }

    @Override
    public Page<DataFile> findAllDataFile(Pageable pageable) {
        return dataFileRepository.findAll(pageable);
    }

    @Override
    public List<DataFileColumn> getDataFileColumns(Long dataFileId) {
        List<DataFileColumn> result;
        DataFile dataFile = dataFileRepository.getOne(dataFileId);
        result = dataFile.getColumns();
        return result;
    }

    @Override
    public DataFile buildDataFile(DataFileForm fileDataForm) {
        return domainBuilderService.newDataFileBuilderInstance()
            .base(fileDataForm.getName(), fileDataForm.getDescription())
            .data(fileDataForm.getColumns())
            .build();
    }

    @Override
    public DataFileForm createDataFile(DataFileForm dataFileForm) {
        DataFile dataFile = buildDataFile(dataFileForm);
        return new DataFileForm(createDataFile(dataFile));
    }

    @Override
    public DataFile createDataFile(DataFile dataFile) {
        log.debug("createDataFile() - dataFile: " + dataFile);
        DataFile createdDataFile = dataFileRepository.save(dataFile);
        return createdDataFile;
    }

    @Override
    public DataFileForm updateDataFile(DataFileForm dataFileForm) {
        log.debug("updateDataFile() - fileDataForm: " + dataFileForm);

        DataFile dataFile = findDataFile(dataFileForm.getId());
        dataFile.setName(dataFileForm.getName());
        dataFile.setDescription(dataFileForm.getDescription());
        List<DataFileColumn> columns = new ArrayList<>();
        dataFileForm.getColumns().forEach(entity -> {
            DataFileColumn dataFileColumn = new DataFileColumn();
            dataFileColumn.setName(entity.getName());
            List<DataValue> values = new ArrayList<>();
            entity.getValues().forEach(child -> {
                if (child != null && child.getValue() != null) {
                    String value = child.getValue().trim();
                    DataValue dataValue = getObjForValue(value);
                    dataValue.setDataColumn(dataFileColumn);
                    values.add(dataValue);
                }
            });
            dataFileColumn.setValues(values);
            dataFileColumn.setDataFile(dataFile);
            columns.add(dataFileColumn);
        });
        dataFile.setColumns(columns);

        return new DataFileForm(updateDataFile(dataFile));
    }

    @Override
    public DataFile updateDataFile(DataFile fileData) {
        log.debug("updateDataFile() - fileData: " + fileData);
        return dataFileRepository.save(fileData);
    }

    @Override
    public DataFile findDataFile(Long id) {
        log.debug("findDataFile() - id: " + id);
        return dataFileRepository.findOne(id);
    }


    @Override
    public List<Map<String, Integer>> getAllColumns(Long dataFileId) {

        List<Map<String, Integer>> result = new ArrayList<>();
        DataFile dataFile = dataFileRepository.getOne(dataFileId);
        int position = 0;
        for (DataFileColumn dataFileColumn : dataFile.getColumns()) {
            String name = dataFileColumn.getName();
            Map<String, Integer> column = new HashMap<>(1);
            column.put(name, position);
            result.add(column);
            position++;
        }
        return result;
    }

    private DataValue getObjForValue(String value) {
        DataValue dataValue;
        if (value == null) {
            dataValue = new DataValueString();
            dataValue.setValue("");
        } else {
            if (NumberUtils.isNumber(value)) {
                if (Ints.tryParse(value) != null) {
                    dataValue = new DataValueInteger();
                    dataValue.setValue(Integer.parseInt(value));
                } else {
                    dataValue = new DataValueDouble();
                    dataValue.setValue(Double.parseDouble(value));
                }
            } else {
                dataValue = new DataValueString();
                dataValue.setValue(value);
            }
        }
        return dataValue;
    }
}
