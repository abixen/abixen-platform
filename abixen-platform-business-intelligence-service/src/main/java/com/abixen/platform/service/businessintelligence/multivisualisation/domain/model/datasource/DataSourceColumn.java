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
import com.abixen.platform.common.infrastructure.util.ModelKeys;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataValueType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSource.NAME_MAX_LENGTH;


@Entity
@Table(name = "data_source_column")
@SequenceGenerator(sequenceName = "data_source_column_seq", name = "data_source_column_seq", allocationSize = 1)
public final class DataSourceColumn implements Serializable {

    private static final long serialVersionUID = 8078651909903181737L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "data_source_column_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = ModelKeys.NAME_MAX_LENGTH, nullable = false)
    @NotNull
    @Size(max = NAME_MAX_LENGTH)
    private String name;

    @Column(name = "position")
    @Min(0)
    private Integer position;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_value_type")
    @NotNull
    private DataValueType dataValueType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "data_source_id")
    @Valid
    private DataSource dataSource;

    private DataSourceColumn() {
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

    public Integer getPosition() {
        return position;
    }

    private void setPosition(final Integer position) {
        this.position = position;
    }

    public DataValueType getDataValueType() {
        return dataValueType;
    }

    private void setDataValueType(final DataValueType dataValueType) {
        this.dataValueType = dataValueType;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    private void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void changeDataSource(final DataSource dataSource) {
        setDataSource(dataSource);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<DataSourceColumn> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new DataSourceColumn();
        }

        public Builder details(final String name) {
            this.product.setName(name);
            return this;
        }

        public Builder parameters(final DataValueType dataValueType, final Integer position) {
            this.product.setDataValueType(dataValueType);
            this.product.setPosition(position);
            return this;
        }

        public Builder dataSource(final DataSource dataSource) {
            this.product.setDataSource(dataSource);
            return this;
        }

    }

}