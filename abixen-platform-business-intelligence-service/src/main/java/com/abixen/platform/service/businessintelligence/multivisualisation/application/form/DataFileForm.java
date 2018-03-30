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
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileDto;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


public class DataFileForm implements Form {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private Set<DataFileColumnDto> columns = new HashSet<>();

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

    public Set<DataFileColumnDto> getColumns() {
        return columns;
    }

    public void setColumns(Set<DataFileColumnDto> columns) {
        this.columns = columns;
    }

    public DataFileForm() {
        super();
    }

    public DataFileForm(DataFileDto dataFileDto) {
        this.setId(dataFileDto.getId());
        this.setName(dataFileDto.getName());
        this.setDescription(dataFileDto.getDescription());
        this.setColumns(dataFileDto.getColumns());
    }

}