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

package com.abixen.platform.service.businessintelligence.multivisualisation.service.impl.parser.reader.impl;


import com.abixen.platform.service.businessintelligence.multivisualisation.message.FileParseError;
import com.abixen.platform.service.businessintelligence.multivisualisation.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.util.dataFile.ColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.util.dataFile.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.util.dataFile.RowDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.impl.parser.reader.CsvReaderService;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CsvReaderServiceImpl implements CsvReaderService {

    private static final char DATA_SEPARATOR = ';';
    private static final Integer HEADER_COLUMN_INDEX = 0;

    @Override
    public DataFileDto read(final MultipartFile multipartFile, final Boolean readFirstColumnAsColumnName, final FileParserMessage msg) {
        try {
            final CSVReader csvReader = new CSVReader(new InputStreamReader(multipartFile.getInputStream()), DATA_SEPARATOR);
            return readFile(csvReader, readFirstColumnAsColumnName);
        } catch (IOException e) {
            msg.addFileParseError(new FileParseError("Can't read file."));
        }
        return null;
    }

    private DataFileDto readFile(final CSVReader csvReader, final Boolean readFirstColumnAsColumnName) throws IOException {
        final List<String[]> allDataInFile = csvReader.readAll();
        final Integer typeColumnIndex = readFirstColumnAsColumnName ? HEADER_COLUMN_INDEX + 1 : 0;

        final DataFileDto dataFileDto = new DataFileDto();
        for (int i = 0; i < allDataInFile.size(); i++) {
            if (i == typeColumnIndex) {
                dataFileDto.setRowTypes(getLineAsRowInMemory(allDataInFile.get(i)));
            } else {
                dataFileDto.addRow(getLineAsRowInMemory(allDataInFile.get(i)));
            }
        }
        return dataFileDto;
    }

    private RowDto getLineAsRowInMemory(final String[] strings) {
        final RowDto rowDto = new RowDto();
        for (String cell : strings) {
            rowDto.addColumn(new ColumnDto(cell));
        }
        return rowDto;
    }
}
