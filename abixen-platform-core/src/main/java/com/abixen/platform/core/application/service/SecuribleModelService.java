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

package com.abixen.platform.core.application.service;

import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import com.abixen.platform.common.infrastructure.annotation.PlatformApplicationService;
import com.abixen.platform.core.domain.model.SecurableModel;
import com.abixen.platform.core.domain.service.LayoutService;
import com.abixen.platform.core.domain.service.ModuleService;
import com.abixen.platform.core.domain.service.PageService;
import com.abixen.platform.core.infrastructure.exception.PlatformCoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@PlatformApplicationService
public class SecuribleModelService {

    private final ModuleService moduleService;
    private final PageService pageService;
    private final LayoutService layoutService;

    @Autowired
    public SecuribleModelService(ModuleService moduleService,
                                 PageService pageService,
                                 LayoutService layoutService) {
        this.moduleService = moduleService;
        this.pageService = pageService;
        this.layoutService = layoutService;
    }

    public SecurableModel getSecuribleModel(Long securableObjectId, AclClassName aclClassName) {
        SecurableModel securibleObject;

        log.debug("getSecuribleModel() - securableObjectId={}, aclClassName={}", securableObjectId, aclClassName);

        switch (aclClassName) {
            case PAGE:
                securibleObject = pageService.find(securableObjectId);
                break;
            case MODULE:
                securibleObject = moduleService.find(securableObjectId);
                break;
            case LAYOUT:
                securibleObject = layoutService.find(securableObjectId);
                break;
            default:
                throw new PlatformCoreException("Unsupported aclClassName value: " + aclClassName);
        }

        return securibleObject;
    }

}