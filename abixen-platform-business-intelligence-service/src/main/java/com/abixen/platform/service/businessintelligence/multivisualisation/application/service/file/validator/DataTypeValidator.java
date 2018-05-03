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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.message.FileParseError;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataValueType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFileColumn;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
class DataTypeValidator {

    private static final Pattern INTEGER_PATTERN = Pattern.compile("[0-9]*+");
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?[0-9]*[\\\\.\\\\,]?[0-9]+([eE][-+]?[0-9]+)?");
    private static final List<Pattern> DATE_PATTERN_LIST = Arrays.asList(
            Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$"),
            Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$"),
            Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$")
    );

    public void validateColumnType(final FileParserMessage<DataFileColumn> msg, final List<DataValueType> columnTypeAsDataValueTypeList, final String typeAsString) {
        try {
            columnTypeAsDataValueTypeList.add(DataValueType.valueOf(typeAsString));
        } catch (IllegalArgumentException e) {
            msg.addFileParseError(new FileParseError(0, String.format("\"%s\" wasn't recognized as valid column type", typeAsString)));
        }
    }

    public void validateAsString(final Integer i, final Integer j, final FileParserMessage<DataFileColumn> msg, final String element) {
        if (element.equals("")) {
            msg.addFileParseError(new FileParseError(1, String.format("[Line %d, column %d] is empty", i, j)));
        }
    }

    public void validateAsDouble(final Integer i, final Integer j, final FileParserMessage<DataFileColumn> msg, final String element) {
        if (!DOUBLE_PATTERN.matcher(element).matches()) {
            msg.addFileParseError(new FileParseError(1, String.format("[Line %d, column %d] \"%s\" wasn't recognized as proper double format", i, j, element)));
        }
    }

    public void validateAsDate(final Integer i, final Integer j, final FileParserMessage<DataFileColumn> msg, final String element) {
        if (!DATE_PATTERN_LIST.stream().filter(pattern -> pattern.matcher(element).matches()).findAny().isPresent()) {
            msg.addFileParseError(new FileParseError(1, String.format("[Line %d, column %d] \"%s\" wasn't recognized as proper data format", i, j, element)));
        }
    }

    public void validateAsInteger(Integer i, Integer j, FileParserMessage<DataFileColumn> msg, String element) {
        if (!INTEGER_PATTERN.matcher(element).matches()) {
            msg.addFileParseError(new FileParseError(1, String.format("[Line %d, column %d] \"%s\" wasn't recognized as proper integer format", i, j, element)));
        }
    }

}