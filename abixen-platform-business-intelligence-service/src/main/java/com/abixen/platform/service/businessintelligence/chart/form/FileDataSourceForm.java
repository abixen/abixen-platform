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

package com.abixen.platform.service.businessintelligence.chart.form;

import com.abixen.platform.core.form.Form;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.service.businessintelligence.chart.model.enumtype.DataSourceFileType;
import com.abixen.platform.service.businessintelligence.chart.model.impl.FileDataSource;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;


public class FileDataSourceForm extends DataSourceForm implements Form {

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private DataSourceFileType dataSourceFileType;

    public FileDataSourceForm() {
    }

    public FileDataSourceForm(FileDataSource fileDataSource) {
        this.setId(fileDataSource.getId());
        this.setName(fileDataSource.getName());
        this.setDescription(fileDataSource.getDescription());
        this.dataSourceFileType = fileDataSource.getDataSourceFileType();
    }

    public DataSourceFileType getDataSourceFileType() {
        return dataSourceFileType;
    }

    public void setDataSourceFileType(DataSourceFileType dataSourceFileType) {
        this.dataSourceFileType = dataSourceFileType;
    }
}
