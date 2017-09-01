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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.file;

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataFileRepository;

import java.util.Set;

public class FileDataSourceBuilder extends DataSourceBuilder {

    public FileDataSourceBuilder rows(final Set<FileDataSourceRow> rowa) {
        ((FileDataSource) this.product).setRows(rowa);
        return this;
    }

    public FileDataSourceBuilder dataFile(final Long dataFileId, final DataFileRepository repository) {
        ((FileDataSource) this.product).setDataFile(repository.findOne(dataFileId));
        return this;
    }

    @Override
    public FileDataSource build() {
        return (FileDataSource) super.build();
    }

    @Override
    public void initProduct() {
        this.product = new FileDataSource();
    }

    @Override
    public FileDataSource assembleProduct() {
        return (FileDataSource) super.assembleProduct();
    }
}
