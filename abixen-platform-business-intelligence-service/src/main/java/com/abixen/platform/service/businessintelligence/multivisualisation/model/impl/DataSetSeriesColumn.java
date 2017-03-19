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
import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.ColumnType;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataSetSeriesColumnWeb;

import javax.persistence.*;
import java.io.Serializable;




@Entity
@Table(name = "data_set_series_column")
@SequenceGenerator(sequenceName = "data_set_series_column_seq", name = "data_set_series_column_seq", allocationSize = 1)
public class DataSetSeriesColumn implements DataSetSeriesColumnWeb, Serializable {

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

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public ColumnType getType() {
        return type;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public DataSourceColumn getDataSourceColumn() {
        return dataSourceColumn;
    }

    public void setDataSourceColumn(DataSourceColumn dataSourceColumn) {
        this.dataSourceColumn = dataSourceColumn;
    }
}
