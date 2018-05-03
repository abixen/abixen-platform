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
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.FileDataSourceRowDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.file.FileDataSourceRow;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FileDataSourceRowToFileDataSourceRowDtoConverter extends AbstractConverter<FileDataSourceRow, FileDataSourceRowDto> {

    @Override
    public FileDataSourceRowDto convert(FileDataSourceRow fileDataSourceRow, Map<String, Object> parameters) {
        if (fileDataSourceRow == null) {
            return null;
        }

        FileDataSourceRowDto fileDataSourceRowDto = new FileDataSourceRowDto();

        fileDataSourceRowDto.setId(fileDataSourceRow.getId())
                .setRowNumber(fileDataSourceRow.getRowNumber());

        return fileDataSourceRowDto;
    }
}
