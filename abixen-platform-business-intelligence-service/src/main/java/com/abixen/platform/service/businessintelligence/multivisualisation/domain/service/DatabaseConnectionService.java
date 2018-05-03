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
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.connection.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DatabaseConnectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;


@Slf4j
@Transactional
@PlatformDomainService
public class DatabaseConnectionService {

    private final DatabaseConnectionRepository databaseConnectionRepository;

    @Autowired
    public DatabaseConnectionService(DatabaseConnectionRepository databaseConnectionRepository) {
        this.databaseConnectionRepository = databaseConnectionRepository;
    }

    public DatabaseConnection find(final Long id) {
        log.debug("find() - id: {}", id);

        return databaseConnectionRepository.findOne(id);
    }

    public Page<DatabaseConnection> findAll(final Pageable pageable) {
        log.debug("findAll() - pageable: {}", pageable);

        return databaseConnectionRepository.findAll(pageable);
    }

    public DatabaseConnection create(final DatabaseConnection databaseConnection) {
        log.debug("create() - databaseConnection: {}", databaseConnection);

        return databaseConnectionRepository.save(databaseConnection);
    }

    public DatabaseConnection update(final DatabaseConnection databaseConnection) {
        log.debug("update() - databaseConnection: {}", databaseConnection);

        return databaseConnectionRepository.save(databaseConnection);
    }

    public void delete(final Long id) {
        log.debug("delete() - id: {}", id);

        databaseConnectionRepository.delete(id);
    }

}