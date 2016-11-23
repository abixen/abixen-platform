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


@Entity
@Table(name = "data_source_value_double")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class DataSourceValueDouble extends DataSourceValue<Double> {


    private static final long serialVersionUID = -1434303122608329824L;

    @Column(name = "value", nullable = true)
    private Double value;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
