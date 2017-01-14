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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {

    @Resource
    private ResourceRepository resourceRepository;

    @Override
    public List<com.abixen.platform.core.model.impl.Resource> findAllUniqueResources() {
        List<com.abixen.platform.core.model.impl.Resource> resources = resourceRepository.findAll();
        return resources.stream().filter(distinctByKey(resource -> resource.getRelativeUrl())).collect(Collectors.toList());
    }

    @Override
    public void updateResource(ModuleType moduleType, List<com.abixen.platform.core.model.impl.Resource> newResources) {

        resourceRepository.deleteResources(moduleType);

        newResources.forEach(resource -> {
            resourceRepository.save(resource);
        });
    }

    @Override
    public Page<com.abixen.platform.core.model.impl.Resource> findAllResourcesForModule(Long moduleId, Pageable pageable) {
        return this.resourceRepository.findAllByModuleId(moduleId, pageable);
    }

    private <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


}
