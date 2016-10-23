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

import com.abixen.platform.core.model.impl.ModuleType;
import com.abixen.platform.core.repository.ModuleTypeRepository;
import com.abixen.platform.core.service.ModuleTypeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ModuleTypeServiceImpl implements ModuleTypeService {

    private final Logger log = Logger.getLogger(ModuleTypeServiceImpl.class.getName());

    @Resource
    private ModuleTypeRepository moduleTypeRepository;

    @Override
    public ModuleType findModuleType(Long id) {
        log.debug("findModuleType() - id: " + id);
        return moduleTypeRepository.findOne(id);
    }

    @Override
    public List<ModuleType> findAllModuleTypes() {
        log.debug("findAllModuleTypes()");
        return moduleTypeRepository.findAll();
    }
}
