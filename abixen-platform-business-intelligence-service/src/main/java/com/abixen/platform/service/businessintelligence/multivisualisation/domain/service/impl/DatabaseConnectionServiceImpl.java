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

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.database.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DatabaseConnectionRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.DatabaseConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class DatabaseConnectionServiceImpl implements DatabaseConnectionService {

    private final DatabaseConnectionRepository databaseConnectionRepository;

    @Autowired
    public DatabaseConnectionServiceImpl(DatabaseConnectionRepository databaseConnectionRepository) {
        this.databaseConnectionRepository = databaseConnectionRepository;
    }

    @Override
    public DatabaseConnection find(final Long id) {
        log.debug("find() - id: {}", id);

        return databaseConnectionRepository.getOne(id);
    }

    @Override
    public Page<DatabaseConnection> findAll(final Pageable pageable) {
        log.debug("findAll() - pageable: {}", pageable);

        return databaseConnectionRepository.findAll(pageable);
    }

    @Override
    public DatabaseConnection create(final DatabaseConnection databaseConnection) {
        log.debug("create() - databaseConnection: {}", databaseConnection);

        return databaseConnectionRepository.save(databaseConnection);
    }

    @Override
    public DatabaseConnection update(final DatabaseConnection databaseConnection) {
        log.debug("update() - databaseConnection: {}", databaseConnection);

        return databaseConnectionRepository.save(databaseConnection);
    }

    @Override
    public void delete(final Long id) {
        log.debug("delete() - id: {}", id);

        databaseConnectionRepository.delete(id);
    }

}
