/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.client.web.model;

import com.abixen.platform.common.domain.model.Model;
import com.abixen.platform.common.domain.model.enumtype.ResourcePage;
import com.abixen.platform.common.domain.model.enumtype.ResourcePageLocation;
import com.abixen.platform.common.domain.model.enumtype.ResourceType;


public class Resource extends Model {

    private Long id;
    private String relativeUrl;
    private ResourcePageLocation resourcePageLocation;
    private ResourcePage resourcePage;
    private ResourceType resourceType;
    private ModuleType moduleType;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelativeUrl() {
        return relativeUrl;
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    public ResourcePageLocation getResourcePageLocation() {
        return resourcePageLocation;
    }

    public void setResourcePageLocation(ResourcePageLocation resourcePageLocation) {
        this.resourcePageLocation = resourcePageLocation;
    }

    public ResourcePage getResourcePage() {
        return resourcePage;
    }

    public void setResourcePage(ResourcePage resourcePage) {
        this.resourcePage = resourcePage;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }

}
