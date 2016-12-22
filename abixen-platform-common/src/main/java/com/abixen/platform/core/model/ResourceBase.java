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

package com.abixen.platform.core.model;

import com.abixen.platform.core.model.enumtype.ResourcePageLocation;
import com.abixen.platform.core.model.enumtype.ResourcePage;
import com.abixen.platform.core.model.enumtype.ResourcePageLocation;
import com.abixen.platform.core.model.enumtype.ResourceType;


public interface ResourceBase<ModuleType extends ModuleTypeBase> {

    int RESOURCE_RELATIVE_URL_MIN_LENGTH = 3;
    int RESOURCE_RELATIVE_URL_MAX_LENGTH = 250;

    Long getId();

    void setId(Long id);

    String getRelativeUrl();

    void setRelativeUrl(String relativeUrl);

    ResourcePageLocation getResourcePageLocation();

    void setResourcePageLocation(ResourcePageLocation resourcePageLocation);

    ResourcePage getResourcePage();

    void setResourcePage(ResourcePage resourcePage);

    ResourceType getResourceType();

    void setResourceType(ResourceType resourceType);

    ModuleType getModuleType();

    void setModuleType(ModuleType module);
}
