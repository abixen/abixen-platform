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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class ResourceServiceImpl implements ResourceService {

    private final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

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

    private <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


}
