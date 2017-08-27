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

package com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.web.facade.impl;

import com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.web.facade.converter.DataFileColumnToDataFileColumnDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.web.facade.converter.DataFileToDataFileDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.web.facade.converter.DataSourceColumnToDataSourceColumnDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.web.facade.DataFileFacade;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DataFileForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFile;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFileColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DataFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class DataFileFacadeImpl implements DataFileFacade {

    private final DataFileService dataFileService;
    private final DataFileToDataFileDtoConverter dataFileToDataFileDtoConverter;
    private final DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter;
    private final DataFileColumnToDataFileColumnDtoConverter dataFileColumnToDataFileColumnDtoConverter;

    @Autowired
    public DataFileFacadeImpl(DataFileService dataFileService,
                              DataFileToDataFileDtoConverter dataFileToDataFileDtoConverter,
                              DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter,
                              DataFileColumnToDataFileColumnDtoConverter dataFileColumnToDataFileColumnDtoConverter) {
        this.dataFileService = dataFileService;
        this.dataFileToDataFileDtoConverter = dataFileToDataFileDtoConverter;
        this.dataSourceColumnToDataSourceColumnDtoConverter = dataSourceColumnToDataSourceColumnDtoConverter;
        this.dataFileColumnToDataFileColumnDtoConverter = dataFileColumnToDataFileColumnDtoConverter;
    }

    @Override
    public Page<DataFileDto> findAllDataFile(Pageable pageable) {
        Page<DataFile> dataFiles = dataFileService.findAll(pageable);
        Page<DataFileDto> dataFileDtos = dataFileToDataFileDtoConverter.convertToPage(dataFiles);
        return dataFileDtos;
    }

    @Override
    public DataFileDto findDataFile(Long id) {
        DataFile dataFile = dataFileService.find(id);
        DataFileDto dataFileDto = dataFileToDataFileDtoConverter.convert(dataFile);
        return dataFileDto;
    }

    @Override
    public List<DataSourceColumnDto> getDataFileColumns(Long dataFileId) {
        List<DataSourceColumn> dataFileColumns = dataFileService.getDataFileColumns(dataFileId);
        List<DataSourceColumnDto> dataSourceColumnDtos = dataSourceColumnToDataSourceColumnDtoConverter.convertToList(dataFileColumns);
        return dataSourceColumnDtos;
    }

    @Override
    public DataFileDto createDataFile(DataFileForm dataFileForm) {
        DataFile dataFile = dataFileService.create(dataFileForm);
        DataFileDto dataFileDto = dataFileToDataFileDtoConverter.convert(dataFile);
        return dataFileDto;
    }

    @Override
    public DataFileDto updateDataFile(DataFileForm dataFileForm) {
        DataFile dataFile = dataFileService.update(dataFileForm);
        DataFileDto dataFileDto = dataFileToDataFileDtoConverter.convert(dataFile);
        return dataFileDto;
    }

    @Override
    public void deleteDataFile(Long id) {
        dataFileService.delete(id);
    }

    @Override
    public FileParserMessage<DataFileColumnDto> uploadAndParseFile(MultipartFile fileToParse, Boolean readFirstColumnAsColumnName) {
        FileParserMessage<DataFileColumn> dataFileColumnFileParserMessage = dataFileService.parse(fileToParse, readFirstColumnAsColumnName);
        FileParserMessage<DataFileColumnDto> dataFileColumnDtoFileParserMessage = new FileParserMessage<>();
        dataFileColumnDtoFileParserMessage.setFileParseErrors(dataFileColumnFileParserMessage.getFileParseErrors());
        dataFileColumnDtoFileParserMessage.setData(dataFileColumnToDataFileColumnDtoConverter.convertToList(dataFileColumnFileParserMessage.getData()));
        return dataFileColumnDtoFileParserMessage;
    }
}
