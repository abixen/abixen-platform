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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service.file.reader;


import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.datafile.ColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.datafile.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.datafile.RowDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.message.FileParseError;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFileColumn;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import static org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted;

@Service
public class ExcelReaderService {

    private static final int FIRST_SHEET_INDEX = 0;
    private static final String XLSX_FILE_EXTENSION = ".xlsx";
    private static final String XLS_FILE_EXTENSION = ".xls";

    public DataFileDto read(final MultipartFile multipartFile, final Boolean readFirstColumnAsColumnName, final FileParserMessage msg) {
        try {
            final Workbook workbook = openFileAsWorkBook(multipartFile, msg);
            return parseWorkbook(workbook.getSheetAt(FIRST_SHEET_INDEX), msg, readFirstColumnAsColumnName);
        } catch (IOException e) {
            msg.addFileParseError(new FileParseError("Can't read file."));
        }
        return null;
    }

    private Workbook openFileAsWorkBook(final MultipartFile file, final FileParserMessage msg) throws IOException {
        try {
            final String fileName = file.getOriginalFilename();
            if (XLSX_FILE_EXTENSION.equals(fileName.substring(fileName.lastIndexOf(".")))) {
                return new XSSFWorkbook(file.getInputStream());
            }
            if (XLS_FILE_EXTENSION.equals(fileName.substring(fileName.lastIndexOf(".")))) {
                return new HSSFWorkbook(file.getInputStream());
            }
            throw new NotOfficeXmlFileException("Not recognized type");
        } catch (NotOfficeXmlFileException e) {
            msg.addFileParseError(new FileParseError(0, "Invalid file type"));
            throw new IOException();
        }
    }

    private DataFileDto parseWorkbook(final Sheet sheet, final FileParserMessage<DataFileColumn> msg, final Boolean readFirstColumnAsColumnName) {
        final DataFileDto dataFileDto = new DataFileDto();
        dataFileDto.setRowTypes(buildRowTypes(sheet.getRow(readFirstColumnAsColumnName ? 1 : 0)));
        final Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            dataFileDto.addRow(readRowAsRowInMemory(rowIterator.next(), dataFileDto.getRowTypes()));
        }
        return dataFileDto;
    }

    private RowDto buildRowTypes(final Row row) {
        final RowDto rowDto = new RowDto();
        final Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            rowDto.addColumn(new ColumnDto(getTypeAsString(cellIterator.next())));
        }
        return rowDto;
    }

    private String getTypeAsString(final Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                return isCellDateFormatted(cell) ? "DATE" : "DOUBLE";
            case STRING:
                return "STRING";
            default:
                return null;
        }
    }

    private RowDto readRowAsRowInMemory(final Row row, final RowDto rowTypes) {
        final RowDto rowDto = new RowDto();
        final Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            rowDto.addColumn(new ColumnDto(formatIfData(cellIterator.next())));
        }
        return rowDto;
    }

    private String formatIfData(final Cell cell) {
        if (cell.getCellTypeEnum() == CellType.NUMERIC && isCellDateFormatted(cell)) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.format(cell.getDateCellValue());
        }
        return cell.toString();
    }

}