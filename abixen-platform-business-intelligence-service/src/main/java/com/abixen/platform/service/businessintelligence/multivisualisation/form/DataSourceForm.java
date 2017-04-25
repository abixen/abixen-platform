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
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataSourceColumnWeb;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

import static com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSource.DESCRIPTION_MAX_LENGTH;
import static com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSource.NAME_MAX_LENGTH;


public class DataSourceForm implements Form {

    @JsonView(WebModelJsonSerialize.class)
    private Long id;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    @Size(max = NAME_MAX_LENGTH)
    private String name;

    @JsonView(WebModelJsonSerialize.class)
    @Size(max = DESCRIPTION_MAX_LENGTH)
    private String description;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private Set<DataSourceColumnWeb> columns;

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

    public Set<DataSourceColumnWeb> getColumns() {
        return columns;
    }

    public void setColumns(Set<DataSourceColumnWeb> columns) {
        this.columns = columns;
    }


}
