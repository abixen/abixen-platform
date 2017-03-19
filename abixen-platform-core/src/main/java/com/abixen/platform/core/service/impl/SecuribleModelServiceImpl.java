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
import com.abixen.platform.common.model.SecurableModel;
import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.core.repository.LayoutRepository;
import com.abixen.platform.core.repository.ModuleRepository;
import com.abixen.platform.core.repository.PageRepository;
import com.abixen.platform.core.service.SecuribleModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class SecuribleModelServiceImpl implements SecuribleModelService {

    private final ModuleRepository moduleRepository;
    private final PageRepository pageRepository;
    private final LayoutRepository layoutRepository;

    @Autowired
    public SecuribleModelServiceImpl(ModuleRepository moduleRepository,
                                     PageRepository pageRepository,
                                     LayoutRepository layoutRepository) {
        this.moduleRepository = moduleRepository;
        this.pageRepository = pageRepository;
        this.layoutRepository = layoutRepository;
    }

    @Override
    public SecurableModel getSecuribleModel(Long securableObjectId, AclClassName aclClassName) {
        SecurableModel securibleObject;

        log.debug("getSecuribleModel() - securableObjectId={}, aclClassName={}", securableObjectId, aclClassName);

        switch (aclClassName) {
            case PAGE:
                securibleObject = pageRepository.findOne(securableObjectId);
                break;
            case MODULE:
                securibleObject = moduleRepository.findOne(securableObjectId);
                break;
            case LAYOUT:
                securibleObject = layoutRepository.findOne(securableObjectId);
                break;
            default:
                throw new PlatformCoreException("Unsupported aclClassName value: " + aclClassName);
        }

        return securibleObject;
    }

}