/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.service.businessintelligence.multivisualisation.util.impl;

import com.abixen.platform.common.util.EntityBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataFileColumnDTO;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.data.DataValue;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.data.DataValueDouble;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.data.DataValueInteger;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.data.DataValueString;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFile;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFileColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.util.DataFileBuilder;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class DataFileBuilderImpl extends EntityBuilder<DataFile> implements DataFileBuilder {

    @Override
    protected void initProduct() {
        this.product = new DataFile();
    }

    @Override
    public DataFileBuilder base(String name, String description) {
        this.product.setName(name);
        this.product.setDescription(description);
        return this;
    }

    @Override
    public DataFileBuilder data(Set<DataFileColumnDTO> dataColumn) {
        List<DataFileColumn> columns = new ArrayList<>();
        dataColumn.forEach(entity -> {
            DataFileColumn dataFileColumn = new DataFileColumn();
            dataFileColumn.setName(entity.getName());
            dataFileColumn.setPosition(entity.getPosition());
            dataFileColumn.setDataValueType(entity.getDataValueType());
            List<DataValue> values = new ArrayList<>();
            entity.getValues().forEach(child -> {
                if (child != null && child.getValue() != null) {
                    String value = child.getValue().trim();
                    DataValue dataValue = getObjForValue(value);
                    dataValue.setDataColumn(dataFileColumn);
                    values.add(dataValue);
                }
            });
            dataFileColumn.setValues(values);
            dataFileColumn.setDataFile(this.product);
            columns.add(dataFileColumn);
        });
        this.product.setColumns(columns);
        return this;
    }

    private DataValue getObjForValue(String value) {
        DataValue dataValue;
        if (value == null) {
            dataValue = new DataValueString();
            dataValue.setValue("");
        } else {
            if (NumberUtils.isNumber(value)) {
                if (Ints.tryParse(value) != null) {
                    dataValue = new DataValueInteger();
                    dataValue.setValue(Integer.parseInt(value));
                } else {
                    dataValue = new DataValueDouble();
                    dataValue.setValue(Double.parseDouble(value));
                }
            } else {
                dataValue = new DataValueString();
                dataValue.setValue(value);
            }
        }
        return dataValue;
    }


}
