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

package com.abixen.platform.service.businessintelligence.multivisualisation.form;

import com.abixen.platform.common.form.Form;
import com.abixen.platform.common.util.WebModelJsonSerialize;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataFileColumnDTO;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFile;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


public class DataFileForm implements Form {

    @JsonView(WebModelJsonSerialize.class)
    private Long id;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private String name;

    @JsonView(WebModelJsonSerialize.class)
    private String description;

    @JsonView(WebModelJsonSerialize.class)
    private Set<DataFileColumnDTO> columns = new HashSet<>();

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

    public Set<DataFileColumnDTO> getColumns() {
        return columns;
    }

    public void setColumns(Set<DataFileColumnDTO> columns) {
        this.columns = columns;
    }
    public DataFileForm() {
        super();
    }

    public DataFileForm(DataFile dataFile) {
        this.setId(dataFile.getId());
        this.setName(dataFile.getName());
        this.setDescription(dataFile.getDescription());
    }


}
