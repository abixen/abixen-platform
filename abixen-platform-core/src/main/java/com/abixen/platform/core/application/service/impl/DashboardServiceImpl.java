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

package com.abixen.platform.core.application.service.impl;

import com.abixen.platform.core.application.dto.DashboardModuleDto;
import com.abixen.platform.core.application.form.PageConfigurationForm;
import com.abixen.platform.core.application.service.DashboardService;
import com.abixen.platform.core.application.service.LayoutService;
import com.abixen.platform.core.application.service.ModuleService;
import com.abixen.platform.core.application.service.ModuleTypeService;
import com.abixen.platform.core.application.service.PageService;
import com.abixen.platform.core.domain.model.Module;
import com.abixen.platform.core.domain.model.ModuleBuilder;
import com.abixen.platform.core.domain.model.ModuleType;
import com.abixen.platform.core.domain.model.Page;
import com.abixen.platform.core.infrastructure.exception.PlatformCoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Transactional
@Service
public class DashboardServiceImpl implements DashboardService {

    private final PageService pageService;
    private final ModuleService moduleService;
    private final ModuleTypeService moduleTypeService;
    private final LayoutService layoutService;

    @Autowired
    public DashboardServiceImpl(PageService pageService,
                                ModuleService moduleService,
                                ModuleTypeService moduleTypeService,
                                LayoutService layoutService) {
        this.pageService = pageService;
        this.moduleService = moduleService;
        this.moduleTypeService = moduleTypeService;
        this.layoutService = layoutService;
    }

    @Override
    public PageConfigurationForm update(PageConfigurationForm pageConfigurationForm) {
        return change(pageConfigurationForm, false);
    }

    @Override
    public PageConfigurationForm configure(PageConfigurationForm pageConfigurationForm) {
        return change(pageConfigurationForm, true);
    }

    PageConfigurationForm change(PageConfigurationForm pageConfigurationForm, boolean configurationChangeType) {
        List<Long> currentModulesIds = new ArrayList<>();

        Page page = pageService.find(pageConfigurationForm.getPage().getId());

        if (configurationChangeType) {
            validateConfiguration(pageConfigurationForm, page);
        }

        page.changeDescription(pageConfigurationForm.getPage().getDescription());
        page.changeTitle(pageConfigurationForm.getPage().getTitle());
        page.changeIcon(pageConfigurationForm.getPage().getIcon());
        page.changeLayout(layoutService.findLayout(pageConfigurationForm.getPage().getLayout().getId()));
        pageService.update(page);

        updateExistingModules(pageConfigurationForm.getDashboardModuleDtos(), currentModulesIds);
        createNonExistentModules(pageConfigurationForm.getDashboardModuleDtos(), pageConfigurationForm.getPage().getId(), currentModulesIds);

        moduleService.deleteAllExcept(page, currentModulesIds);

        return pageConfigurationForm;
    }

    void updateExistingModules(List<DashboardModuleDto> dashboardModules, List<Long> modulesIds) {
        dashboardModules
                .stream()
                .filter(dashboardModule -> dashboardModule.getId() != null)
                .forEach(dashboardModule -> {
                    Module module = moduleService.find(dashboardModule.getId());
                    module.changeDescription(dashboardModule.getDescription());
                    module.changeTitle(dashboardModule.getTitle());
                    module.changePositionIndexes(dashboardModule.getRowIndex(), dashboardModule.getColumnIndex(), dashboardModule.getOrderIndex());

                    moduleService.update(module);
                    modulesIds.add(module.getId());
                });
    }

    void createNonExistentModules(List<DashboardModuleDto> dashboardModuleDtos, Long pageId, List<Long> modulesIds) {
        dashboardModuleDtos
                .stream()
                .filter(dashboardModuleDto -> dashboardModuleDto.getId() == null)
                .forEach(dashboardModuleDto -> {
                            ModuleType moduleType = moduleTypeService.find(dashboardModuleDto.getModuleType().getId());

                            Module module = new ModuleBuilder()
                                    .positionIndexes(dashboardModuleDto.getRowIndex(), dashboardModuleDto.getColumnIndex(), dashboardModuleDto.getOrderIndex())
                                    .title(dashboardModuleDto.getTitle())
                                    .moduleType(moduleType)
                                    .page(pageService.find(pageId))
                                    .description((dashboardModuleDto.getDescription()))
                                    .build();

                            dashboardModuleDto.setId(moduleService.create(module).getId());
                            modulesIds.add(dashboardModuleDto.getId());
                        }
                );
    }

    void validateConfiguration(PageConfigurationForm pageConfigurationForm, Page page) {
        boolean validationFailed = false;

        if (page.getDescription() == null && pageConfigurationForm.getPage().getDescription() != null) {
            validationFailed = true;
        } else if (page.getDescription() != null && !page.getDescription().equals(pageConfigurationForm.getPage().getDescription())) {
            validationFailed = true;
        } else if (!page.getTitle().equals(pageConfigurationForm.getPage().getTitle())) {
            validationFailed = true;
        }

        if (validationFailed) {
            throw new PlatformCoreException("Can not modify page's parameters during configuration's update operation.");
        }
    }

}