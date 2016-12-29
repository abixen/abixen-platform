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

package com.abixen.platform.businessintelligence.chart.model.impl;

import com.abixen.platform.core.util.ModelKeys;
import com.abixen.platform.businessintelligence.chart.model.enumtype.DataSourceType;
import com.abixen.platform.businessintelligence.chart.model.web.DataSourceWeb;
import com.abixen.platform.core.model.audit.AuditingModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "data_source")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(sequenceName = "data_source_seq", name = "data_source_seq", allocationSize = 1)
public class DataSource extends AuditingModel implements DataSourceWeb, Serializable {

    private static final long serialVersionUID = -1420930478759410093L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "data_source_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = ModelKeys.NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(mappedBy = "dataSource", cascade = CascadeType.ALL)
    private Set<DataSourceColumn> columns = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private DataSourceType dataSourceType;

    @Lob
    @Column(name = "filter", nullable = true)
    private String filter;

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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Set<DataSourceColumn> getColumns() {
        return columns;
    }

    public void setColumns(Set<DataSourceColumn> columns) {
        this.columns = columns;
    }

    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    @Override
    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }


}