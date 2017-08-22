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
import com.abixen.platform.service.businessintelligence.multivisualisation.model.util.dataFile.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.FileDataParserService;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.impl.parser.preparer.FileDataPreparer;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.impl.parser.reader.ExcelReaderService;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.impl.parser.validator.FileDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("excelParserService")
class ExcelFileParserServiceImpl implements FileDataParserService {

    private final ExcelReaderService reader;
    private final FileDataValidator validator;
    private final FileDataPreparer fileDataPreparer;

    @Autowired
    ExcelFileParserServiceImpl(ExcelReaderService reader,
                                      FileDataValidator validator,
                                      FileDataPreparer fileDataPreparer) {
        this.reader = reader;
        this.validator = validator;
        this.fileDataPreparer = fileDataPreparer;
    }

    public FileParserMessage<DataFileColumn> parseFile(final MultipartFile multipartFile, final Boolean readFirstColumnAsColumnName) {
        final FileParserMessage<DataFileColumn> msg = new FileParserMessage<>();
        final DataFileDto readedData = reader.read(multipartFile, readFirstColumnAsColumnName, msg);
        if (validator.valid(readedData, msg, readFirstColumnAsColumnName)) {
            msg.setData(fileDataPreparer.prepareData(readedData, msg, readFirstColumnAsColumnName));
        }
        return msg;
    }
}
