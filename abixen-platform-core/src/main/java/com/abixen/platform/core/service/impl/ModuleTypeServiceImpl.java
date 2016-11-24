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

import com.abixen.platform.core.client.ModulesConfigurationProperties;
import com.abixen.platform.core.integration.ModuleConfigurationIntegrationClient;
import com.abixen.platform.core.model.impl.ModuleType;
import com.abixen.platform.core.model.impl.Resource;
import com.abixen.platform.core.repository.ModuleTypeRepository;
import com.abixen.platform.core.service.ModuleTypeService;
import com.abixen.platform.core.service.ResourceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Transactional
@Service
public class ModuleTypeServiceImpl implements ModuleTypeService {

    private final Logger log = Logger.getLogger(ModuleTypeServiceImpl.class.getName());

    private final ModuleTypeRepository moduleTypeRepository;

    private final ModuleConfigurationIntegrationClient moduleConfigurationIntegrationClient;

    private final ResourceService resourceService;

    @Autowired
    public ModuleTypeServiceImpl(ModuleTypeRepository moduleTypeRepository,
                                 ModuleConfigurationIntegrationClient moduleConfigurationIntegrationClient,
                                 ResourceService resourceService) {
        this.moduleTypeRepository = moduleTypeRepository;
        this.moduleConfigurationIntegrationClient = moduleConfigurationIntegrationClient;
        this.resourceService = resourceService;
    }

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

    @Override
    public void reload(Long id) {
        log.debug("reload() - id: " + id);

        ModuleType moduleType = moduleTypeRepository.findOne(id);

        String serviceId = "abixen-platform-modules";

        ModulesConfigurationProperties modulesConfigurationProperties = moduleConfigurationIntegrationClient.getModulesConfigurationProperties(serviceId);

        ModulesConfigurationProperties.Module module = modulesConfigurationProperties.
                getModules().
                stream().
                filter(m -> moduleType.getName().equals(m.getName())).
                findAny().get();

        List<Resource> newResources = new ArrayList<>();

        module.getStaticResources().forEach(staticResource -> {
            Resource resource = new Resource();

            resource.setModuleType(moduleType);
            resource.setRelativeUrl(staticResource.getRelativeUrl());
            resource.setResourceLocation(staticResource.getResourceLocation());
            resource.setResourceType(staticResource.getResourceType());

            newResources.add(resource);
        });

        resourceService.updateResource(moduleType, newResources);
    }
}
