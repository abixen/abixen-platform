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
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "data_value_string")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public final class DataValueString extends DataValue<String> {

    public static final int DATASOURCE_VALUE_STRING_MAX_LENGTH = 32;

    private static final long serialVersionUID = 4578637099421599970L;

    @Length(min = 0, max = DATASOURCE_VALUE_STRING_MAX_LENGTH)
    @Column(name = "value", length = DATASOURCE_VALUE_STRING_MAX_LENGTH)
    private String value;

    private DataValueString() {
    }

    public String getValue() {
        return value;
    }

    @Override
    void setValue(final String value) {
        this.value = value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<DataValueString> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new DataValueString();
        }

        public Builder position(final Integer position) {
            this.product.setPosition(position);
            return this;
        }

        public Builder dataColumn(final DataColumn dataColumn) {
            this.product.setDataColumn(dataColumn);
            return this;
        }

        public Builder value(final String value) {
            this.product.setValue(value);
            return this;
        }
    }

}