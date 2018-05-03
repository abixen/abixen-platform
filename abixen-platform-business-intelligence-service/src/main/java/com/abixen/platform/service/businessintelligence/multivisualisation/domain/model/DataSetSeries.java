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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "data_set_series")
@SequenceGenerator(sequenceName = "data_set_series_seq", name = "data_set_series_seq", allocationSize = 1)
public final class DataSetSeries implements Serializable {

    private static final long serialVersionUID = 1745322508918878116L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "data_set_series_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = ModelKeys.NAME_MAX_LENGTH, nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "value_column_id", nullable = false)
    private DataSetSeriesColumn valueSeriesColumn;

    @Column(name = "filter", nullable = true)
    private String filter;

    @ManyToOne
    @JoinColumn(name = "data_set_id", nullable = false)
    private DataSet dataSet;

    private DataSetSeries() {
    }

    public Long getId() {
        return id;
    }

    private void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(final String name) {
        this.name = name;
    }

    public DataSetSeriesColumn getValueSeriesColumn() {
        return valueSeriesColumn;
    }

    private void setValueSeriesColumn(final DataSetSeriesColumn valueSeriesColumn) {
        this.valueSeriesColumn = valueSeriesColumn;
    }

    public String getFilter() {
        return filter;
    }

    private void setFilter(final String filter) {
        this.filter = filter;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    private void setDataSet(final DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public void changeName(String name) {
        setName(name);
    }

    public void changeValueSeriesColumn(DataSetSeriesColumn valueSeriesColumn) {
        setValueSeriesColumn(valueSeriesColumn);
    }

    public void changeDataParameters(String filter, DataSet dataSet) {
        setFilter(filter);
        setDataSet(dataSet);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<DataSetSeries> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new DataSetSeries();
        }

        public Builder name(final String name) {
            this.product.setName(name);
            return this;
        }

        public Builder valueSeriesColumn(final DataSetSeriesColumn valueSeriesColumn) {
            this.product.setValueSeriesColumn(valueSeriesColumn);
            return this;
        }

        public Builder dataParameters(final String filter, final DataSet dataSet) {
            this.product.setFilter(filter);
            this.product.setDataSet(dataSet);
            return this;
        }
    }

}