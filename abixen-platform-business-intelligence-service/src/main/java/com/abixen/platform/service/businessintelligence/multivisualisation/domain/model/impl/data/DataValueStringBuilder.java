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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.data;

import com.abixen.platform.common.util.EntityBuilder;

public class DataValueStringBuilder extends EntityBuilder<DataValueString> {

    public DataValueStringBuilder position(final Integer position) {
        this.product.setPosition(position);
        return this;
    }

    public DataValueStringBuilder dataColumn(final DataColumn dataColumn) {
        this.product.setDataColumn(dataColumn);
        return this;
    }

    public DataValueStringBuilder value(final String value) {
        this.product.setValue(value);
        return this;
    }

    @Override
    public DataValueString build() {
        return super.build();
    }

    @Override
    protected void initProduct() {
        this.product = new DataValueString();
    }

    @Override
    protected DataValueString assembleProduct() {
        return super.assembleProduct();
    }
}
