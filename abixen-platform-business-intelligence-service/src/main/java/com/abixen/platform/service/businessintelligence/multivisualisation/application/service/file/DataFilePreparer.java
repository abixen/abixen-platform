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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service.file;

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.datafile.ColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.datafile.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.datafile.RowDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataValueType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data.DataValue;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data.DataValueDate;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data.DataValueDouble;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data.DataValueInteger;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.data.DataValueString;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFileColumn;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
class DataFilePreparer {

    public List<DataFileColumn> prepareData(final DataFileDto readedData, final FileParserMessage msg, final Boolean readFirstColumnAsColumnName) {
        final List<DataValueType> columnTypes = getRowAsDataValueTypeList(msg, readedData.getRowTypes());
        final List<DataFileColumn> dataFileColumns = new ArrayList<>();
        final List<RowDto> rows = readedData.getRows();
        for (int i = 0; i < columnTypes.size(); i++) {
            String name = null;
            if (readFirstColumnAsColumnName) {
                name = rows.get(0).getColumns().get(i).getValue();
            }
            final List<DataValue> dataValues = new ArrayList<>();
            int startIndexColumn = readFirstColumnAsColumnName ? 1 : 0;
            for (int j = startIndexColumn; j < rows.size(); j++) {
                final RowDto rowDto = rows.get(j);
                dataValues.add(parseAs(columnTypes.get(i), rowDto.getColumns().get(i).getValue().trim()));
            }
            dataFileColumns.add((DataFileColumn) DataFileColumn.builder()
                    .dataValueType(columnTypes.get(i))
                    .values(dataValues)
                    .name(name)
                    .position(i)
                    .build());
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
            case DOUBLE:
                return parseAsDouble(element);
            case DATE:
                return parseAsDate(element);
            case INTEGER:
                return parseAsInteger(element);
            case STRING:
                return parseAsString(element);
            default:
                return null;
        }
    }

    //FIXME - ugly - should be DataValue?
    private DataValue parseAsDate(final String element) {
        final DateFormat df = DateFormat.getDateInstance();
        try {
            return DataValueDate.builder()
                    .value(df.parse(element))
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    //FIXME - should be DataValue?
    private DataValue parseAsString(final String element) {
        return DataValueString.builder()
                .value(element)
                .build();
    }

    private DataValueInteger parseAsInteger(final String element) {
        return DataValueInteger.builder()
                .value(Integer.valueOf(element))
                .build();
    }

    private DataValueDouble parseAsDouble(final String element) {
        return DataValueDouble.builder()
                .value(Double.valueOf(element.replace(",", ".")))
                .build();
    }

}