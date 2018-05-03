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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.database;

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.connection.DatabaseConnection;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name = "database_data_source")
public final class DatabaseDataSource extends DataSource implements Serializable {

    private static final long serialVersionUID = -1420930478759410093L;

    @ManyToOne
    @JoinColumn(name = "database_connection_id", nullable = false)
    @NotNull
    @Valid
    private DatabaseConnection databaseConnection;

    @Column(name = "filter")
    private String filter;

    @Column(name = "selected_table")
    private String table;

    private DatabaseDataSource() {
    }

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    private void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public String getFilter() {
        return filter;
    }

    private void setFilter(final String filter) {
        this.filter = filter;
    }

    public String getTable() {
        return table;
    }

    private void setTable(final String table) {
        this.table = table;
    }

    public void changeDatabaseConnection(DatabaseConnection databaseConnection) {
        setDatabaseConnection(databaseConnection);
    }

    public void changeFilter(final String filter) {
        setFilter(filter);
    }

    public void changeTable(final String table) {
        setTable(table);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends DataSource.Builder {

        private Builder() {
        }

        @Override
        public void initProduct() {
            this.product = new DatabaseDataSource();
        }

        public Builder databaseConnection(final DatabaseConnection databaseConnection) {
            ((DatabaseDataSource) this.product).setDatabaseConnection(databaseConnection);
            return this;
        }

        public Builder filter(final String filter) {
            ((DatabaseDataSource) this.product).setFilter(filter);
            return this;
        }

        public Builder table(final String table) {
            ((DatabaseDataSource) this.product).setTable(table);
            return this;
        }
    }

}