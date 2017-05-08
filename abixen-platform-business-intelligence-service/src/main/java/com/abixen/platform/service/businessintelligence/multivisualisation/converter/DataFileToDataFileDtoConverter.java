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

package com.abixen.platform.service.businessintelligence.multivisualisation.converter;

import com.abixen.platform.common.converter.AbstractConverter;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataFileToDataFileDtoConverter extends AbstractConverter<DataFile, DataFileDto> {

    private final DataFileColumnToDataFileColumnDtoConverter dataFileColumnToDataFileColumnDtoConverter;

    @Autowired
    public DataFileToDataFileDtoConverter(DataFileColumnToDataFileColumnDtoConverter dataFileColumnToDataFileColumnDtoConverter) {
        this.dataFileColumnToDataFileColumnDtoConverter = dataFileColumnToDataFileColumnDtoConverter;
    }

    @Override
    public DataFileDto convert(DataFile dataFile, Map<String, Object> parameters) {
        return new DataFileDto()
                .setId(dataFile.getId())
                .setName(dataFile.getName())
                .setDescription(dataFile.getDescription())
                .setColumns(dataFileColumnToDataFileColumnDtoConverter.convertToList(dataFile.getColumns()));
    }
}
