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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DatabaseConnectionForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.database.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface DatabaseConnectionService {

    Page<DatabaseConnection> findAllDatabaseConnections(Pageable pageable);

    DatabaseConnection findDatabaseConnection(Long id);

    void deleteDatabaseConnection(Long id);

    DatabaseConnection buildDatabaseConnection(DatabaseConnectionForm databaseConnectionForm);

    DatabaseConnection createDatabaseConnection(DatabaseConnectionForm databaseConnectionForm);

    DatabaseConnection updateDatabaseConnection(DatabaseConnectionForm databaseConnectionForm);

    DatabaseConnection createDatabaseConnection(DatabaseConnection databaseConnection);

    DatabaseConnection updateDatabaseConnection(DatabaseConnection databaseConnection);

    void testDatabaseConnection(DatabaseConnectionForm databaseConnectionForm);

    List<String> getTables(Long databaseConnectionId);

    List<DataSourceColumn> getTableColumns(Long databaseConnectionId, String table);
}
