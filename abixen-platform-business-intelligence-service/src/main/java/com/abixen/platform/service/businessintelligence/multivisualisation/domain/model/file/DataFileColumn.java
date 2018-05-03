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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file;

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataValueType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data.DataColumn;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("FILE")
public final class DataFileColumn extends DataColumn {

    @OneToOne
    @JoinColumn(name = "data_file_id", nullable = false)
    @JsonIgnore
    private DataFile dataFile;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_value_type")
    @NotNull
    private DataValueType dataValueType = DataValueType.STRING;

    private DataFileColumn() {
    }

    public DataFile getDataFile() {
        return dataFile;
    }

    private void setDataFile(final DataFile dataFile) {
        this.dataFile = dataFile;
    }

    public DataValueType getDataValueType() {
        return dataValueType;
    }

    private void setDataValueType(final DataValueType dataValueType) {
        this.dataValueType = dataValueType;
    }

    public void changeDataFile(final DataFile dataFile) {
        setDataFile(dataFile);
    }

    public void changeDataValueType(final DataValueType dataValueType) {
        setDataValueType(dataValueType);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends DataColumn.Builder {

        private Builder() {
            super();
        }

        public Builder dataFile(final DataFile dataFile) {
            ((DataFileColumn) this.product).setDataFile(dataFile);
            return this;
        }

        public Builder dataValueType(final DataValueType dataValueType) {
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

}