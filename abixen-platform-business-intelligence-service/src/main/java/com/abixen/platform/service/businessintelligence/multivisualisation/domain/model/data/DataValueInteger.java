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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data;

import com.abixen.platform.common.domain.model.EntityBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "data_value_integer")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public final class DataValueInteger extends DataValue<Integer> {


    private static final long serialVersionUID = -4059158260855268805L;

    @Column(name = "value", nullable = true)
    private Integer value;

    private DataValueInteger() {
    }

    public Integer getValue() {
        return value;
    }

    void setValue(final Integer value) {
        this.value = value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<DataValueInteger> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new DataValueInteger();
        }

        public Builder position(final Integer position) {
            this.product.setPosition(position);
            return this;
        }

        public Builder dataColumn(final DataColumn dataColumn) {
            this.product.setDataColumn(dataColumn);
            return this;
        }

        public Builder value(final Integer value) {
            this.product.setValue(value);
            return this;
        }
    }

}