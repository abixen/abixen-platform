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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.database;

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.database.DatabaseConnection;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;



@Entity
@Table(name = "database_data_source")
public class DatabaseDataSource extends DataSource implements Serializable {

    private static final long serialVersionUID = -1420930478759410093L;

    @ManyToOne
    @JoinColumn(name = "database_connection_id", nullable = false)
    @NotNull
    @Valid
    private DatabaseConnection databaseConnection;

    @Lob
    @Column(name = "filter", nullable = true)
    private String filter;

    @Column(name = "selected_table", nullable = true)
    private String table;

    DatabaseDataSource() {
    }

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public String getFilter() {
        return filter;
    }

    void setFilter(String filter) {
        this.filter = filter;
    }

    public String getTable() {
        return table;
    }

    void setTable(String table) {
        this.table = table;
    }

    public void changeDatabaseConnection(DatabaseConnection databaseConnection) {
        setDatabaseConnection(databaseConnection);
    }

    public void changeFilter(String filter) {
        setFilter(filter);
    }

    public void changeTable(String table) {
        setTable(table);
    }


}