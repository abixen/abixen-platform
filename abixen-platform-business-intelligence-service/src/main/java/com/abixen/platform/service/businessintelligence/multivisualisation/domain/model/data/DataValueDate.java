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
import java.util.Date;


@Entity
@Table(name = "data_value_date")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public final class DataValueDate extends DataValue<Date> {

    private static final long serialVersionUID = -655187403228089633L;

    @Column(name = "value", nullable = true)
    private Date value;

    private DataValueDate() {
    }

    public Date getValue() {
        return value;
    }

    void setValue(final Date value) {
        this.value = value;
    }

    public void changeValue(Date value) {
        setValue(value);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<DataValueDate> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new DataValueDate();
        }

        public Builder position(final Integer position) {
            this.product.setPosition(position);
            return this;
        }

        public Builder dataColumn(final DataColumn dataColumn) {
            this.product.setDataColumn(dataColumn);
            return this;
        }

        public Builder value(final Date value) {
            this.product.setValue(value);
            return this;
        }
    }

}