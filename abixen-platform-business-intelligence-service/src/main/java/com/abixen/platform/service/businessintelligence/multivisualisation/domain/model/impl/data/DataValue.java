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
package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.data;

import com.abixen.platform.common.model.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
@Table(name = "data_value")
@SequenceGenerator(sequenceName = "data_value_seq", name = "data_value_seq", allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DataValue<E> extends Model {

    private static final long serialVersionUID = 9032457388585082311L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "data_value_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "position")
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "data_column_id", nullable = false)
    @JsonIgnore
    private DataColumn dataColumn;

    DataValue() {
    }

    public Long getId() {
        return id;
    }

    void setId(final Long id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    void setPosition(final Integer position) {
        this.position = position;
    }

    public abstract E getValue();

    abstract void setValue(final E value);

    public DataColumn getDataColumn() {
        return dataColumn;
    }

    public void setDataColumn(final DataColumn dataColumn) {
        this.dataColumn = dataColumn;
    }

    public void changePosition(final Integer position) {
        setPosition(position);
    }

    public void changeDataColumn(final DataColumn dataColumn) {
        setDataColumn(dataColumn);
    }

}

