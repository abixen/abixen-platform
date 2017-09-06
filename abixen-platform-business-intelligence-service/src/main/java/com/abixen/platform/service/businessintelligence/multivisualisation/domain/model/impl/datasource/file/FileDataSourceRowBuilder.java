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

import com.abixen.platform.common.domain.model.EntityBuilder;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource;

public class FileDataSourceRowBuilder extends EntityBuilder<FileDataSourceRow> {

    public FileDataSourceRowBuilder rowNumeber(final Integer rowNumber) {
        this.product.setRowNumber(rowNumber);
        return this;
    }

    public FileDataSourceRowBuilder fileDataSource(final DataSource fileDataSource) {
        this.product.setFileDataSource(fileDataSource);
        return this;
    }

    @Override
    public FileDataSourceRow build() {
        return super.build();
    }

    @Override
    protected void initProduct() {
        this.product = new FileDataSourceRow();
    }

    @Override
    protected FileDataSourceRow assembleProduct() {
        return super.assembleProduct();
    }
}
