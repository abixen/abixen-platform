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

package com.abixen.platform.core.service.impl;

import com.abixen.platform.core.exception.PlatformCoreException;
import com.abixen.platform.core.model.SecurableModel;
import com.abixen.platform.core.service.ModuleService;
import com.abixen.platform.core.service.PageService;
import com.abixen.platform.core.service.SecuribleModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class SecuribleModelServiceImpl implements SecuribleModelService {

    private final ModuleService moduleService;
    private final PageService pageService;

    @Autowired
    public SecuribleModelServiceImpl(ModuleService moduleService,
                                     PageService pageService) {
        this.moduleService = moduleService;
        this.pageService = pageService;

    }

    @Override
    public SecurableModel getSecuribleModel(Long securableObjectId, String domainCanonicalClassName) {
        SecurableModel securibleObject;

        log.debug("getSecuribleModel() - securableObjectId={}, domainCanonicalClassName={}", securableObjectId, domainCanonicalClassName);

        switch (domainCanonicalClassName) {
            case "com.abixen.platform.core.model.impl.Page":
                securibleObject = pageService.findPage(securableObjectId);
                break;
            case "com.abixen.platform.core.model.impl.Module":
            case "Module":
                securibleObject = moduleService.findModule(securableObjectId);
                break;
            default:
                throw new PlatformCoreException("Wrong domainCanonicalClassName value: " + domainCanonicalClassName);
        }

        return securibleObject;
    }

}