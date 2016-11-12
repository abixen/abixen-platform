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

package com.abixen.platform.module.chart.model.impl;

import com.abixen.platform.module.chart.model.enumtype.DataSourceFileType;
import com.abixen.platform.module.chart.model.web.FileDataSourceWeb;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "file_data_source")
public class FileDataSource extends DataSource implements FileDataSourceWeb, Serializable {

    private static final long serialVersionUID = -1420930478759410093L;


    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = true)
    private DataSourceFileType dataSourceFileType;


    public DataSourceFileType getDataSourceFileType() {
        return dataSourceFileType;
    }

    public void setDataSourceFileType(DataSourceFileType dataSourceFileType) {
        this.dataSourceFileType = dataSourceFileType;
    }

}