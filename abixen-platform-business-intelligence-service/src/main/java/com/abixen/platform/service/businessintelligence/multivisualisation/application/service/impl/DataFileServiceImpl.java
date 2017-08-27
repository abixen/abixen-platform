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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service.impl;

import com.abixen.platform.common.exception.PlatformRuntimeException;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.FileDataParserService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.data.*;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumnBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFileBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFileColumnBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.web.facade.converter.DataFileToDataFileDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DataFileForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.file.FileDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFile;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFileColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataFileRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.FileDataSourceRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DataFileService;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataFileServiceImpl implements DataFileService {

    private final DataFileRepository dataFileRepository;
    private final FileDataSourceRepository fileDataSourceRepository;
    private final FileParserFactory fileParserFactory;
    private final DataFileToDataFileDtoConverter dataFileToDataFileDtoConverter;

    @Autowired
    public DataFileServiceImpl(FileDataSourceRepository fileDataSourceRepository,
                               FileParserFactory fileParserFactory,
                               DataFileRepository dataFileRepository,
                               DataFileToDataFileDtoConverter dataFileToDataFileDtoConverter) {
        this.fileDataSourceRepository = fileDataSourceRepository;
        this.fileParserFactory = fileParserFactory;
        this.dataFileRepository = dataFileRepository;
        this.dataFileToDataFileDtoConverter = dataFileToDataFileDtoConverter;
    }

    @Override
    public Page<DataFile> find(String jsonCriteria, Pageable pageable) {
        Page<DataFile> result;
        result = dataFileRepository.findAll(pageable);
        return result;
    }

    @Override
    public DataFile find(Long id) {
        log.debug("find() - id: " + id);
        return dataFileRepository.findOne(id);
    }

    @Override
    public Page<DataFile> findAll(Pageable pageable) {
        return dataFileRepository.findAll(pageable);
    }

    @Override
    public DataFile create(DataFileForm dataFileForm) {
        DataFile dataFile = build(dataFileForm);
        return create(dataFile);
    }

    @Override
    public DataFile create(DataFile dataFile) {
        log.debug("create() - dataFile: " + dataFile);
        DataFile createdDataFile = dataFileRepository.save(dataFile);
        return createdDataFile;
    }

    @Override
    public DataFile update(DataFileForm dataFileForm) {
        log.debug("update() - fileDataForm: " + dataFileForm);

        DataFile dataFile = find(dataFileForm.getId());
        dataFile.changeDetails(dataFileForm.getName(), dataFileForm.getDescription());
        dataFile.changeColumns(dataFileForm.getColumns().stream()
                .map(dataFileColumnDto -> (DataFileColumn) new DataFileColumnBuilder()
                        .dataValueType(dataFileColumnDto.getDataValueType())
                        .name(dataFileColumnDto.getName())
                        .values(dataFileColumnDto.getValues().stream()
                                .map(dataValueDto -> getObjForValue(dataValueDto.getValue().toString().trim()))
                                .collect(Collectors.toList()))
                        .position(dataFileColumnDto.getPosition())
                        .build()
                ).collect(Collectors.toSet()));

        dataFile.getColumns()
                .forEach(dataFileColumn -> {
                    dataFileColumn.changeDataFile(dataFile);
                    dataFileColumn.getValues().forEach(dataValue -> dataValue.setDataColumn(dataFileColumn));
                });

        return update(dataFile);
    }

    @Override
    public DataFile update(DataFile fileData) {
        log.debug("update() - fileData: " + fileData);
        return dataFileRepository.save(fileData);
    }

    @Override
    public void delete(Long id) {
        List<FileDataSource> relatedFileDataSources = fileDataSourceRepository.findByDataFile(dataFileRepository.getOne(id));
        if (relatedFileDataSources.isEmpty()) {
            dataFileRepository.delete(id);
        } else {
            throw new PlatformRuntimeException("You need to remove all data sources related to this data file");
        }
    }

    @Override
    public DataFile build(DataFileForm dataFileForm) {
        DataFile dataFile = new DataFileBuilder()
                .details(dataFileForm.getName(), dataFileForm.getDescription())
                .columns(dataFileForm.getColumns().stream()
                        .map(dataFileColumnDto -> (DataFileColumn) new DataFileColumnBuilder()
                                .dataValueType(dataFileColumnDto.getDataValueType())
                                .name(dataFileColumnDto.getName())
                                .values(dataFileColumnDto.getValues().stream()
                                        .map(dataValueDto -> getObjForValue(dataValueDto.getValue().toString().trim()))
                                        .collect(Collectors.toList()))
                                .position(dataFileColumnDto.getPosition())
                                .build()
                        ).collect(Collectors.toSet()))
                .build();
        dataFile.getColumns()
                .forEach(dataFileColumn -> {
                    dataFileColumn.changeDataFile(dataFile);
                    dataFileColumn.getValues().forEach(dataValue -> dataValue.setDataColumn(dataFileColumn));
                });

        return dataFile;
    }

    @Override
    public List<DataSourceColumn> getDataFileColumns(Long dataFileId) {
        List<DataSourceColumn> result = new ArrayList<>();
        DataFile dataFile = dataFileRepository.getOne(dataFileId);
        for (DataFileColumn dataFileColumn : dataFile.getColumns()) {
            result.add(new DataSourceColumnBuilder()
                    .details(dataFileColumn.getName())
                    .paramters(dataFileColumn.getDataValueType(), dataFileColumn.getPosition())
                    .build());
        }
        return result;
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

    @Override
    public FileParserMessage<DataFileColumn> parse(MultipartFile multipartFile, Boolean readFirstColumnAsColumnName) {
        String fileName = multipartFile.getOriginalFilename();
        FileDataParserService fileDataParserService = fileParserFactory.getParse(fileName.substring(fileName.lastIndexOf(".")));
        return fileDataParserService.parse(multipartFile, readFirstColumnAsColumnName);
    }

    private DataValue getObjForValue(String value) {
        if (value == null) {
            return new DataValueStringBuilder()
                    .value(StringUtils.EMPTY)
                    .build();
        } else {
            if (NumberUtils.isNumber(value)) {
                if (Ints.tryParse(value) != null) {
                    return new DataValueIntegerBuilder()
                            .value(Integer.parseInt(value))
                            .build();
                } else {
                    return new DataValueDoubleBuilder()
                            .value(Double.parseDouble(value))
                            .build();
                }
            } else {
                return new DataValueStringBuilder()
                        .value(value)
                        .build();
            }
        }
    }

}
