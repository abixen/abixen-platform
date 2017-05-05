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

import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.form.DatabaseConnectionForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.database.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataSourceColumnWeb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface DatabaseConnectionService {

    Page<DatabaseConnection> findAllDatabaseConnections(Pageable pageable);

    Page<DatabaseConnectionDto> findAllDatabaseConnectionsAsDto(Pageable pageable);

    DatabaseConnection findDatabaseConnection(Long id);

    DatabaseConnectionDto findDatabaseConnectionAsDto(Long id);

    void deleteDatabaseConnection(Long id);

    DatabaseConnection buildDatabaseConnection(DatabaseConnectionForm databaseConnectionForm);

    DatabaseConnectionForm createDatabaseConnection(DatabaseConnectionForm databaseConnectionForm);

    DatabaseConnectionForm updateDatabaseConnection(DatabaseConnectionForm databaseConnectionForm);

    DatabaseConnection createDatabaseConnection(DatabaseConnection databaseConnection);

    DatabaseConnection updateDatabaseConnection(DatabaseConnection databaseConnection);

    void testDatabaseConnection(DatabaseConnectionForm databaseConnectionForm);

    List<String> getTables(Long databaseConnectionId);

    List<DataSourceColumnWeb> getTableColumns(Long databaseConnectionId, String table);
}
