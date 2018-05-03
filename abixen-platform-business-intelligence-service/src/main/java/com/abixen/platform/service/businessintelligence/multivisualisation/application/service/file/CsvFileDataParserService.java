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

import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.datafile.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.file.reader.CsvReaderService;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.file.validator.DataFileValidator;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.file.DataFileColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("csvParserService")
class CsvFileDataParserService implements DataFileParserService {

    private final CsvReaderService reader;
    private final DataFileValidator validator;
    private final DataFilePreparer fileDataPreparer;

    @Autowired
    CsvFileDataParserService(CsvReaderService reader,
                             DataFileValidator validator,
                             DataFilePreparer fileDataPreparer) {
        this.reader = reader;
        this.validator = validator;
        this.fileDataPreparer = fileDataPreparer;
    }

    @Override
    public FileParserMessage<DataFileColumn> parse(final MultipartFile multipartFile, final Boolean readFirstColumnAsColumnName) {
        final FileParserMessage<DataFileColumn> msg = new FileParserMessage<>();
        final DataFileDto readedData = reader.read(multipartFile, readFirstColumnAsColumnName, msg);
        if (validator.valid(readedData, msg, readFirstColumnAsColumnName)) {
            msg.setData(fileDataPreparer.prepareData(readedData, msg, readFirstColumnAsColumnName));
        }
        return msg;
    }
}
