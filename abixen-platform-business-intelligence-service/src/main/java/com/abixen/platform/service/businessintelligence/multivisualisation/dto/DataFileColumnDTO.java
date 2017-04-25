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

package com.abixen.platform.service.businessintelligence.multivisualisation.dto;

import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DataValueType;

import java.util.ArrayList;
import java.util.List;

public class DataFileColumnDTO {

    private String name;

    private DataValueType dataValueType;

    private Integer position;

    private List<DataFileColumnValueDTO> values = new ArrayList<>();

    public DataFileColumnDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataValueType getDataValueType() {
        return dataValueType;
    }

    public void setDataValueType(DataValueType dataValueType) {
        this.dataValueType = dataValueType;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public List<DataFileColumnValueDTO> getValues() {
        return values;
    }

    public void setValues(List<DataFileColumnValueDTO> values) {
        this.values = values;
    }
}
