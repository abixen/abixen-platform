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

package com.abixen.platform.core.util.impl;

import com.abixen.platform.core.model.impl.Module;
import com.abixen.platform.core.model.impl.ModuleType;
import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.common.util.EntityBuilder;
import com.abixen.platform.core.util.ModuleBuilder;


public class ModuleBuilderImpl extends EntityBuilder<Module> implements ModuleBuilder {

    @Override
    protected void initProduct() {
        this.product = new Module();
    }

    @Override
    protected Module assembleProduct() {
        return this.product;
    }

    public ModuleBuilder positionIndexes(Integer rowIndex, Integer columnIndex, Integer orderIndex) {
        this.product.setRowIndex(rowIndex);
        this.product.setColumnIndex(columnIndex);
        this.product.setOrderIndex(orderIndex);
        return this;
    }

    public ModuleBuilder description(String description) {
        this.product.setDescription(description);
        return this;
    }

    @Override
    public ModuleBuilder moduleData(String title, ModuleType moduleType, Page page) {
        this.product.setTitle(title);
        this.product.setModuleType(moduleType);
        this.product.setPage(page);
        return this;
    }


}
