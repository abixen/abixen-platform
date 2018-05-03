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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service.file.validator;

import com.abixen.platform.common.infrastructure.exception.PlatformRuntimeException;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.datafile.ColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.datafile.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.datafile.RowDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataValueType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFileColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Component
public class DataFileValidator {

    private final DataTypeValidator dataTypeValidator;

    @Autowired
    public DataFileValidator(DataTypeValidator dataTypeValidator) {
        this.dataTypeValidator = dataTypeValidator;
    }

    public boolean valid(final DataFileDto dataFileDto, final FileParserMessage msg, final Boolean readFirstColumnAsColumnName) {
        final List<DataValueType> columnTypes = getRowAsDataValueTypeList(msg, dataFileDto.getRowTypes());
        if (!msg.getFileParseErrors().isEmpty()) {
            return false;
        }
        final List<RowDto> rows = dataFileDto.getRows();
        final int startRowIndex = readFirstColumnAsColumnName ? 1 : 0;
        for (int i = startRowIndex; i < rows.size(); i++) {
            List<ColumnDto> columns = rows.get(i).getColumns();
            for (int j = 0; j < columnTypes.size(); j++) {
                validateAs(msg, columnTypes, i, j, columns.get(j).getValue().trim());
            }
        }
        return msg.getFileParseErrors().isEmpty();
    }

    private List<DataValueType> getRowAsDataValueTypeList(final FileParserMessage msg, final RowDto rowTypes) {
        final List<DataValueType> columnTypeAsDataValueTypeList = new ArrayList<>();
        for (ColumnDto columnType : rowTypes.getColumns()) {
            dataTypeValidator.validateColumnType(msg, columnTypeAsDataValueTypeList, columnType.getValue());
        }
        return columnTypeAsDataValueTypeList;
    }

    private void validateAs(final FileParserMessage<DataFileColumn> msg, final List<DataValueType> columnTypes, final int i, final int j, final String element) {
        switch (columnTypes.get(j)) {
            case DOUBLE:
                dataTypeValidator.validateAsDouble(i, j, msg, element);
                break;
            case DATE:
                dataTypeValidator.validateAsDate(i, j, msg, element);
                break;
            case INTEGER:
                dataTypeValidator.validateAsInteger(i, j, msg, element);
                break;
            case STRING:
                dataTypeValidator.validateAsString(i, j, msg, element);
                break;
            default:
                throw new PlatformRuntimeException(format("[Row: %s Cell: %s ]Column type not recognized", i, j));
        }
    }

}