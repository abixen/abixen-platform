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

import com.abixen.platform.core.service.DomainBuilderService;
import com.abixen.platform.core.util.*;
import com.abixen.platform.core.util.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DomainBuilderServiceImpl implements DomainBuilderService {

    @Override
    public UserBuilder newUserBuilderInstance() {
        log.debug("newUserBuilderInstance()");
        return new UserBuilderImpl();
    }

    @Override
    public RoleBuilder newRoleBuilderInstance() {
        log.debug("newRoleBuilderInstance()");
        return new RoleBuilderImpl();
    }

    @Override
    public ModuleBuilder newModuleBuilderInstance() {
        log.debug("newModuleBuilderInstance()");
        return new ModuleBuilderImpl();
    }

    @Override
    public ModuleTypeBuilder newModuleTypeBuilderInstance() {
        log.debug("newModuleTypeBuilderInstance()");
        return new ModuleTypeBuilderImpl();
    }

    @Override
    public PageBuilder newPageBuilderInstance() {
        log.debug("newPageBuilderInstance()");
        return new PageBuilderImpl();
    }
}