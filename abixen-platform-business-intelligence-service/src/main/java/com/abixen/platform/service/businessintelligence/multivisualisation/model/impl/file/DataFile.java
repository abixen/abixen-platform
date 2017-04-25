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

package com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file;


import com.abixen.platform.common.util.ModelKeys;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataFileWeb;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "data_file")
@SequenceGenerator(sequenceName = "data_file_seq", name = "data_file_seq", allocationSize = 1)
public class DataFile implements DataFileWeb {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "data_file_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = ModelKeys.NAME_MAX_LENGTH, nullable = false)
    @NotNull
    private String name;

    @Column(name = "description", length = ModelKeys.NAME_MAX_LENGTH)
    private String description;

    @OneToMany(mappedBy = "dataFile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DataFileColumn> columns = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DataFileColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<DataFileColumn> columns) {
        if (this.columns != null) {
            this.columns.clear();
            this.columns.addAll(columns);
        } else {
            this.columns = columns;
        }
    }

    public void addColumns(List<DataFileColumn> columns) {
        if (this.columns != null) {
            this.columns.addAll(columns);
        } else {
            this.columns = columns;
        }
    }

    public void removeColumns(List<DataFileColumn> columns) {
        if (this.columns != null) {
            this.columns.removeAll(columns);
        }
    }
}
