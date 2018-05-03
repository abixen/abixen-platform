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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.form;

import com.abixen.platform.common.application.form.Form;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

import static com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSource.DESCRIPTION_MAX_LENGTH;
import static com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSource.NAME_MAX_LENGTH;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "classType")
@JsonSubTypes({@JsonSubTypes.Type(value = DatabaseDataSourceForm.class, name = "DB"),
        @JsonSubTypes.Type(value = FileDataSourceForm.class, name = "FILE")})
public class DataSourceForm implements Form {

    private Long id;

    @NotNull
    @Size(max = NAME_MAX_LENGTH)
    private String name;

    @Size(max = DESCRIPTION_MAX_LENGTH)
    private String description;

    private DataSourceType dataSourceType;

    @NotNull
    private Set<DataSourceColumnDto> columns;

    public DataSourceForm() {
    }

    public DataSourceForm(DataSourceDto dataSourceDto) {
        this.id = dataSourceDto.getId();
        this.name = dataSourceDto.getName();
        this.description = dataSourceDto.getDescription();
        this.columns = dataSourceDto.getColumns();
        this.dataSourceType = dataSourceDto.getDataSourceType();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public Set<DataSourceColumnDto> getColumns() {
        return columns;
    }

    public void setColumns(Set<DataSourceColumnDto> columns) {
        this.columns = columns;
    }

}