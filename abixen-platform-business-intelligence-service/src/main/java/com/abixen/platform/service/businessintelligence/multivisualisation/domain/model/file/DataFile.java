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


import com.abixen.platform.common.domain.model.EntityBuilder;
import com.abixen.platform.common.domain.model.audit.SimpleAuditingModel;
import com.abixen.platform.common.infrastructure.util.ModelKeys;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "data_file")
@SequenceGenerator(sequenceName = "data_file_seq", name = "data_file_seq", allocationSize = 1)
public final class DataFile extends SimpleAuditingModel implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "data_file_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = ModelKeys.NAME_MAX_LENGTH, nullable = false)
    @NotNull
    private String name;

    @Column(name = "description", length = ModelKeys.NAME_MAX_LENGTH)
    private String description;

    @OneToMany(mappedBy = "dataFile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DataFileColumn> columns = new HashSet<>();

    private DataFile() {
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

    public String getDescription() {
        return description;
    }

    private void setDescription(final String description) {
        this.description = description;
    }

    public Set<DataFileColumn> getColumns() {
        return columns;
    }

    private void setColumns(final Set<DataFileColumn> columns) {
        if (this.columns != null) {
            this.columns.clear();
            this.columns.addAll(columns);
        } else {
            this.columns = columns;
        }
    }

    public void changeDetails(final String name, final String description) {
        setName(name);
        setDescription(description);
    }

    public void changeColumns(final Set<DataFileColumn> columns) {
        setColumns(columns);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<DataFile> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new DataFile();
        }

        public Builder details(final String name, final String description) {
            this.product.setName(name);
            this.product.setDescription(description);
            return this;
        }

        public Builder columns(final Set<DataFileColumn> columns) {
            this.product.setColumns(columns);
            return this;
        }

    }

}