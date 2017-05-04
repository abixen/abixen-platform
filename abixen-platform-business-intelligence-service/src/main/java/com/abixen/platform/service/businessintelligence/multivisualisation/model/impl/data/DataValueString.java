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

import com.abixen.platform.service.businessintelligence.multivisualisation.util.ModelKeys;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;


@Entity
@Table(name = "data_value_string")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class DataValueString extends DataValue<String> {

    private static final long serialVersionUID = 4578637099421599970L;

    @Length(min = 0, max = ModelKeys.DATASOURCE_VALUE_STRING_MAX_LENGTH)
    @Column(name = "value", length = ModelKeys.DATASOURCE_VALUE_STRING_MAX_LENGTH, nullable = true)
    private String value;

    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
