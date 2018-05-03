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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.file;

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFile;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "file_data_source")
public final class FileDataSource extends DataSource implements Serializable {

    private static final long serialVersionUID = -1420930478759410093L;

    @ManyToOne
    @JoinColumn(name = "file_data_id", nullable = false)
    @NotNull
    @Valid
    private DataFile dataFile;

    @OneToMany(mappedBy = "fileDataSource", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileDataSourceRow> rows = new HashSet<>();

    private FileDataSource() {
    }

    public DataFile getDataFile() {
        return dataFile;
    }

    private void setDataFile(final DataFile dataFile) {
        this.dataFile = dataFile;
    }

    public Set<FileDataSourceRow> getRows() {
        return rows;
    }

    private void setRows(final Set<FileDataSourceRow> rows) {
        this.rows = rows;
    }

    public void changeDataFile(final DataFile dataFile) {
        setDataFile(dataFile);
    }

    public void changeRows(final Set<FileDataSourceRow> rows) {
        setRows(rows);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends DataSource.Builder {

        private Builder() {
        }

        @Override
        public void initProduct() {
            this.product = new FileDataSource();
        }

        public Builder rows(final Set<FileDataSourceRow> rows) {
            ((FileDataSource) this.product).setRows(rows);
            return this;
        }

        public Builder dataFile(final DataFile dataFile) {
            ((FileDataSource) this.product).setDataFile(dataFile);
            return this;
        }

    }

}