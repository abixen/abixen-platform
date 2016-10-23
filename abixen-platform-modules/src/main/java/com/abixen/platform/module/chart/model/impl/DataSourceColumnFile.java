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

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@DiscriminatorValue("FILE")
public class DataSourceColumnFile extends DataSourceColumn implements Serializable {

    private static final long serialVersionUID = 2032457388581081321L;

    public DataSourceColumnFile() {

    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "column_id", nullable = false)
    private List<DataSourceValue> values;

    public List<DataSourceValue> getValues() {
        return values;
    }

    public void setValues(List<DataSourceValue> values) {
        this.values = values;
    }


    @Override
    public String toString() {
        return "DataSourceColumnFile{" +
                "id=" + getId() +
                ", values=" + values +
                '}';
    }
}