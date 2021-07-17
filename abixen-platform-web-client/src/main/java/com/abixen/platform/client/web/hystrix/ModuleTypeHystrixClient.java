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

package com.abixen.platform.client.web.hystrix;

import com.abixen.platform.client.web.client.ModuleTypeClient;
import com.abixen.platform.client.web.model.ModuleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ModuleTypeHystrixClient {

    private final ModuleTypeClient moduleTypeClient;

    @Autowired
    public ModuleTypeHystrixClient(ModuleTypeClient moduleTypeClient) {
        this.moduleTypeClient = moduleTypeClient;
    }

    public List<ModuleType> getAllModuleTypes() {
        log.debug("getAllModuleTypes()");
        return moduleTypeClient.getAllModuleTypes();
    }
}