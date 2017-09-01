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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.impl;

import com.abixen.platform.common.exception.PlatformRuntimeException;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.file.FileDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFile;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataFileRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.FileDataSourceRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.DataFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DataFileServiceImpl implements DataFileService {

    private final DataFileRepository dataFileRepository;
    private final FileDataSourceRepository fileDataSourceRepository;

    @Autowired
    public DataFileServiceImpl(FileDataSourceRepository fileDataSourceRepository,
                               DataFileRepository dataFileRepository) {
        this.fileDataSourceRepository = fileDataSourceRepository;
        this.dataFileRepository = dataFileRepository;
    }

    @Override
    public DataFile find(final Long id) {
        log.debug("DataFileService - find() - id: {}", id);

        return dataFileRepository.findOne(id);
    }

    @Override
    public Page<DataFile> find(final String jsonCriteria, final Pageable pageable) {
        log.debug("DataFileService - find() - jsonCriteria: {}, pageable: {}", jsonCriteria, pageable);

        return dataFileRepository.findAll(pageable);
    }

    @Override
    public Page<DataFile> findAll(final Pageable pageable) {
        log.debug("DataFileService - find() - pageable: {}",  pageable);

        return dataFileRepository.findAll(pageable);
    }


    @Override
    public DataFile create(final DataFile dataFile) {
        log.debug("DataFileService - create() - dataFile: {}",  dataFile);

        return dataFileRepository.save(dataFile);
    }

    @Override
    public DataFile update(final DataFile dataFile) {
        log.debug("DataFileService - update() - dataFile: {}", dataFile);

        return dataFileRepository.save(dataFile);
    }

    @Override
    public void delete(final Long id) {
        log.debug("DataFileService - delete() - id: {}", id);

        final List<FileDataSource> relatedFileDataSources = fileDataSourceRepository.findByDataFile(dataFileRepository.getOne(id));
        if (relatedFileDataSources.isEmpty()) {
            dataFileRepository.delete(id);
        } else {
            throw new PlatformRuntimeException("You need to remove all data sources related to this data file");
        }
    }

}
