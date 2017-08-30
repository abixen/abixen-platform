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

import com.abixen.platform.common.converter.AbstractConverter;
import com.abixen.platform.common.converter.AuditingModelToAuditingDtoConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataFileToDataFileDtoConverter extends AbstractConverter<DataFile, DataFileDto> {

    private final DataFileColumnToDataFileColumnDtoConverter dataFileColumnToDataFileColumnDtoConverter;
    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;

    @Autowired
    public DataFileToDataFileDtoConverter(DataFileColumnToDataFileColumnDtoConverter dataFileColumnToDataFileColumnDtoConverter,
                                          AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter) {
        this.dataFileColumnToDataFileColumnDtoConverter = dataFileColumnToDataFileColumnDtoConverter;
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
    }

    @Override
    public DataFileDto convert(DataFile dataFile, Map<String, Object> parameters) {
        if (dataFile == null) {
            return null;
        }

        DataFileDto dataFileDto = new DataFileDto();

        dataFileDto.setId(dataFile.getId())
                .setName(dataFile.getName())
                .setDescription(dataFile.getDescription())
                .setColumns(dataFileColumnToDataFileColumnDtoConverter.convertToList(dataFile.getColumns()));

        auditingModelToAuditingDtoConverter.convert(dataFile, dataFileDto);

        return dataFileDto;
    }
}
