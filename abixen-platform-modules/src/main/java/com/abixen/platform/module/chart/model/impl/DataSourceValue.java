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

import com.abixen.platform.core.model.Model;

import javax.persistence.*;


@Entity
@Table(name = "data_source_value")
@SequenceGenerator(sequenceName = "data_source_value_seq", name = "data_source_value_seq", allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)
abstract public class DataSourceValue<E> extends Model {

    private static final long serialVersionUID = 9032457388585082311L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "data_source_value_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "position")
    private Integer position;

    public DataSourceValue() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    abstract public E getValue();

    abstract public void setValue(E value);

}

