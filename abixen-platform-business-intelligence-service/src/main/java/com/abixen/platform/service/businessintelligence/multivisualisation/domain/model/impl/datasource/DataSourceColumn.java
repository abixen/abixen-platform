/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource;

import com.abixen.platform.common.util.ModelKeys;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataValueType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource.NAME_MAX_LENGTH;


@Entity
@Table(name = "data_source_column")
@SequenceGenerator(sequenceName = "data_source_column_seq", name = "data_source_column_seq", allocationSize = 1)
public class DataSourceColumn implements Serializable {

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

    DataSourceColumn() {
    }

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

    public Integer getPosition() {
        return position;
    }

    void setPosition(final Integer position) {
        this.position = position;
    }

    public DataValueType getDataValueType() {
        return dataValueType;
    }

    void setDataValueType(final DataValueType dataValueType) {
        this.dataValueType = dataValueType;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void changeDetails(final String name) {
        setName(name);
    }

    public void changeParameters(final DataValueType dataValueType, final Integer position) {
        setDataValueType(dataValueType);
        setPosition(position);
    }

    public void changeDataSource(final DataSource dataSource) {
        setDataSource(dataSource);
    }

}
