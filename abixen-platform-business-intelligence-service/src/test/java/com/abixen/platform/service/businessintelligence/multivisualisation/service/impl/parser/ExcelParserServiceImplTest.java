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

import com.abixen.platform.service.businessintelligence.multivisualisation.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFileColumn;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class ExcelParserServiceImplTest {

    @InjectMocks
    ExcelParserServiceImpl excelParserService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testThatParseFileReturnProperListOfDataValueWhenParseXLSX() throws IOException {
        File testCSV = new File("src/test/resource/excelCorrect.xlsx");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test.xlsx", "test.xlsx", (String) null, Files.readAllBytes(Paths.get(testCSV.getPath())));
        //when
        FileParserMessage<DataFileColumn> result = excelParserService.parseFile(mockMultipartFile);
        //then
        assert result.getFileParseErrors().isEmpty() && !result.getData().isEmpty();
    }

    @Test
    public void testThatParseFileReturnListOfErrorIfCellsInColumsHaveDiffrentTypeWhenParseXLSX() throws IOException {
        File testCSV = new File("src/test/resource/excelWithDiffrentTypeInColumn.xlsx");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test.xlsx", "test.xlsx", (String) null, Files.readAllBytes(Paths.get(testCSV.getPath())));
        //when
        FileParserMessage<DataFileColumn> result = excelParserService.parseFile(mockMultipartFile);
        //then
        assert !result.getFileParseErrors().isEmpty() && result.getData().isEmpty();
    }

    @Test
    public void testThatParseFileReturnProperListOfDataValueWhenParseXLS() throws IOException {
        File testCSV = new File("src/test/resource/excelCorrect.xls");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test.xlsx", "test.xls", (String) null, Files.readAllBytes(Paths.get(testCSV.getPath())));
        //when
        FileParserMessage<DataFileColumn> result = excelParserService.parseFile(mockMultipartFile);
        //then
        assert result.getFileParseErrors().isEmpty() && !result.getData().isEmpty();
    }

    @Test
    public void testThatParseFileReturnListOfErrorIfCellsInColumsHaveDiffrentTypeWhenParseXLS() throws IOException {
        File testCSV = new File("src/test/resource/excelWithDiffrentTypeInColumn.xls");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test.xlsx", "test.xls", (String) null, Files.readAllBytes(Paths.get(testCSV.getPath())));
        //when
        FileParserMessage<DataFileColumn> result = excelParserService.parseFile(mockMultipartFile);
        //then
        assert !result.getFileParseErrors().isEmpty() && result.getData().isEmpty();
    }
}