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

package com.abixen.platform.core.application.service.dashboard;

import com.abixen.platform.common.infrastructure.annotation.PlatformApplicationService;
import com.abixen.platform.core.application.dto.DashboardModuleDto;
import com.abixen.platform.core.application.service.ModuleTypeService;
import com.abixen.platform.core.domain.model.Module;
import com.abixen.platform.core.domain.model.ModuleType;
import com.abixen.platform.core.domain.model.Page;
import com.abixen.platform.core.domain.service.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Transactional
@PlatformApplicationService
class DashboardModuleService {

    private final ModuleService moduleService;
    private final ModuleTypeService moduleTypeService;


    @Autowired
    DashboardModuleService(ModuleService moduleService,
                           ModuleTypeService moduleTypeService) {
        this.moduleService = moduleService;
        this.moduleTypeService = moduleTypeService;
    }

    List<Module> findAllModules(final Page page) {
        log.debug("findAllModules() - page: {}", page);

        return moduleService.findAll(page);
    }

    List<Long> updateExistingModules(final List<DashboardModuleDto> dashboardModules) {
        return dashboardModules
                .stream()
                .filter(dashboardModule -> dashboardModule.getId() != null)
                .map(dashboardModule -> {
                    Module module = moduleService.find(dashboardModule.getId());
                    module.changeDescription(dashboardModule.getDescription());
                    module.changeTitle(dashboardModule.getTitle());
                    module.changePositionIndexes(dashboardModule.getRowIndex(), dashboardModule.getColumnIndex(), dashboardModule.getOrderIndex());

                    moduleService.update(module);

                    return module.getId();
                })
                .collect(Collectors.toList());
    }

    List<Long> createNotExistingModules(final List<DashboardModuleDto> dashboardModuleDtos, final Page page) {
        return dashboardModuleDtos
                .stream()
                .filter(dashboardModuleDto -> dashboardModuleDto.getId() == null)
                .map(dashboardModuleDto -> {
                    ModuleType moduleType = moduleTypeService.find(dashboardModuleDto.getModuleType().getId());

                    final Module module = Module.builder()
                            .positionIndexes(dashboardModuleDto.getRowIndex(), dashboardModuleDto.getColumnIndex(), dashboardModuleDto.getOrderIndex())
                            .title(dashboardModuleDto.getTitle())
                            .moduleType(moduleType)
                            .page(page)
                            .description((dashboardModuleDto.getDescription()))
                            .build();

                    dashboardModuleDto.setId(moduleService.create(module).getId());

                    return dashboardModuleDto.getId();
                })
                .collect(Collectors.toList());
    }

    void deleteAllModulesExcept(final Page page, final List<Long> ids) {
        log.debug("deleteAllModulesExcept() - page: {}, {}", page, ids);

        moduleService.deleteAllExcept(page, ids);
    }

}