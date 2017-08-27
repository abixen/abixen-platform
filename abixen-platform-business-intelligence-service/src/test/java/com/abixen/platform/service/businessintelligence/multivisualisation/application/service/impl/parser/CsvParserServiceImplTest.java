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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service.impl.parser;

import com.abixen.platform.service.businessintelligence.infrastructure.configuration.PlatformModuleConfiguration;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFileColumn;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.MockitoAnnotations.initMocks;

@Service
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformModuleConfiguration.class)
public class CsvParserServiceImplTest {


    @Autowired
    CsvFileDataParserServiceImpl csvParserService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testThatServiceReturnValidListOfDataFileColumn() throws IOException{
        //given
        File testCSV = new File("src/test/resource/CsvCorrect.csv");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test.csv", Files.readAllBytes(Paths.get(testCSV.getPath())));
        //when
        FileParserMessage<DataFileColumn> result = csvParserService.parse(mockMultipartFile, true);
        //then
        assert result.getFileParseErrors().isEmpty() && !result.getData().isEmpty();
    }

    @Test
    public void testThatServiceReturnListOfErrorIfCsvNotContainColumnTypeForColumns() throws IOException{
        //given
        File testCSV = new File("src/test/resource/CsvWithoutColumnType.csv");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test.csv", Files.readAllBytes(Paths.get(testCSV.getPath())));
        //when
        FileParserMessage<DataFileColumn> result = csvParserService.parse(mockMultipartFile, true);
        //then
        assert !result.getFileParseErrors().isEmpty() && result.getData().isEmpty();
    }

    @Test
    public void testThatServiceReturnListOfErrorIfCsvNotContainColumnNameForColumns() throws IOException{
        //given
        File testCSV = new File("src/test/resource/CsvWithoutColumnName.csv");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test.csv", Files.readAllBytes(Paths.get(testCSV.getPath())));
        //when
        FileParserMessage<DataFileColumn> result = csvParserService.parse(mockMultipartFile, true);
        //then
        assert !result.getFileParseErrors().isEmpty() && result.getData().isEmpty();
    }

    @Test
    public void testThatServiceReturnListOfErrorIfCsvContainValueOfDiffrentTypeInColumn() throws IOException{
        //given
        File testCSV = new File("src/test/resource/CsvWithIncorrectValueInColumn.csv");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test.csv", Files.readAllBytes(Paths.get(testCSV.getPath())));
        //when
        FileParserMessage<DataFileColumn> result = csvParserService.parse(mockMultipartFile, true);
        //then
        assert !result.getFileParseErrors().isEmpty() && result.getData().isEmpty();
    }


}