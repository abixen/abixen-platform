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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource;

import com.abixen.platform.common.domain.model.EntityBuilder;
import com.abixen.platform.common.domain.model.audit.SimpleAuditingModel;
import com.abixen.platform.common.infrastructure.util.ModelKeys;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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

    @Column(name = "filter")
    private String filter;

    protected DataSource() {
    }

    @Override
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

    public String getDescription() {
        return description;
    }

    private void setDescription(final String description) {
        this.description = description;
    }

    public Set<DataSourceColumn> getColumns() {
        return columns;
    }

    private void setColumns(final Set<DataSourceColumn> columns) {
        if (this.columns != null) {
            this.columns.clear();
            this.columns.addAll(columns);
        } else {
            this.columns = columns;
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

    public static class Builder extends EntityBuilder<DataSource> {

        protected Builder() {
        }

        @Override
        public void initProduct() {
            this.product = new DataSource();
        }

        public Builder details(final String name, final String description) {
            this.product.setName(name);
            this.product.setDescription(description);
            return this;
        }

        public Builder parameters(final DataSourceType dataSourceType, final String filter) {
            this.product.setDataSourceType(dataSourceType);
            this.product.setFilter(filter);
            return this;
        }

        public Builder columns(final Set<DataSourceColumn> dataSourceColumns) {
            this.product.setColumns(dataSourceColumns);
            return this;
        }
    }

}