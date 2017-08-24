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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file;

import com.abixen.platform.common.util.EntityBuilder;

import java.util.Set;

public class DataFileBuilder extends EntityBuilder<DataFile> {

    public DataFileBuilder details(String name, String description) {
        this.product.setName(name);
        this.product.setDescription(description);
        return this;
    }

    public DataFileBuilder columns(Set<DataFileColumn> columns) {
        this.product.setColumns(columns);
        return this;
    }


    @Override
    public DataFile build() {
        return super.build();
    }

    @Override
    protected void initProduct() {
        this.product = new DataFile();
    }

    @Override
    protected DataFile assembleProduct() {
        return super.assembleProduct();
    }
}
