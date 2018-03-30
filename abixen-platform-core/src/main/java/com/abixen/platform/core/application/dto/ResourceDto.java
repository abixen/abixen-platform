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

package com.abixen.platform.core.application.dto;


import com.abixen.platform.common.domain.model.enumtype.ResourcePage;
import com.abixen.platform.common.domain.model.enumtype.ResourcePageLocation;
import com.abixen.platform.common.domain.model.enumtype.ResourceType;
import lombok.ToString;

@ToString
public class ResourceDto extends AuditingDto {

    private Long id;
    private String relativeUrl;
    private ResourcePageLocation resourcePageLocation;
    private ResourcePage resourcePage;
    private ResourceType resourceType;
    private ModuleTypeDto moduleType;

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

    public ModuleTypeDto getModuleType() {
        return moduleType;
    }

    public void setModuleType(ModuleTypeDto moduleType) {
        this.moduleType = moduleType;
    }
}