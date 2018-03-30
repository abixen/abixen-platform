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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.data;

import com.abixen.platform.common.domain.model.EntityBuilder;

import java.util.Date;

public class DataValueDateBuilder extends EntityBuilder<DataValueDate> {

    @Override
    public DataValueDate build() {
        return super.build();
    }

    @Override
    protected void initProduct() {
        this.product = new DataValueDate();
    }

    @Override
    protected DataValueDate assembleProduct() {
        return super.assembleProduct();
    }

    public DataValueDateBuilder position(final Integer position) {
        this.product.setPosition(position);
        return this;
    }

    public DataValueDateBuilder dataColumn(final DataColumn dataColumn) {
        this.product.setDataColumn(dataColumn);
        return this;
    }

    public DataValueDateBuilder value(final Date value) {
        this.product.setValue(value);
        return this;
    }
}
