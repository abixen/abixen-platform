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

import com.abixen.platform.core.model.impl.AdminSidebarItem;
import com.abixen.platform.core.model.impl.ModuleType;
import com.abixen.platform.common.util.EntityBuilder;
import com.abixen.platform.core.util.ModuleTypeBuilder;

import java.util.List;


public class ModuleTypeBuilderImpl extends EntityBuilder<ModuleType> implements ModuleTypeBuilder {

    @Override
    protected void initProduct() {
        this.product = new ModuleType();
    }

    @Override
    protected ModuleType assembleProduct() {
        return this.product;
    }

    public ModuleTypeBuilder basic(String name, String title, String description) {
        this.product.setName(name);
        this.product.setTitle(title);
        this.product.setDescription(description);
        return this;
    }

    @Override
    public ModuleTypeBuilder angular(String angularJsNameApplication, String angularJsNameAdmin) {
        this.product.setAngularJsNameApplication(angularJsNameApplication);
        this.product.setAngularJsNameAdmin(angularJsNameAdmin);
        return this;
    }

    public ModuleTypeBuilder initUrl(String initUrl) {
        this.product.setInitUrl(initUrl);
        return this;
    }

    @Override
    public ModuleTypeBuilder serviceId(String serviceId) {
        this.product.setServiceId(serviceId);
        return this;
    }

    @Override
    public ModuleTypeBuilder adminSidebarItems(List<AdminSidebarItem> adminSidebarItems) {
        this.product.setAdminSidebarItems(adminSidebarItems);
        return this;
    }

}