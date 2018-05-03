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
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataFileToDataFileDtoConverter extends AbstractConverter<DataFile, DataFileDto> {

    private final DataFileColumnToDataFileColumnDtoConverter dataFileColumnToDataFileColumnDtoConverter;
    private final AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter;

    @Autowired
    public DataFileToDataFileDtoConverter(DataFileColumnToDataFileColumnDtoConverter dataFileColumnToDataFileColumnDtoConverter,
                                          AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter) {
        this.dataFileColumnToDataFileColumnDtoConverter = dataFileColumnToDataFileColumnDtoConverter;
        this.auditingModelToSimpleAuditingDtoConverter = auditingModelToSimpleAuditingDtoConverter;
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
                .setColumns(dataFileColumnToDataFileColumnDtoConverter.convertToSet(dataFile.getColumns()));

        auditingModelToSimpleAuditingDtoConverter.convert(dataFile, dataFileDto);

        return dataFileDto;
    }

}