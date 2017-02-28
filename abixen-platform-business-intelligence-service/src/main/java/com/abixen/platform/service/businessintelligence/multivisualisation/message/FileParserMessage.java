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

package com.abixen.platform.service.businessintelligence.multivisualisation.message;

import java.util.ArrayList;
import java.util.List;

public class FileParserMessage<T> {
    private List<T> data = new ArrayList<T>();
    private List<FileParseError> fileParseErrors = new ArrayList<>();

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<FileParseError> getFileParseErrors() {
        return fileParseErrors;
    }

    public void setFileParseErrors(List<FileParseError> fileParseErrors) {
        this.fileParseErrors = fileParseErrors;
    }

    public void addFileParseError(FileParseError fileParseError) {
        this.fileParseErrors.add(fileParseError);
    }
}
