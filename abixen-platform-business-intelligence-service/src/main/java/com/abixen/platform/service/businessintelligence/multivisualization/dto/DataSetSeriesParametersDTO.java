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

package com.abixen.platform.service.businessintelligence.multivisualization.dto;

import com.abixen.platform.service.businessintelligence.multivisualization.model.enumtype.DataValueType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;


//TODO - to remove
public class DataSetSeriesParametersDTO implements Serializable {
    private static final long serialVersionUID = -4430417766491197470L;

    @NotNull
    private Long dataSourceId;

    @NotNull
    private DataValueType valueTypeAxisX = DataValueType.DOUBLE;

    @NotNull
    private DataValueType valueTypeAxisY = DataValueType.DOUBLE;

    @NotNull
    private Map<String, Map<Integer, Set<Integer>>> series;


    public Map<String, Map<Integer, Set<Integer>>> getSeries() {
        return series;
    }

    /**
     *
     * @param series Map( "name" - Map( "X axis index" , Set( "Y axis index")))
     */
    public void setSeries(Map<String, Map<Integer, Set<Integer>>> series) {
        this.series = series;
    }

    public DataValueType getValueTypeAxisX() {
        return valueTypeAxisX;
    }

    public void setValueTypeAxisX(DataValueType valueTypeAxisX) {
        this.valueTypeAxisX = valueTypeAxisX;
    }

    public DataValueType getValueTypeAxisY() {
        return valueTypeAxisY;
    }

    public void setValueTypeAxisY(DataValueType valueTypeAxisY) {
        this.valueTypeAxisY = valueTypeAxisY;
    }

    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
}
