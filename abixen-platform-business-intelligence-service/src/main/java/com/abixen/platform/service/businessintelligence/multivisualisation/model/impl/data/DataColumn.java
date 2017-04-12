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

package com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.data;

import com.abixen.platform.common.util.ModelKeys;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "data_column")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SequenceGenerator(sequenceName = "data_column_seq", name = "data_column_seq", allocationSize = 1)
@DiscriminatorColumn(name = "type")
public class DataColumn {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "data_column_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = ModelKeys.NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(name = "position", nullable = false)
    private Integer position;

    @OneToMany(mappedBy = "dataColumn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DataValue> values = new ArrayList<>();

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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public List<DataValue> getValues() {
        return values;
    }

    public void setValues(List<DataValue> values) {
        if (this.values != null) {
            this.values.clear();
            this.values.addAll(values);
        } else {
            this.values = values;
        }
    }

    public void addValues(List<DataValue> values) {
        if (this.values != null) {
            this.values.addAll(values);
        } else {
            this.values = values;
        }
    }

    public void removeValues(List<DataValue> values) {
        if (this.values != null) {
            this.values.removeAll(values);
        }
    }
}
