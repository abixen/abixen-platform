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

import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.common.infrastructure.annotation.PlatformApplicationService;
import com.abixen.platform.common.infrastructure.security.PlatformUser;
import com.abixen.platform.core.application.converter.ModuleTypeToModuleTypeDtoConverter;
import com.abixen.platform.core.application.dto.ModuleTypeDto;
import com.abixen.platform.core.application.form.ModuleTypeSearchForm;
import com.abixen.platform.core.domain.model.AdminSidebarItem;
import com.abixen.platform.core.domain.model.ModuleType;
import com.abixen.platform.core.domain.model.Resource;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.domain.repository.ModuleTypeRepository;
import com.abixen.platform.core.domain.service.UserService;
import com.abixen.platform.core.infrastructure.configuration.properties.RegisteredModuleServicesConfigurationProperties;
import com.abixen.platform.core.infrastructure.integration.ModuleConfigurationIntegrationClient;
import com.abixen.platform.core.interfaces.client.ModulesConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@PlatformApplicationService
public class ModuleTypeService {

    private final ModuleTypeRepository moduleTypeRepository;
    private final ModuleConfigurationIntegrationClient moduleConfigurationIntegrationClient;
    private final ResourceService resourceService;
    private final RegisteredModuleServicesConfigurationProperties registeredModuleServicesConfigurationProperties;
    private final SecurityService securityService;
    private final UserService userService;
    private final ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter;

    @Autowired
    public ModuleTypeService(ModuleTypeRepository moduleTypeRepository,
                             ModuleConfigurationIntegrationClient moduleConfigurationIntegrationClient,
                             ResourceService resourceService,
                             RegisteredModuleServicesConfigurationProperties registeredModuleServicesConfigurationProperties,
                             SecurityService securityService,
                             UserService userService,
                             ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter) {
        this.moduleTypeRepository = moduleTypeRepository;
        this.moduleConfigurationIntegrationClient = moduleConfigurationIntegrationClient;
        this.resourceService = resourceService;
        this.registeredModuleServicesConfigurationProperties = registeredModuleServicesConfigurationProperties;
        this.securityService = securityService;
        this.userService = userService;
        this.moduleTypeToModuleTypeDtoConverter = moduleTypeToModuleTypeDtoConverter;
    }

    public ModuleType find(Long id) {
        log.debug("find() - id: " + id);
        return moduleTypeRepository.findOne(id);
    }

    public List<ModuleTypeDto> findAll() {
        log.debug("findAll()");

        PlatformUser platformAuthorizedUser = securityService.getAuthorizedUser();
        User authorizedUser = userService.find(platformAuthorizedUser.getId());

        List<ModuleType> securityFilteredModuleTypes = moduleTypeRepository.findAllSecured(authorizedUser, PermissionName.MODULE_TYPE_VIEW);
        List<ModuleType> allModuleTypes = moduleTypeRepository.findAll();

        List<ModuleTypeDto> moduleTypeDtos = new ArrayList<>();

        allModuleTypes.forEach(moduleType -> {
            if (securityFilteredModuleTypes.contains(moduleType)) {
                ModuleTypeDto moduleTypeDto = moduleTypeToModuleTypeDtoConverter.convert(moduleType);
                moduleTypeDtos.add(moduleTypeDto);
            } else {
                ModuleTypeDto moduleTypeDto = new ModuleTypeDto();
                moduleTypeDto.setId(moduleType.getId());
                moduleTypeDto.setName(moduleType.getName());
                moduleTypeDtos.add(moduleTypeDto);
            }
        });

        return moduleTypeDtos;
    }

    public Page<ModuleType> findAll(Pageable pageable, ModuleTypeSearchForm moduleTypeSearchForm) {
        log.debug("findAll()");
        return moduleTypeRepository.findAll(pageable, moduleTypeSearchForm);
    }

    public List<ModuleType> findModuleTypes() {
        return moduleTypeRepository.findAll();
    }

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
        moduleType.changeAdminSidebarItems(generateAdminSidebarItem(modulesConfigurationProperties));
        moduleTypeRepository.save(moduleType);
    }

    public void reloadAll() {

        registeredModuleServicesConfigurationProperties.getRegisteredServices().forEach(service -> {
            log.info("service: " + service.getServiceId());

            ModulesConfigurationProperties modulesConfigurationProperties = moduleConfigurationIntegrationClient.getModulesConfigurationProperties(service.getServiceId());

            modulesConfigurationProperties.getModules().forEach(module -> {
                log.info("module: " + module.getName());
                ModuleType moduleType = moduleTypeRepository.find(module.getName());

                //TODO - add removing functionality
                if (moduleType == null) {
                    moduleType = ModuleType.builder()
                            .basic(module.getName(), module.getTitle(), module.getDescription())
                            .angular(module.getAngularJsNameApplication(), module.getAngularJsNameAdmin())
                            .initUrl(module.getRelativeInitUrl())
                            .serviceId(service.getServiceId())
                            .adminSidebarItems(generateAdminSidebarItem(modulesConfigurationProperties)).build();
                    moduleTypeRepository.save(moduleType);
                } else {
                    moduleType.changeDescription(module.getDescription());
                    moduleType.changeTitle(module.getTitle());
                    moduleType.changeInitUrl(module.getRelativeInitUrl());
                    moduleType.changeAdminSidebarItems(generateAdminSidebarItem(modulesConfigurationProperties));
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
            Resource resource = Resource.builder()
                    .moduleType(moduleType)
                    .relativeUrl(staticResource.getRelativeUrl())
                    .pageLocation(staticResource.getResourcePageLocation())
                    .page(staticResource.getResourcePage())
                    .type(staticResource.getResourceType())
                    .build();

            newResources.add(resource);
        });

        return newResources;
    }

    private List<AdminSidebarItem> generateAdminSidebarItem(ModulesConfigurationProperties modulesConfigurationProperties) {
        List<AdminSidebarItem> newAdminSidebarItems = new ArrayList<>();

        modulesConfigurationProperties.getAdminSidebarItems().forEach(asi -> {
            AdminSidebarItem adminSidebarItem = AdminSidebarItem.builder()
                    .title(asi.getTitle())
                    .angularJsState(asi.getAngularJsState())
                    .iconClass(asi.getIconClass())
                    .orderIndex(asi.getOrderIndex())
                    .build();

            newAdminSidebarItems.add(adminSidebarItem);
        });

        return newAdminSidebarItems;
    }
}