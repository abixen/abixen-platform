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

package com.abixen.platform.service.businessintelligence.multivisualisation.dto;

import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DataOrientation;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DataValueType;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.file.FileDataSource;
import com.abixen.platform.common.util.ModelKeys;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


//TODO - to remove
public class DataSourceCsvParametersDTO implements Serializable {


    private static final long serialVersionUID = -7430477767491597772L;

    @NotNull
    private char separator = ModelKeys.COMMA;

    @NotNull
    private DataOrientation orientation = DataOrientation.Rows;

    @NotNull
    private FileDataSource fileDataSource;

    @NotNull
    private File csvFile;

    @NotNull
    @Size(min = ModelKeys.NAME_MIN_LENGTH, max = ModelKeys.NAME_MAX_LENGTH)
    private String name;

    @NotNull
    private List<DataValueType> valueTypes;


    @NotNull
    private Map<String, Integer> columns;

    public List<DataValueType> getValueTypes() {
        return valueTypes;
    }

    public void setValueTypes(List<DataValueType> valueTypes) {
        this.valueTypes = valueTypes;
    }

    public Map<String, Integer> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, Integer> columns) {
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(File csvFile) {
        this.csvFile = csvFile;
    }

    public char getSeparator() {
        return separator;
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    public DataOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(DataOrientation orientation) {
        this.orientation = orientation;
    }

    public FileDataSource getFileDataSource() {
        return fileDataSource;
    }

    public void setFileDataSource(FileDataSource fileDataSource) {
        this.fileDataSource = fileDataSource;
    }

    @Override
    public String toString() {
        return "DataSourceCsvParametersDTO{" +
                "separator=" + separator +
                ", orientation=" + orientation +
                ", fileDataSource=" + fileDataSource +
                ", csvFile=" + csvFile +
                ", name='" + name + '\'' +
                '}';
    }
}


