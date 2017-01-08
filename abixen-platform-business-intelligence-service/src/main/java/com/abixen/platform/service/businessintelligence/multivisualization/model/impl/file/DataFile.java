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

package com.abixen.platform.service.businessintelligence.multivisualization.model.impl.file;


import com.abixen.platform.core.util.ModelKeys;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "data_file")
@SequenceGenerator(sequenceName = "data_file_seq", name = "data_file_seq", allocationSize = 1)
public class DataFile {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "data_file_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = ModelKeys.NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(name = "descripton", length = ModelKeys.NAME_MAX_LENGTH, nullable = false)
    private String descripton;

    @OneToMany(mappedBy = "dataFile", cascade = CascadeType.ALL)
    private Set<DataFileColumn> columns = new HashSet<>();

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

    public String getDescripton() {
        return descripton;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }

    public Set<DataFileColumn> getColumns() {
        return columns;
    }

    public void setColumns(Set<DataFileColumn> columns) {
        this.columns = columns;
    }
}
