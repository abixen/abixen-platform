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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service.impl.parser.preparer;

import com.abixen.platform.service.businessintelligence.multivisualisation.application.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataValueType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.data.*;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFileColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.util.dataFile.ColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.util.dataFile.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.util.dataFile.RowDto;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileDataPreparer {

    public List<DataFileColumn> prepareData(final DataFileDto readedData, final FileParserMessage msg, final Boolean readFirstColumnAsColumnName) {
        final List<DataValueType> columnTypes = getRowAsDataValueTypeList(msg, readedData.getRowTypes());
        final List<DataFileColumn> dataFileColumns = new ArrayList<>();
        final List<RowDto> rows = readedData.getRows();
        for (int i = 0; i < columnTypes.size(); i++) {
            final DataFileColumn dataFileColumn = new DataFileColumn();
            if (readFirstColumnAsColumnName) {
                dataFileColumn.setName(rows.get(0).getColumns().get(i).getValue());
            }
            dataFileColumn.setDataValueType(columnTypes.get(i));
            dataFileColumn.setPosition(i);
            final List<DataValue> dataValues = new ArrayList<>();
            int startIndexColumn = readFirstColumnAsColumnName ? 1 : 0;
            for (int j = startIndexColumn; j < rows.size(); j++) {
                final RowDto rowDto = rows.get(j);
                dataValues.add(parseAs(columnTypes.get(i), rowDto.getColumns().get(i).getValue().trim()));
            }
            dataFileColumn.setValues(dataValues);
            dataFileColumns.add(dataFileColumn);
        }
        return dataFileColumns;
    }

    private List<DataValueType> getRowAsDataValueTypeList(final FileParserMessage msg, final RowDto rowTypes) {
        final List<DataValueType> columnTypeAsDataValueTypeList = new ArrayList<>();
        for (final ColumnDto columnType : rowTypes.getColumns()) {
            columnTypeAsDataValueTypeList.add(DataValueType.valueOf(columnType.getValue().trim()));
        }
        return columnTypeAsDataValueTypeList;
    }

    private DataValue parseAs(final DataValueType columnType, final String element) {
        switch (columnType) {
            case DOUBLE: return parseAsDouble(element);
            case DATE: return parseAsDate(element);
            case INTEGER: return parseAsInteger(element);
            case STRING: return parseAsString(element);
            default: return null;
        }
    }

    private DataValue parseAsDate(final String element) {
        final DateFormat df = DateFormat.getDateInstance();
        final DataValueDate dataValueDate = new DataValueDate();
        try {
            dataValueDate.setValue(df.parse(element));
        } catch (Exception e) {
            return null;
        }
        return dataValueDate;
    }

    private DataValue parseAsString(final String element) {
        final DataValueString dataValueString = new DataValueString();
        dataValueString.setValue(element);
        return dataValueString;
    }

    private DataValueInteger parseAsInteger(final String element) {
        final DataValueInteger dataValueInteger = new DataValueInteger();
        dataValueInteger.setValue(Integer.valueOf(element));
        return dataValueInteger;
    }

    private DataValueDouble parseAsDouble(final String element) {
        final DataValueDouble dataValueDouble = new DataValueDouble();
        dataValueDouble.setValue(Double.valueOf(element.replace(",", ".")));
        return dataValueDouble;
    }

}
