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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.file;

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFile;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "file_data_source")
public class FileDataSource extends DataSource implements Serializable {

    private static final long serialVersionUID = -1420930478759410093L;

    @ManyToOne
    @JoinColumn(name = "file_data_id", nullable = false)
    @NotNull
    @Valid
    private DataFile dataFile;

    FileDataSource() {
    }

    @OneToMany(mappedBy = "fileDataSource", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileDataSourceRow> rows = new HashSet<>();

    public DataFile getDataFile() {
        return dataFile;
    }

    void setDataFile(DataFile dataFile) {
        this.dataFile = dataFile;
    }

    public Set<FileDataSourceRow> getRows() {
        return rows;
    }

    void setRows(Set<FileDataSourceRow> rows) {
        this.rows = rows;
    }

    public void changeDataFile(DataFile dataFile) {
        setDataFile(dataFile);
    }

    public void changeRows(Set<FileDataSourceRow> rows) {
        setRows(rows);
    }
}