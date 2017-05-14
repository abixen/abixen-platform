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

package com.abixen.platform.service.businessintelligence.multivisualisation.facade;

import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataSourceColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.form.DatabaseConnectionForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DatabaseConnectionFacade {

    Page<DatabaseConnectionDto> findAllDatabaseConnections(Pageable pageable);

    DatabaseConnectionDto findDatabaseConnection(Long id);

    void deleteDatabaseConnection(Long id);

    DatabaseConnectionDto createDatabaseConnection(DatabaseConnectionForm databaseConnectionForm);

    DatabaseConnectionDto updateDatabaseConnection(DatabaseConnectionForm databaseConnectionForm);

    void testDatabaseConnection(DatabaseConnectionForm databaseConnectionForm);

    List<String> getTables(Long databaseConnectionId);

    List<DataSourceColumnDto> getTableColumns(Long databaseConnectionId, String table);

}
