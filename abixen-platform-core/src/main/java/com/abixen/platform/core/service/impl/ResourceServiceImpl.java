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
import com.abixen.platform.core.repository.ResourceRepository;
import com.abixen.platform.core.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {

    @Resource
    private ResourceRepository resourceRepository;

    @Override
    public List<com.abixen.platform.core.model.impl.Resource> findAllResources() {
        return resourceRepository.findAll();
    }

    @Override
    public void updateResource(ModuleType moduleType, List<com.abixen.platform.core.model.impl.Resource> newResources) {

        resourceRepository.deleteResources(moduleType);

        newResources.forEach(resource -> {
            resourceRepository.save(resource);
        });
    }
}