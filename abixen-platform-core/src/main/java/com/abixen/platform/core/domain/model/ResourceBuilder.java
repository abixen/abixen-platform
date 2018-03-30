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


import com.abixen.platform.common.domain.model.enumtype.ResourcePage;
import com.abixen.platform.common.domain.model.enumtype.ResourcePageLocation;
import com.abixen.platform.common.domain.model.enumtype.ResourceType;
import com.abixen.platform.common.domain.model.EntityBuilder;


public class ResourceBuilder extends EntityBuilder<Resource> {

    @Override
    protected void initProduct() {
        this.product = new Resource();
    }

    @Override
    protected Resource assembleProduct() {
        return this.product;
    }

    public ResourceBuilder relativeUrl(String relativeUrl) {
        this.product.setRelativeUrl(relativeUrl);
        return this;
    }

    public ResourceBuilder pageLocation(ResourcePageLocation resourcePageLocation) {
        this.product.setResourcePageLocation(resourcePageLocation);
        return this;
    }

    public ResourceBuilder page(ResourcePage resourcePage) {
        this.product.setResourcePage(resourcePage);
        return this;
    }

    public ResourceBuilder type(ResourceType resourceType) {
        this.product.setResourceType(resourceType);
        return this;
    }

    public ResourceBuilder moduleType(ModuleType moduleType) {
        this.product.setModuleType(moduleType);
        return this;
    }
}