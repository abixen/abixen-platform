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

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataSourceType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataSourceRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class DataSourceServiceImpl implements DataSourceService {

    private final DataSourceRepository dataSourceRepository;

    @Autowired
    public DataSourceServiceImpl(final DataSourceRepository dataSourceRepository) {
        this.dataSourceRepository = dataSourceRepository;
    }


    @Override
    public DataSource find(final Long id) {
        log.debug("DataSourceService - find() - id: {}", id);

        return dataSourceRepository.findOne(id);
    }

    @Override
    public Page<DataSource> findAll(final Pageable pageable, final DataSourceType dataSourceType) {
        log.debug("DataSourceService - findAll() - pageable: {}, dataSourceType", pageable, dataSourceType);

        if (dataSourceType != null) {
            return dataSourceRepository.findByDataSourceType(dataSourceType, pageable);
        } else {
            return dataSourceRepository.findAll(pageable);
        }
    }

    @Override
    public DataSource create(final DataSource dataSource) {
        log.debug("DataSourceService - create() - dataSource {}", dataSource);

        return dataSourceRepository.save(dataSource);
    }

    @Override
    public DataSource update(final DataSource dataSource) {
        log.debug("DataSourceService - update() - dataSource {}", dataSource);

        return dataSourceRepository.save(dataSource);
    }

    @Override
    public void delete(final Long id) {
        log.debug("DataSourceService - delete() - id {}", id);

        dataSourceRepository.delete(id);
    }

}
