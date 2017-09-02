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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file;

import com.abixen.platform.common.domain.model.utils.EntityBuilder;

import java.util.Set;

public class DataFileBuilder extends EntityBuilder<DataFile> {

    public DataFileBuilder details(final String name, final String description) {
        this.product.setName(name);
        this.product.setDescription(description);
        return this;
    }

    public DataFileBuilder columns(final Set<DataFileColumn> columns) {
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
