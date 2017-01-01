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

package com.abixen.platform.service.businessintelligence.chart.form;

import com.abixen.platform.core.form.Form;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.service.businessintelligence.chart.model.impl.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.chart.model.web.DataSourceColumnWeb;
import com.abixen.platform.service.businessintelligence.chart.model.web.DatabaseConnectionWeb;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;
import java.util.HashSet;


public class DatabaseDataSourceForm extends DataSourceForm implements Form {

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private DatabaseConnectionWeb databaseConnection;

    @JsonView(WebModelJsonSerialize.class)
    private String filter;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private String table;

    public DatabaseDataSourceForm() {
    }

    public DatabaseDataSourceForm(DatabaseDataSource databaseDataSource) {
        this.setId(databaseDataSource.getId());
        this.setName(databaseDataSource.getName());
        this.setDescription(databaseDataSource.getDescription());
        this.databaseConnection = databaseDataSource.getDatabaseConnection();
        this.filter = databaseDataSource.getFilter();
        this.table = databaseDataSource.getTable();
        this.setColumns(new HashSet<>());

        for (DataSourceColumnWeb dataSourceColumn : databaseDataSource.getColumns()) {
            this.getColumns().add(dataSourceColumn);
        }
    }

    public DatabaseConnectionWeb getDatabaseConnection() {
        return databaseConnection;
    }

    public void setDatabaseConnection(DatabaseConnectionWeb databaseConnection) {
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
