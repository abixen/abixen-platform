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

import com.abixen.platform.service.businessintelligence.multivisualisation.service.FileParserService;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.impl.FileParserFactory;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("fileParserFactoryImpl")
public class FileParserFactoryImpl implements FileParserFactory {

    private FileParserService csvFileParserService;
    private FileParserService excelFileParserService;

    @Autowired
    public FileParserFactoryImpl(@Qualifier("csvParserService") FileParserService csvFileParserService,
                                 @Qualifier("excelParserService") FileParserService excelFileParserService) {
        this.csvFileParserService = csvFileParserService;
        this.excelFileParserService = excelFileParserService;
    }

    @Override
    public FileParserService getParse(String extension) {
        switch (extension) {
            case ".csv":
                return csvFileParserService;
            case ".xls":
                return excelFileParserService;
            case ".xlsx":
                return excelFileParserService;
            default:
                throw new NotImplementedException("Parser for this file isn't implemented");
        }
    }
}
