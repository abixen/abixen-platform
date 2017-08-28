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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.database;

import com.abixen.platform.common.util.EntityBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DatabaseType;

public class DatabaseConnectionBuilder extends EntityBuilder<DatabaseConnection> {

    public DatabaseConnectionBuilder credentials(String username, String password) {
        this.product.setUsername(username);
        this.product.setPassword(password);
        return this;
    }

    public DatabaseConnectionBuilder details(String name, String description) {
        this.product.setName(name);
        this.product.setDescription(description);
        return this;
    }

    public DatabaseConnectionBuilder database(DatabaseType databaseType,
                                              String databaseHost,
                                              Integer databasePort,
                                              String databaseName) {
        this.product.setDatabaseType(databaseType);
        this.product.setDatabaseHost(databaseHost);
        this.product.setDatabasePort(databasePort);
        this.product.setDatabaseName(databaseName);
        return this;
    }

    @Override
    public DatabaseConnection build() {
        return super.build();
    }

    @Override
    protected void initProduct() {
        this.product = new DatabaseConnection();
    }

    @Override
    protected DatabaseConnection assembleProduct() {
        return super.assembleProduct();
    }
}
