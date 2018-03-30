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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.form;

import com.abixen.platform.common.application.form.Form;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.FileDataSourceDto;

public class FileDataSourceForm extends DataSourceForm implements Form {

    private DataFileDto dataFile;

    public FileDataSourceForm() {
        super();
    }

    public FileDataSourceForm(FileDataSourceDto fileDataSourceDto) {
        super(fileDataSourceDto);
        this.dataFile = fileDataSourceDto.getDataFile();
    }

    public DataFileDto getDataFile() {
        return dataFile;
    }

    public void setDataFile(DataFileDto dataFile) {
        this.dataFile = dataFile;
    }
}
