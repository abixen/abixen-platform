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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.form;

import com.abixen.platform.common.application.form.Form;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseDataSourceDto;

import javax.validation.constraints.NotNull;


public class DatabaseDataSourceForm extends DataSourceForm implements Form {

    @NotNull
    private DatabaseConnectionDto databaseConnection;

    private String filter;

    @NotNull
    private String table;

    public DatabaseDataSourceForm() {
        super();
    }

    public DatabaseDataSourceForm(DatabaseDataSourceDto databaseDataSource) {
        super(databaseDataSource);
        this.databaseConnection = databaseDataSource.getDatabaseConnection();
        this.filter = databaseDataSource.getFilter();
        this.table = databaseDataSource.getTable();
    }

    public DatabaseConnectionDto getDatabaseConnection() {
        return databaseConnection;
    }

    public void setDatabaseConnection(DatabaseConnectionDto databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

}
