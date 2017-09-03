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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.converter.DataFileColumnToDataFileColumnDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.converter.DataSourceColumnToDataSourceColumnDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.FileDataParserService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.data.*;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumnBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFileBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFileColumnBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.converter.DataFileToDataFileDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DataFileForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFile;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFileColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DataFileManagementService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.DataFileService;
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
public class DataFileManagementServiceImpl implements DataFileManagementService {

    private final DataFileService dataFileService;
    private final FileParserFactory fileParserFactory;
    private final DataFileToDataFileDtoConverter dataFileToDataFileDtoConverter;
    private final DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter;
    private final DataFileColumnToDataFileColumnDtoConverter dataFileColumnToDataFileColumnDtoConverter;

    @Autowired
    public DataFileManagementServiceImpl(DataFileService dataFileService,
                                         FileParserFactory fileParserFactory,
                                         DataFileToDataFileDtoConverter dataFileToDataFileDtoConverter,
                                         DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter,
                                         DataFileColumnToDataFileColumnDtoConverter dataFileColumnToDataFileColumnDtoConverter) {
        this.dataFileService = dataFileService;
        this.fileParserFactory = fileParserFactory;
        this.dataFileToDataFileDtoConverter = dataFileToDataFileDtoConverter;
        this.dataSourceColumnToDataSourceColumnDtoConverter = dataSourceColumnToDataSourceColumnDtoConverter;
        this.dataFileColumnToDataFileColumnDtoConverter = dataFileColumnToDataFileColumnDtoConverter;
    }

    @Override
    public DataFileDto findDataFile(final Long id) {
        log.debug("findDataFile() - id: {}", id);

        final DataFile dataFile = dataFileService.find(id);

        return dataFileToDataFileDtoConverter.convert(dataFile);
    }

    @Override
    public Page<DataFileDto> findDataFile(final String jsonCriteria, final Pageable pageable) {
        log.debug("findDataFile() - jsonCriteria: {}, pageable: {}", jsonCriteria, pageable);

        final Page<DataFile> dataFiles = dataFileService.findAll(pageable);

        return dataFileToDataFileDtoConverter.convertToPage(dataFiles);
    }

    @Override
    public Page<DataFileDto> findAllDataFiles(final Pageable pageable) {
        log.debug("findAllDataFiles() - pageable: {}", pageable);

        final Page<DataFile> dataFiles = dataFileService.findAll(pageable);

        return dataFileToDataFileDtoConverter.convertToPage(dataFiles);
    }

    @Override
    public DataFileDto createDataFile(final DataFileForm dataFileForm) {
        log.debug("createDataFile() - dataFileForm: {}", dataFileForm);

        final DataFile dataFile = dataFileService.create(build(dataFileForm));

        return dataFileToDataFileDtoConverter.convert(dataFile);
    }

    @Override
    public DataFileDto updateDataFile(final DataFileForm dataFileForm) {
        log.debug("updateDataFile() - dataFileForm: {}", dataFileForm);

        final DataFile dataFile = dataFileService.find(dataFileForm.getId());
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

        final DataFile updateDataFile = dataFileService.update(dataFile);

        return dataFileToDataFileDtoConverter.convert(updateDataFile);
    }

    @Override
    public void deleteDataFile(final Long id) {
        log.debug("deleteDataFile() - id: {}", id);

        dataFileService.delete(id);
    }

    @Override
    public List<DataSourceColumnDto> findAllDataFileColumns(final Long dataFileId) {
        log.debug("findAllDataFileColumns() - dataFileId: {}", dataFileId);

        final List<DataSourceColumn> result = new ArrayList<>();
        final DataFile dataFile = dataFileService.find(dataFileId);
        for (DataFileColumn dataFileColumn : dataFile.getColumns()) {
            result.add(new DataSourceColumnBuilder()
                    .details(dataFileColumn.getName())
                    .paramters(dataFileColumn.getDataValueType(), dataFileColumn.getPosition())
                    .build());
        }

        return dataSourceColumnToDataSourceColumnDtoConverter.convertToList(result);
    }

    @Override
    public List<Map<String, Integer>> findAllColumns(final Long dataFileId) {
        log.debug("findAllColumns() - dataFileId: {}", dataFileId);

        final List<Map<String, Integer>> result = new ArrayList<>();
        final DataFile dataFile = dataFileService.find(dataFileId);
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
    public FileParserMessage<DataFileColumnDto> parse(final MultipartFile multipartFile, final Boolean readFirstColumnAsColumnName) {
        log.debug("parse() - multipartFile: {}, readFirstColumnAsColumnName: {}", multipartFile, readFirstColumnAsColumnName);

        final String fileName = multipartFile.getOriginalFilename();
        final FileDataParserService fileDataParserService = fileParserFactory.getParse(fileName.substring(fileName.lastIndexOf(".")));
        final FileParserMessage<DataFileColumn> result = fileDataParserService.parse(multipartFile, readFirstColumnAsColumnName);

        return new FileParserMessage(dataFileColumnToDataFileColumnDtoConverter.convertToList(result.getData()), result.getFileParseErrors());
    }

    public DataFile build(final DataFileForm dataFileForm) {
        final DataFile dataFile = new DataFileBuilder()
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
