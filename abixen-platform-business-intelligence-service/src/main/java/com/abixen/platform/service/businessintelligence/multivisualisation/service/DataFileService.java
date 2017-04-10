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

package com.abixen.platform.service.businessintelligence.multivisualisation.service;

import com.abixen.platform.service.businessintelligence.multivisualisation.form.DataFileForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFile;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFileColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataSourceColumnWeb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface DataFileService {

    Page<DataFile> getDataFile(String jsonCriteria, Pageable pageable);

    Page<DataFile> findAllDataFile(Pageable pageable);

    List<DataSourceColumnWeb> getDataFileColumns(Long dataFileId);

    List<Map<String, Integer>> getAllColumns(Long dataFileId);

    DataFile buildDataFile(DataFileForm dataFileForm);

    DataFileForm createDataFile(DataFileForm dataFileForm);

    DataFile createDataFile(DataFile dataFile);

    DataFileForm updateDataFile(DataFileForm dataFileForm);

    DataFile updateDataFile(DataFile dataFile);

    DataFile findDataFile(Long id);

    void delateFileData(Long id);

    FileParserMessage<DataFileColumn> uploadAndParseFile(MultipartFile fileToParse, Boolean readFirstColumnAsColumnName);
}
