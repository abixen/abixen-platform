/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.database;

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DatabaseConnectionRepository;

public class DatabaseDataSourceBuilder extends DataSourceBuilder {

    public DatabaseDataSourceBuilder databaseConnection(Long databaseConnectionId, DatabaseConnectionRepository repository) {
        ((DatabaseDataSource) this.product).setDatabaseConnection(repository.findOne(databaseConnectionId));
        return this;
    }

    public DatabaseDataSourceBuilder filter(String filter) {
        ((DatabaseDataSource) this.product).setFilter(filter);
        return this;
    }

    public DatabaseDataSourceBuilder table(String table) {
        ((DatabaseDataSource) this.product).setTable(table);
        return this;
    }

    @Override
    public DatabaseDataSource build() {
        return (DatabaseDataSource) super.build();
    }

    @Override
    public void initProduct() {
        this.product = new DatabaseDataSource();
    }

    @Override
    public DatabaseDataSource assembleProduct() {
        return (DatabaseDataSource) super.assembleProduct();
    }
}
