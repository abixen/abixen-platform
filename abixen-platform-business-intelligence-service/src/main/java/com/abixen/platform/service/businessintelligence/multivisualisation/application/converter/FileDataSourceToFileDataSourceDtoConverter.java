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
import com.abixen.platform.common.application.converter.AuditingModelToSimpleAuditingDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.FileDataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.file.FileDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FileDataSourceToFileDataSourceDtoConverter extends AbstractConverter<FileDataSource, FileDataSourceDto> {

    private final AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter;
    private final FileDataSourceRowToFileDataSourceRowDtoConverter fileDataSourceRowToFileDataSourceRowDtoConverter;
    private final DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter;
    private final DataFileToDataFileDtoConverter dataFileToDataFileDtoConverter;

    @Autowired
    public FileDataSourceToFileDataSourceDtoConverter(AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter,
                                                      FileDataSourceRowToFileDataSourceRowDtoConverter fileDataSourceRowToFileDataSourceRowDtoConverter,
                                                      DataSourceColumnToDataSourceColumnDtoConverter dataSourceColumnToDataSourceColumnDtoConverter,
                                                      DataFileToDataFileDtoConverter dataFileToDataFileDtoConverter) {
        this.auditingModelToSimpleAuditingDtoConverter = auditingModelToSimpleAuditingDtoConverter;
        this.fileDataSourceRowToFileDataSourceRowDtoConverter = fileDataSourceRowToFileDataSourceRowDtoConverter;
        this.dataSourceColumnToDataSourceColumnDtoConverter = dataSourceColumnToDataSourceColumnDtoConverter;
        this.dataFileToDataFileDtoConverter = dataFileToDataFileDtoConverter;
    }

    @Override
    public FileDataSourceDto convert(FileDataSource fileDataSource, Map<String, Object> parameters) {
        if (fileDataSource == null) {
            return null;
        }

        final FileDataSourceDto fileDataSourceDto = (FileDataSourceDto) new FileDataSourceDto()
                .setRows(fileDataSourceRowToFileDataSourceRowDtoConverter.convertToSet(fileDataSource.getRows()))
                .setDataFile(dataFileToDataFileDtoConverter.convert(fileDataSource.getDataFile()))
                .setId(fileDataSource.getId())
                .setName(fileDataSource.getName())
                .setFilter(fileDataSource.getFilter())
                .setDescription(fileDataSource.getDescription())
                .setDataSourceType(fileDataSource.getDataSourceType())
                .setColumns(dataSourceColumnToDataSourceColumnDtoConverter.convertToSet(fileDataSource.getColumns()));

        auditingModelToSimpleAuditingDtoConverter.convert(fileDataSource, fileDataSourceDto);

        return fileDataSourceDto;
    }
}
