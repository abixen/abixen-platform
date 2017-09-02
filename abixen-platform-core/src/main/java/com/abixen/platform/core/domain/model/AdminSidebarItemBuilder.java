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


public class AdminSidebarItemBuilder extends EntityBuilder<AdminSidebarItem> {

    @Override
    protected void initProduct() {
        this.product = new AdminSidebarItem();
    }

    @Override
    protected AdminSidebarItem assembleProduct() {
        return this.product;
    }

    public AdminSidebarItemBuilder title(String title) {
        this.product.setTitle(title);
        return this;
    }

    public AdminSidebarItemBuilder angularJsState(String angularJsState) {
        this.product.setAngularJsState(angularJsState);
        return this;
    }

    public AdminSidebarItemBuilder orderIndex(Double orderIndex) {
        this.product.setOrderIndex(orderIndex);
        return this;
    }

    public AdminSidebarItemBuilder iconClass(String iconClass) {
        this.product.setIconClass(iconClass);
        return this;
    }
}