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
import com.abixen.platform.core.configuration.properties.RegisteredModuleServicesConfigurationProperties;
import com.abixen.platform.core.converter.ModuleTypeToModuleTypeDtoConverter;
import com.abixen.platform.core.dto.ModuleTypeDto;
import com.abixen.platform.core.form.ModuleTypeSearchForm;
import com.abixen.platform.core.integration.ModuleConfigurationIntegrationClient;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.AdminSidebarItem;
import com.abixen.platform.core.model.impl.ModuleType;
import com.abixen.platform.core.model.impl.Resource;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.repository.ModuleTypeRepository;
import com.abixen.platform.common.security.PlatformUser;
import com.abixen.platform.core.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@Service
public class ModuleTypeServiceImpl implements ModuleTypeService {

    private final ModuleTypeRepository moduleTypeRepository;
    private final ModuleConfigurationIntegrationClient moduleConfigurationIntegrationClient;
    private final ResourceService resourceService;
    private final RegisteredModuleServicesConfigurationProperties registeredModuleServicesConfigurationProperties;
    private final DomainBuilderService domainBuilderService;
    private final SecurityService securityService;
    private final UserService userService;
    private final ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter;

    @Autowired
    public ModuleTypeServiceImpl(ModuleTypeRepository moduleTypeRepository,
                                 ModuleConfigurationIntegrationClient moduleConfigurationIntegrationClient,
                                 ResourceService resourceService,
                                 RegisteredModuleServicesConfigurationProperties registeredModuleServicesConfigurationProperties,
                                 DomainBuilderService domainBuilderService,
                                 SecurityService securityService,
                                 UserService userService,
                                 ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter) {
        this.moduleTypeRepository = moduleTypeRepository;
        this.moduleConfigurationIntegrationClient = moduleConfigurationIntegrationClient;
        this.resourceService = resourceService;
        this.registeredModuleServicesConfigurationProperties = registeredModuleServicesConfigurationProperties;
        this.domainBuilderService = domainBuilderService;
        this.securityService = securityService;
        this.userService = userService;
        this.moduleTypeToModuleTypeDtoConverter = moduleTypeToModuleTypeDtoConverter;
    }

    @Override
    public ModuleType findModuleType(Long id) {
        log.debug("findModuleType() - id: " + id);
        return moduleTypeRepository.findOne(id);
    }

    @Override
    public List<ModuleTypeDto> findAllModuleTypes() {
        log.debug("findAllModuleTypes()");

        PlatformUser platformAuthorizedUser = securityService.getAuthorizedUser();
        User authorizedUser = userService.findUser(platformAuthorizedUser.getId());

        List<ModuleType> securityFilteredModuleTypes = moduleTypeRepository.findAllSecured(authorizedUser, PermissionName.MODULE_TYPE_VIEW);
        List<ModuleType> allModuleTypes = moduleTypeRepository.findAll();

        List<ModuleTypeDto> moduleTypeDtos = new ArrayList<>();

        allModuleTypes.forEach(moduleType -> {
            if (securityFilteredModuleTypes.contains(moduleType)) {
                ModuleTypeDto moduleTypeDto = moduleTypeToModuleTypeDtoConverter.convert(moduleType);
                moduleTypeDtos.add(moduleTypeDto);
            } else {
                ModuleTypeDto moduleTypeDto = new ModuleTypeDto();
                moduleType.setId(moduleType.getId());
                moduleType.setName(moduleType.getName());
                moduleTypeDtos.add(moduleTypeDto);
            }
        });

        return moduleTypeDtos;
    }

    @Override
    public Page<ModuleType> findModuleTypes(Pageable pageable, ModuleTypeSearchForm moduleTypeSearchForm) {
        log.debug("findAllModuleTypes()");
        return moduleTypeRepository.findAll(pageable, moduleTypeSearchForm);
    }

    @Override
    public List<ModuleType> findModuleTypes() {
        return moduleTypeRepository.findAll();
    }

    @Override
    public void reload(Long id) {
        log.debug("reload() - id: " + id);

        ModuleType moduleType = moduleTypeRepository.findOne(id);

        ModulesConfigurationProperties modulesConfigurationProperties = moduleConfigurationIntegrationClient.getModulesConfigurationProperties(moduleType.getServiceId());

        ModulesConfigurationProperties.Module module = modulesConfigurationProperties.
                getModules().
                stream().
                filter(m -> moduleType.getName().equals(m.getName())).
                findAny().get();

        List<Resource> newResources = generateResources(moduleType, module);
        resourceService.updateResource(moduleType, newResources);
        moduleType.setAdminSidebarItems(generateAdminSidebarItem(modulesConfigurationProperties));
        moduleTypeRepository.save(moduleType);
    }

    @Override
    public void reloadAll() {

        registeredModuleServicesConfigurationProperties.getRegisteredServices().forEach(service -> {
            log.info("service: " + service.getServiceId());

            ModulesConfigurationProperties modulesConfigurationProperties = moduleConfigurationIntegrationClient.getModulesConfigurationProperties(service.getServiceId());

            modulesConfigurationProperties.getModules().forEach(module -> {
                log.info("module: " + module.getName());
                ModuleType moduleType = moduleTypeRepository.findByName(module.getName());

                //TODO - add removing functionality
                if (moduleType == null) {
                    moduleType = domainBuilderService.
                            newModuleTypeBuilderInstance().
                            basic(module.getName(), module.getTitle(), module.getDescription()).
                            angular(module.getAngularJsNameApplication(), module.getAngularJsNameAdmin()).
                            initUrl(module.getRelativeInitUrl()).
                            serviceId(service.getServiceId()).
                            adminSidebarItems(generateAdminSidebarItem(modulesConfigurationProperties)).build();
                    moduleTypeRepository.save(moduleType);
                } else {
                    moduleType.setDescription(module.getDescription());
                    moduleType.setTitle(module.getTitle());
                    moduleType.setInitUrl(module.getRelativeInitUrl());
                    moduleType.setAdminSidebarItems(generateAdminSidebarItem(modulesConfigurationProperties));
                    moduleTypeRepository.save(moduleType);
                }

                List<Resource> newResources = generateResources(moduleType, module);
                resourceService.updateResource(moduleType, newResources);
            });
        });
    }

    private List<Resource> generateResources(ModuleType moduleType, ModulesConfigurationProperties.Module module) {
        log.info("generateResources() - moduleType.getName(): " + moduleType.getName());
        List<Resource> newResources = new ArrayList<>();

        module.getStaticResources().forEach(staticResource -> {
            Resource resource = new Resource();

            resource.setModuleType(moduleType);
            resource.setRelativeUrl(staticResource.getRelativeUrl());
            resource.setResourcePageLocation(staticResource.getResourcePageLocation());
            resource.setResourcePage(staticResource.getResourcePage());
            resource.setResourceType(staticResource.getResourceType());

            newResources.add(resource);
        });

        return newResources;
    }

    private List<AdminSidebarItem> generateAdminSidebarItem(ModulesConfigurationProperties modulesConfigurationProperties) {
        List<AdminSidebarItem> newAdminSidebarItems = new ArrayList<>();

        modulesConfigurationProperties.getAdminSidebarItems().forEach(asi -> {
            AdminSidebarItem adminSidebarItem = new AdminSidebarItem();

            adminSidebarItem.setTitle(asi.getTitle());
            adminSidebarItem.setAngularJsState(asi.getAngularJsState());
            adminSidebarItem.setIconClass(asi.getIconClass());
            adminSidebarItem.setOrderIndex(asi.getOrderIndex());

            newAdminSidebarItems.add(adminSidebarItem);
        });

        return newAdminSidebarItems;
    }
}
