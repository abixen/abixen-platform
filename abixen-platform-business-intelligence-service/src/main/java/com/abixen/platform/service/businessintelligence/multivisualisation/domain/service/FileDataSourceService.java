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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.service;

import com.abixen.platform.common.infrastructure.annotation.PlatformDomainService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.file.FileDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFile;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.FileDataSourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@PlatformDomainService
public class FileDataSourceService {

    private final FileDataSourceRepository fileDataSourceRepository;

    @Autowired
    public FileDataSourceService(FileDataSourceRepository fileDataSourceRepository) {
        this.fileDataSourceRepository = fileDataSourceRepository;
    }


    public List<FileDataSource> find(DataFile dataFile) {
        log.debug("find() - dataFile: {}", dataFile);

        return fileDataSourceRepository.findByDataFile(dataFile);
    }

}