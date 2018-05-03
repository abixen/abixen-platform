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
import com.abixen.platform.common.infrastructure.exception.PlatformRuntimeException;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.file.FileDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFile;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@PlatformDomainService
public class DataFileService {

    private final DataFileRepository dataFileRepository;
    private final FileDataSourceService fileDataSourceService;

    @Autowired
    public DataFileService(FileDataSourceService fileDataSourceService,
                           DataFileRepository dataFileRepository) {
        this.fileDataSourceService = fileDataSourceService;
        this.dataFileRepository = dataFileRepository;
    }

    public DataFile find(final Long id) {
        log.debug("find() - id: {}", id);

        return dataFileRepository.findOne(id);
    }

    public Page<DataFile> findAll(final Pageable pageable) {
        log.debug("find() - pageable: {}", pageable);

        return dataFileRepository.findAll(pageable);
    }

    public DataFile create(final DataFile dataFile) {
        log.debug("create() - dataFile: {}", dataFile);

        return dataFileRepository.save(dataFile);
    }

    public DataFile update(final DataFile dataFile) {
        log.debug("update() - dataFile: {}", dataFile);

        return dataFileRepository.save(dataFile);
    }

    public void delete(final Long id) {
        log.debug("delete() - id: {}", id);

        final List<FileDataSource> relatedFileDataSources = fileDataSourceService.find(dataFileRepository.findOne(id));
        if (relatedFileDataSources.isEmpty()) {
            dataFileRepository.delete(id);
        } else {
            throw new PlatformRuntimeException("You need to remove all data sources related to this data file");
        }
    }

}