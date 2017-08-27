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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DataFileForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFile;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFileColumn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface DataFileService {

    Page<DataFile> find(String jsonCriteria, Pageable pageable);

    DataFile find(Long id);

    Page<DataFile> findAll(Pageable pageable);

    DataFile create(DataFileForm dataFileForm);

    DataFile create(DataFile dataFile);

    DataFile update(DataFileForm dataFileForm);

    DataFile update(DataFile dataFile);

    void delete(Long id);

    DataFile build(DataFileForm dataFileForm);

    List<DataSourceColumn> getDataFileColumns(Long dataFileId);

    List<Map<String, Integer>> getAllColumns(Long dataFileId);

    FileParserMessage<DataFileColumn> parse(MultipartFile fileToParse, Boolean readFirstColumnAsColumnName);
}
