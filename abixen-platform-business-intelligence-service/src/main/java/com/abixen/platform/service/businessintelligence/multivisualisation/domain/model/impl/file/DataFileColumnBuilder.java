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

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataValueType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.data.DataColumnBuilder;

public class DataFileColumnBuilder extends DataColumnBuilder {

    public DataFileColumnBuilder dataFile(final DataFile dataFile) {
        ((DataFileColumn) this.product).setDataFile(dataFile);
        return this;
    }

    public DataFileColumnBuilder dataValueType(final DataValueType dataValueType) {
        ((DataFileColumn) this.product).setDataValueType(dataValueType);
        return this;
    }

    @Override
    public DataFileColumn build() {
        return (DataFileColumn) super.build();
    }

    @Override
    protected void initProduct() {
        this.product = new DataFileColumn();
    }

    @Override
    protected DataFileColumn assembleProduct() {
        return (DataFileColumn) super.assembleProduct();
    }
}
