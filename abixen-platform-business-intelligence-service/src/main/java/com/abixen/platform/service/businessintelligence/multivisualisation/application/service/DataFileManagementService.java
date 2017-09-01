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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service;

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DataFileForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.message.FileParserMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface DataFileManagementService {

    DataFileDto findDataFile(final Long id);

    Page<DataFileDto> findDataFile(final String jsonCriteria, final Pageable pageable);

    Page<DataFileDto> findAllDataFile(final Pageable pageable);

    DataFileDto createDataFile(final DataFileForm dataFileForm);

    DataFileDto updateDataFile(final DataFileForm dataFileForm);

    void deleteDataFile(final Long id);

    List<DataSourceColumnDto> findDataFileColumns(final Long dataFileId);

    List<Map<String, Integer>> findAllColumns(final Long dataFileId);

    FileParserMessage<DataFileColumnDto> parse(final MultipartFile fileToParse, final Boolean readFirstColumnAsColumnName);
}
