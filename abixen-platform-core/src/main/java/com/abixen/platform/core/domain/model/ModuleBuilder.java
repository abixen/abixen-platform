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

package com.abixen.platform.core.domain.model;

import com.abixen.platform.common.domain.model.utils.EntityBuilder;


public class ModuleBuilder extends EntityBuilder<Module> {

    @Override
    protected void initProduct() {
        this.product = new Module();
    }

    @Override
    protected Module assembleProduct() {
        return this.product;
    }

    public ModuleBuilder title(String title) {
        this.product.setTitle(title);
        return this;
    }

    public ModuleBuilder moduleType(ModuleType moduleType) {
        this.product.setModuleType(moduleType);
        return this;
    }

    public ModuleBuilder page(Page page) {
        this.product.setPage(page);
        return this;
    }

    public ModuleBuilder description(String description) {
        this.product.setDescription(description);
        return this;
    }

    public ModuleBuilder positionIndexes(Integer rowIndex, Integer columnIndex, Integer orderIndex) {
        this.product.setRowIndex(rowIndex);
        this.product.setColumnIndex(columnIndex);
        this.product.setOrderIndex(orderIndex);
        return this;
    }
}