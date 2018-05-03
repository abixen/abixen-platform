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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data;

import com.abixen.platform.common.domain.model.EntityBuilder;
import com.abixen.platform.common.infrastructure.util.ModelKeys;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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

    protected DataColumn() {
    }

    public Long getId() {
        return id;
    }

    protected void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    protected void setName(final String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    protected void setPosition(final Integer position) {
        this.position = position;
    }

    public List<DataValue> getValues() {
        return values;
    }

    protected void setValues(final List<DataValue> values) {
        if (this.values != null) {
            this.values.clear();
            this.values.addAll(values);
        } else {
            this.values = values;
        }
    }

    public void changeName(final String name) {
        setName(name);
    }

    public void changePosition(final Integer position) {
        setPosition(position);
    }

    public void changeValues(final List<DataValue> values) {
        setValues(values);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EntityBuilder<DataColumn> {

        protected Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new DataColumn();
        }

        public Builder name(final String name) {
            this.product.setName(name);
            return this;
        }

        public Builder position(final Integer position) {
            this.product.setPosition(position);
            return this;
        }

        public Builder values(final List<DataValue> values) {
            this.product.setValues(values);
            return this;
        }
    }

}