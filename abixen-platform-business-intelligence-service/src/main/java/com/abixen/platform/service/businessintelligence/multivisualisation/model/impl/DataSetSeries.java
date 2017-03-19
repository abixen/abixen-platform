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

package com.abixen.platform.service.businessintelligence.multivisualisation.model.impl;

import com.abixen.platform.common.util.ModelKeys;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataSetSeriesWeb;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "data_set_series")
@SequenceGenerator(sequenceName = "data_set_series_seq", name = "data_set_series_seq", allocationSize = 1)
public class DataSetSeries implements DataSetSeriesWeb, Serializable {

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

    @Lob
    @Column(name = "filter", nullable = true)
    private String filter;

    @ManyToOne
    @JoinColumn(name = "data_set_id", nullable = false)
    private DataSet dataSet;

    public DataSetSeries() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public DataSetSeriesColumn getValueSeriesColumn() {
        return valueSeriesColumn;
    }

    public void setValueSeriesColumn(DataSetSeriesColumn valueSeriesColumn) {
        this.valueSeriesColumn = valueSeriesColumn;
    }

    @Override
    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }
}
