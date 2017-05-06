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


package com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.file;

import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSource;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "file_data_source_row")
@SequenceGenerator(sequenceName = "data_source_row_seq", name = "data_source_row_seq", allocationSize = 1)
public class FileDataSourceRow {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "data_source_row_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "row_number")
    @NotNull
    @Min(0)
    private Integer rowNumber;

    @ManyToOne
    @JoinColumn(name = "file_data_source_id", nullable = false)
    @NotNull
    @Valid
    private DataSource fileDataSource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public DataSource getFileDataSource() {
        return fileDataSource;
    }

    public void setFileDataSource(DataSource fileDataSource) {
        this.fileDataSource = fileDataSource;
    }
}
