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

package com.abixen.platform.service.businessintelligence.multivisualisation.form;

import com.abixen.platform.common.form.Form;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.file.FileDataSource;

public class FileDataSourceForm extends DataSourceForm implements Form {

    private DataFileForm dataFile;

    public FileDataSourceForm() {
    }

    public FileDataSourceForm(FileDataSource fileDataSource) {
        this.setId(fileDataSource.getId());
        this.setName(fileDataSource.getName());
        this.setDescription(fileDataSource.getDescription());
    }

    public DataFileForm getDataFile() {
        return dataFile;
    }

    public void setDataFile(DataFileForm dataFile) {
        this.dataFile = dataFile;
    }
}
