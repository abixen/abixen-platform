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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource;

import com.abixen.platform.common.util.ModelKeys;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataSourceType;
import com.abixen.platform.common.domain.model.audit.SimpleAuditingModel;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "data_source")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(sequenceName = "data_source_seq", name = "data_source_seq", allocationSize = 1)
public class DataSource extends SimpleAuditingModel implements Serializable {

    private static final long serialVersionUID = -1420930478759410093L;

    public static final int NAME_MAX_LENGTH = 60;
    public static final int DESCRIPTION_MAX_LENGTH = 1000;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "data_source_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = ModelKeys.NAME_MAX_LENGTH, nullable = false)
    @NotNull
    @Size(max = NAME_MAX_LENGTH)
    private String name;

    @Column(name = "description", nullable = true)
    @Size(max = DESCRIPTION_MAX_LENGTH)
    private String description;

    @OneToMany(mappedBy = "dataSource", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DataSourceColumn> columns = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @NotNull
    private DataSourceType dataSourceType;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "filter", nullable = true)
    private String filter;

    protected DataSource() {
    }

    @Override
    public Long getId() {
        return id;
    }

    void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(final String description) {
        this.description = description;
    }

    public Set<DataSourceColumn> getColumns() {
        return columns;
    }

    void setColumns(final Set<DataSourceColumn> columns) {
        if (this.columns != null) {
            this.columns.clear();
            this.columns.addAll(columns);
        } else {
            this.columns = columns;
        }
    }

    public void removeColumns(final Set<DataSourceColumn> columns) {
        if (this.columns != null) {
            this.columns.removeAll(columns);
        }
    }

    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    void setDataSourceType(final DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getFilter() {
        return filter;
    }

    void setFilter(final String filter) {
        this.filter = filter;
    }

    public void changeDetails(final String name, final String description) {
        setName(name);
        setDescription(description);
    }

    public void changeParameters(final DataSourceType dataSourceType, final String filter) {
        setDataSourceType(dataSourceType);
        setFilter(filter);
    }

    public void changeColumns(final Set<DataSourceColumn> columns) {
        setColumns(columns);
    }


}