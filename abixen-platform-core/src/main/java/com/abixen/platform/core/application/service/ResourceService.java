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

import com.abixen.platform.common.infrastructure.annotation.PlatformApplicationService;
import com.abixen.platform.core.domain.model.ModuleType;
import com.abixen.platform.core.domain.repository.ResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@PlatformApplicationService
public class ResourceService {

    @Resource
    private ResourceRepository resourceRepository;

    public List<com.abixen.platform.core.domain.model.Resource> findAllResources() {
        return resourceRepository.findAll();
    }

    public void updateResource(ModuleType moduleType, List<com.abixen.platform.core.domain.model.Resource> newResources) {

        resourceRepository.deleteAll(moduleType);

        newResources.forEach(resource -> {
            resourceRepository.save(resource);
        });
    }

    public Page<com.abixen.platform.core.domain.model.Resource> findAllResources(Long moduleId, Pageable pageable) {
        return this.resourceRepository.findAll(moduleId, pageable);
    }

}