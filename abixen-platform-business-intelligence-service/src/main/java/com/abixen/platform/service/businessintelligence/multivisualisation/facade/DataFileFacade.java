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

package com.abixen.platform.service.businessintelligence.multivisualisation.facade;

import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataFileColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataSourceColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.form.DataFileForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.message.FileParserMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DataFileFacade {

    Page<DataFileDto> findAllDataFile(Pageable pageable);

    DataFileDto findDataFile(Long id);

    List<DataSourceColumnDto> getDataFileColumns(Long dataFileId);

    DataFileDto createDataFile(DataFileForm dataFileForm);

    DataFileDto updateDataFile(DataFileForm dataFileForm);

    void deleteDataFile(Long id);

    FileParserMessage<DataFileColumnDto> uploadAndParseFile(MultipartFile fileToParse, Boolean readFirstColumnAsColumnName);

}
