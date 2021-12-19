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

import com.abixen.platform.common.domain.model.AbstractBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "data_value_double")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public final class DataValueDouble extends DataValue<Double> {


    private static final long serialVersionUID = -1434303122608329824L;

    @Column(name = "value_", nullable = true)
    private Double value;

    private DataValueDouble() {
    }

    public Double getValue() {
        return value;
    }

    void setValue(final Double value) {
        this.value = value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBuilder<DataValueDouble> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new DataValueDouble();
        }

        public Builder position(final Integer position) {
            this.product.setPosition(position);
            return this;
        }

        public Builder dataColumn(final DataColumn dataColumn) {
            this.product.setDataColumn(dataColumn);
            return this;
        }

        public Builder value(final Double value) {
            this.product.setValue(value);
            return this;
        }
    }

}