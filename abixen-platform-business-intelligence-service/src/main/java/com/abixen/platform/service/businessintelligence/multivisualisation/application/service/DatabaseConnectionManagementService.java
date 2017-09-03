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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DatabaseConnectionForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface DatabaseConnectionManagementService {

    DatabaseConnectionDto findDatabaseConnection(final Long id);

    Page<DatabaseConnectionDto> findAllDatabaseConnections(final Pageable pageable);

    DatabaseConnectionDto createDatabaseConnection(final DatabaseConnectionForm databaseConnectionForm);

    DatabaseConnectionDto updateDatabaseConnection(final DatabaseConnectionForm databaseConnectionForm);

    void deleteDatabaseConnection(final Long id);

    void testDatabaseConnection(final DatabaseConnectionForm databaseConnectionForm);

    List<String> findTablesInDatabase(final Long databaseConnectionId);

    List<DataSourceColumnDto> findTableColumnsFromDatabase(final Long databaseConnectionId, final String table);
}
