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

import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.application.converter.ModuleTypeToModuleTypeDtoConverter;
import com.abixen.platform.core.application.converter.PageToPageDtoConverter;
import com.abixen.platform.core.application.dto.DashboardDto;
import com.abixen.platform.core.application.dto.DashboardModuleDto;
import com.abixen.platform.core.application.dto.PageDto;
import com.abixen.platform.core.application.form.DashboardForm;
import com.abixen.platform.core.application.service.DashboardService;
import com.abixen.platform.core.application.service.LayoutService;
import com.abixen.platform.core.application.service.ModuleTypeService;
import com.abixen.platform.core.domain.model.Module;
import com.abixen.platform.core.domain.model.ModuleBuilder;
import com.abixen.platform.core.domain.model.ModuleType;
import com.abixen.platform.core.domain.model.Page;
import com.abixen.platform.core.domain.model.PageBuilder;
import com.abixen.platform.core.domain.service.ModuleService;
import com.abixen.platform.core.domain.service.PageService;
import com.abixen.platform.core.infrastructure.exception.PlatformCoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final PageToPageDtoConverter pageToPageDtoConverter;
    private final ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter;


    @Autowired
    public DashboardServiceImpl(PageService pageService,
                                ModuleService moduleService,
                                ModuleTypeService moduleTypeService,
                                LayoutService layoutService,
                                PageToPageDtoConverter pageToPageDtoConverter,
                                ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter) {
        this.pageService = pageService;
        this.moduleService = moduleService;
        this.moduleTypeService = moduleTypeService;
        this.layoutService = layoutService;
        this.pageToPageDtoConverter = pageToPageDtoConverter;
        this.moduleTypeToModuleTypeDtoConverter = moduleTypeToModuleTypeDtoConverter;
    }

    @Override
    public DashboardDto find(final Long pageId) {
        log.debug("find() - pageId: {}", pageId);

        Page page = pageService.find(pageId);

        List<Module> modules = moduleService.findAll(page);
        List<DashboardModuleDto> dashboardModules = new ArrayList<>();

        modules
                .stream()
                .forEach(module ->
                        dashboardModules.add(new DashboardModuleDto(
                                module.getId(),
                                module.getDescription(),
                                module.getModuleType().getName(),
                                moduleTypeToModuleTypeDtoConverter.convert(module.getModuleType()),
                                module.getTitle(),
                                module.getRowIndex(),
                                module.getColumnIndex(),
                                module.getOrderIndex()
                        ))
                );

        layoutService.convertPageLayoutToJson(page);
        PageDto pageDto = pageToPageDtoConverter.convert(page);

        return new DashboardDto(pageDto, dashboardModules);
    }

    @PreAuthorize("hasPermission('" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_ADD + "')")
    @Override
    public DashboardForm create(final DashboardForm dashboardForm) {
        log.debug("create() - dashboardForm: {}", dashboardForm);

        final Page page = new PageBuilder()
                .layout(layoutService.findLayout(dashboardForm.getPage().getLayout().getId()))
                .title(dashboardForm.getPage().getTitle())
                .description(dashboardForm.getPage().getDescription())
                .icon(dashboardForm.getPage().getIcon())
                .build();

        final Page createdPage = pageService.create(page);
        layoutService.convertPageLayoutToJson(createdPage);

        final PageDto pageDto = pageToPageDtoConverter.convert(page);

        return new DashboardForm(pageDto);
    }

    @Override
    public DashboardForm update(final DashboardForm dashboardForm) {
        log.debug("update() - dashboardForm: {}", dashboardForm);

        return change(dashboardForm, false);
    }

    @Override
    public DashboardForm configure(final DashboardForm dashboardForm) {
        log.debug("configure() - dashboardForm: {}", dashboardForm);

        return change(dashboardForm, true);
    }

    private DashboardForm change(final DashboardForm dashboardForm, final boolean configurationChangeType) {
        List<Long> currentModulesIds = new ArrayList<>();

        Page page = pageService.find(dashboardForm.getPage().getId());

        if (configurationChangeType) {
            validateConfiguration(dashboardForm, page);
        }

        page.changeDescription(dashboardForm.getPage().getDescription());
        page.changeTitle(dashboardForm.getPage().getTitle());
        page.changeIcon(dashboardForm.getPage().getIcon());
        page.changeLayout(layoutService.findLayout(dashboardForm.getPage().getLayout().getId()));
        pageService.update(page);

        updateExistingModules(dashboardForm.getDashboardModuleDtos(), currentModulesIds);
        createNonExistentModules(dashboardForm.getDashboardModuleDtos(), dashboardForm.getPage().getId(), currentModulesIds);

        moduleService.deleteAllExcept(page, currentModulesIds);

        //FIXME - create a new instance
        return dashboardForm;
    }

    private void updateExistingModules(final List<DashboardModuleDto> dashboardModules, final List<Long> modulesIds) {
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

    private void createNonExistentModules(final List<DashboardModuleDto> dashboardModuleDtos, final Long pageId, final List<Long> modulesIds) {
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

    private void validateConfiguration(final DashboardForm dashboardForm, final Page page) {
        boolean validationFailed = false;

        if (page.getDescription() == null && dashboardForm.getPage().getDescription() != null) {
            validationFailed = true;
        } else if (page.getDescription() != null && !page.getDescription().equals(dashboardForm.getPage().getDescription())) {
            validationFailed = true;
        } else if (!page.getTitle().equals(dashboardForm.getPage().getTitle())) {
            validationFailed = true;
        }

        if (validationFailed) {
            throw new PlatformCoreException("Can not modify page's parameters during configuration's update operation.");
        }
    }

}