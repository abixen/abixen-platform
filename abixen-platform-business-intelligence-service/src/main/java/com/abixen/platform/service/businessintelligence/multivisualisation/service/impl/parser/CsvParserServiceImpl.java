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

package com.abixen.platform.service.businessintelligence.multivisualisation.service.impl.parser;


import com.abixen.platform.service.businessintelligence.multivisualisation.message.FileParseError;
import com.abixen.platform.service.businessintelligence.multivisualisation.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DataValueType;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.data.*;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFileColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.FileParserService;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service("csvParserService")
public class CsvParserServiceImpl implements FileParserService {
    private final char separator = ';';
    private final Integer headerColumnIndex = 0;
    private static final Pattern INTEGER_PATTERN = Pattern.compile("[0-9]*+");
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?[0-9]*[\\\\.\\\\,]?[0-9]+([eE][-+]?[0-9]+)?");
    private static final List<Pattern> DATE_PATTERN_LIST = Arrays.asList(
            Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$"),
            Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$"),
            Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$")
    );


    @Override
    public FileParserMessage<DataFileColumn> parseFile(MultipartFile multipartFile, Boolean readFirstColumnAsColumnName) {
        FileParserMessage<DataFileColumn> msg = new FileParserMessage<>();
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(multipartFile.getInputStream()), separator);
            parseFile(msg, csvReader, readFirstColumnAsColumnName);
        } catch (IOException e) {
            msg.addFileParseError(new FileParseError("Can't read file."));
        }
        return msg;
    }

    private void parseFile(FileParserMessage<DataFileColumn> msg, CSVReader csvReader, Boolean readFirstColumnAsColumnName) throws IOException {
        List<String[]> allDataInFile = csvReader.readAll();
        String[] header = null;
        Integer typeColumnIndex = readFirstColumnAsColumnName ? headerColumnIndex + 1 : 0;
        if (readFirstColumnAsColumnName) {
            header = getSeparatedValue(allDataInFile.get(headerColumnIndex));
        }
        String[] columnType = getSeparatedValue(allDataInFile.get(typeColumnIndex));
        List<String[]> data = allDataInFile.subList(typeColumnIndex + 1, allDataInFile.size());
        if (validate(msg, columnType, data)) {
            msg.setData(prepareData(msg, header, columnType, data));
        }
    }

    private String[] getSeparatedValue(String[] line) {
        return line;
    }

    private boolean validate(FileParserMessage<DataFileColumn> msg, String[] columnType, List<String[]> data) {
        List<DataValueType> columnTypes = getColumnTypeAsDataValueTypeList(msg, columnType);
        if (!msg.getFileParseErrors().isEmpty()) {
            return false;
        }
        for (int i = 0; i < data.size(); i++) {
            String[] lineAsList = data.get(i);
            for (int j = 0; j < columnTypes.size(); j++) {
                validateAs(msg, columnTypes, i, j, lineAsList[j].trim());
            }
        }
        return msg.getFileParseErrors().isEmpty();
    }

    private void validateAs(FileParserMessage<DataFileColumn> msg, List<DataValueType> columnTypes, int i, int j, String element) {
        switch (columnTypes.get(j)) {
            case DOUBLE:
                validateAsDouble(i, j, msg, element);
                break;
            case DATE:
                validateAsDate(i, j, msg, element);
                break;
            case INTEGER:
                validateAsInteger(i, j, msg, element);
                break;
            case STRING:
                validateAsString(i, j, msg, element);
                break;
            default: break;
        }
    }

    private List<DataValueType> getColumnTypeAsDataValueTypeList(FileParserMessage<DataFileColumn> msg, String[] columnType) {
        List<DataValueType> columnTypeAsDataValueTypeList = new ArrayList<>();
        for (String typeAsString : columnType) {
            validateColumnType(msg, columnTypeAsDataValueTypeList, typeAsString);
        }
        return columnTypeAsDataValueTypeList;
    }

    private void validateColumnType(FileParserMessage<DataFileColumn> msg, List<DataValueType> columnTypeAsDataValueTypeList, String typeAsString) {
        try {
            columnTypeAsDataValueTypeList.add(DataValueType.valueOf(typeAsString));
        } catch (IllegalArgumentException e) {
            msg.addFileParseError(new FileParseError(0, String.format("\"%s\" wasn't recognized as valid column type", typeAsString)));
        }
    }

    private void validateAsString(Integer i, Integer j, FileParserMessage<DataFileColumn> msg, String element) {
        if (element.equals("")) {
            msg.addFileParseError(new FileParseError(1, String.format("[Line %d, column %d] is empty", i, j)));
        }
    }

    private void validateAsDouble(Integer i, Integer j, FileParserMessage<DataFileColumn> msg, String element) {
        if (!DOUBLE_PATTERN.matcher(element).matches()) {
            msg.addFileParseError(new FileParseError(1, String.format("[Line %d, column %d] \"%s\" wasn't recognized as proper double format", i, j, element)));
        }
    }

    private void validateAsDate(Integer i, Integer j, FileParserMessage<DataFileColumn> msg, String element) {
        if (!DATE_PATTERN_LIST.stream().filter(pattern -> pattern.matcher(element).matches()).findAny().isPresent()) {
            msg.addFileParseError(new FileParseError(1, String.format("[Line %d, column %d] \"%s\" wasn't recognized as proper data format", i, j, element)));
        }
    }

    private void validateAsInteger(Integer i, Integer j, FileParserMessage<DataFileColumn> msg, String element) {
        if (!INTEGER_PATTERN.matcher(element).matches()) {
            msg.addFileParseError(new FileParseError(1, String.format("[Line %d, column %d] \"%s\" wasn't recognized as proper integer format", i, j, element)));
        }
    }

    private List<DataFileColumn> prepareData(FileParserMessage<DataFileColumn> msg, String[] header, String[] columnType, List<String[]> data) {
        List<DataValueType> columnTypes = getColumnTypeAsDataValueTypeList(msg, columnType);
        List<DataFileColumn> dataFileColumns = new ArrayList<>();
        for (int i = 0; i < columnTypes.size(); i++) {
            DataFileColumn dataFileColumn = new DataFileColumn();
            if (header != null) {
                dataFileColumn.setName(header[i]);
            }
            dataFileColumn.setDataValueType(columnTypes.get(i));
            List<DataValue> dataValues = new ArrayList<>();
            for (int j = 0; j < data.size(); j++) {
                String[] lineAsList = data.get(j);
                dataValues.add(parseAs(columnTypes.get(i), lineAsList[i].trim()));
            }
            dataFileColumn.setValues(dataValues);
            dataFileColumns.add(dataFileColumn);
        }
        return dataFileColumns;
    }

    private DataValue parseAs(DataValueType columnType, String element) {
        switch (columnType) {
            case DOUBLE: return parseAsDouble(element);
            case DATE: return parseAsDate(element);
            case INTEGER: return parseAsInteger(element);
            case STRING: return parseAsString(element);
            default: return null;
        }
    }

    private DataValue parseAsDate(String element) {
        DataValueDate dataValueDate = new DataValueDate();
        dataValueDate.setValue(new Date(element));
        return dataValueDate;
    }

    private DataValue parseAsString(String element) {
        DataValueString dataValueString = new DataValueString();
        dataValueString.setValue(element);
        return dataValueString;
    }

    private DataValueInteger parseAsInteger(String element) {
        DataValueInteger dataValueInteger = new DataValueInteger();
        dataValueInteger.setValue(Integer.valueOf(element));
        return dataValueInteger;
    }

    private DataValueDouble parseAsDouble(String element) {
        DataValueDouble dataValueDouble = new DataValueDouble();
        dataValueDouble.setValue(Double.valueOf(element.replace(",", ".")));
        return dataValueDouble;
    }
}
