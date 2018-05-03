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
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataSourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;


@Slf4j
@Transactional
@PlatformDomainService
public class DataSourceService {

    private final DataSourceRepository dataSourceRepository;

    @Autowired
    public DataSourceService(final DataSourceRepository dataSourceRepository) {
        this.dataSourceRepository = dataSourceRepository;
    }


    public DataSource find(final Long id) {
        log.debug("find() - id: {}", id);

        return dataSourceRepository.findOne(id);
    }

    public Page<DataSource> findAll(final Pageable pageable, final DataSourceType dataSourceType) {
        log.debug("findAll() - pageable: {}, dataSourceType", pageable, dataSourceType);

        if (dataSourceType == null) {
            return dataSourceRepository.findAll(pageable);
        }

        return dataSourceRepository.findByDataSourceType(dataSourceType, pageable);
    }

    public DataSource create(final DataSource dataSource) {
        log.debug("create() - dataSource {}", dataSource);

        return dataSourceRepository.save(dataSource);
    }

    public DataSource update(final DataSource dataSource) {
        log.debug("update() - dataSource {}", dataSource);

        return dataSourceRepository.save(dataSource);
    }

    public void delete(final Long id) {
        log.debug("delete() - id {}", id);

        dataSourceRepository.delete(id);
    }

}