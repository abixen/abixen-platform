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

package com.abixen.platform.client.web.model;

import com.abixen.platform.core.model.Model;
import com.abixen.platform.core.model.ResourceBase;
import com.abixen.platform.core.model.enumtype.ResourceLocation;
import com.abixen.platform.core.model.enumtype.ResourceType;


public class Resource extends Model implements ResourceBase<ModuleType> {

    private Long id;
    private String relativeUrl;
    private ResourceLocation resourceLocation;
    private ResourceType resourceType;
    private ModuleType moduleType;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getRelativeUrl() {
        return relativeUrl;
    }

    @Override
    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    @Override
    public void setResourceLocation(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    @Override
    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public ModuleType getModuleType() {
        return moduleType;
    }

    @Override
    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }

}

