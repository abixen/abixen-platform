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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model;

import com.abixen.platform.common.domain.model.EntityBuilder;
import com.abixen.platform.common.infrastructure.util.ModelKeys;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceColumn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "data_set_series_column")
@SequenceGenerator(sequenceName = "data_set_series_column_seq", name = "data_set_series_column_seq", allocationSize = 1)
public final class DataSetSeriesColumn implements Serializable {

    private static final long serialVersionUID = 5511866355918336820L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "data_set_series_column_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "type")
    private ColumnType type;

    //TODO check if needed
    @Column(name = "name", length = ModelKeys.NAME_MAX_LENGTH, nullable = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_source_column_id", nullable = false)
    private DataSourceColumn dataSourceColumn;

    private DataSetSeriesColumn() {
    }

    public Long getId() {
        return id;
    }

    private void setId(final Long id) {
        this.id = id;
    }

    public ColumnType getType() {
        return type;
    }

    private void setType(final ColumnType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    private void setName(final String name) {
        this.name = name;
    }

    public DataSourceColumn getDataSourceColumn() {
        return dataSourceColumn;
    }

    private void setDataSourceColumn(final DataSourceColumn dataSourceColumn) {
        this.dataSourceColumn = dataSourceColumn;
    }

    public void changeName(final String name) {
        setName(name);
    }

    public void changeColumn(final ColumnType columnType, final DataSourceColumn dataSourceColumn) {
        setType(columnType);
        setDataSourceColumn(dataSourceColumn);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<DataSetSeriesColumn> {

        private Builder() {
        }

        @Override
        public DataSetSeriesColumn build() {
            return super.build();
        }

        @Override
        protected void initProduct() {
            this.product = new DataSetSeriesColumn();
        }

        @Override
        protected DataSetSeriesColumn assembleProduct() {
            return super.assembleProduct();
        }

        public Builder name(final String name) {
            this.product.setName(name);
            return this;
        }

        public Builder column(final ColumnType columnType, final DataSourceColumn dataSourceColumn) {
            this.product.setType(columnType);
            this.product.setDataSourceColumn(dataSourceColumn);
            return this;
        }
    }

}