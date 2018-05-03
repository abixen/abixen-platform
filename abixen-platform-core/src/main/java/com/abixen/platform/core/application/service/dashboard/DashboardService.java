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
import com.abixen.platform.core.application.converter.ModuleToDashboardModuleDtoConverter;
import com.abixen.platform.core.application.converter.PageToPageDtoConverter;
import com.abixen.platform.core.application.dto.DashboardDto;
import com.abixen.platform.core.application.dto.DashboardModuleDto;
import com.abixen.platform.core.application.dto.PageDto;
import com.abixen.platform.core.application.form.DashboardForm;
import com.abixen.platform.core.domain.model.Module;
import com.abixen.platform.core.domain.model.Page;
import com.abixen.platform.core.domain.service.LayoutService;
import com.abixen.platform.core.domain.service.PageService;
import com.abixen.platform.core.infrastructure.exception.PlatformCoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Transactional
@PlatformApplicationService
public class DashboardService {

    private final PageService pageService;
    private final LayoutService layoutService;
    private final DashboardModuleService dashboardModuleService;
    private final PageToPageDtoConverter pageToPageDtoConverter;
    private final ModuleToDashboardModuleDtoConverter moduleToDashboardModuleDtoConverter;


    @Autowired
    public DashboardService(PageService pageService,
                            LayoutService layoutService,
                            DashboardModuleService dashboardModuleService,
                            PageToPageDtoConverter pageToPageDtoConverter,
                            ModuleToDashboardModuleDtoConverter moduleToDashboardModuleDtoConverter) {
        this.pageService = pageService;
        this.layoutService = layoutService;
        this.dashboardModuleService = dashboardModuleService;
        this.pageToPageDtoConverter = pageToPageDtoConverter;
        this.moduleToDashboardModuleDtoConverter = moduleToDashboardModuleDtoConverter;
    }

    public DashboardDto find(final Long pageId) {
        log.debug("find() - pageId: {}", pageId);

        final Page page = pageService.find(pageId);
        final List<Module> modules = dashboardModuleService.findAllModules(page);

        final PageDto pageDto = pageToPageDtoConverter.convert(page);
        final List<DashboardModuleDto> dashboardModules = moduleToDashboardModuleDtoConverter.convertToList(modules);

        return new DashboardDto(pageDto, dashboardModules);
    }

    public DashboardForm create(final DashboardForm dashboardForm) {
        log.debug("create() - dashboardForm: {}", dashboardForm);

        final Page page = Page.builder()
                .layout(layoutService.find(dashboardForm.getPage().getLayout().getId()))
                .title(dashboardForm.getPage().getTitle())
                .description(dashboardForm.getPage().getDescription())
                .icon(dashboardForm.getPage().getIcon())
                .build();

        final Page createdPage = pageService.create(page);

        final PageDto pageDto = pageToPageDtoConverter.convert(createdPage);

        return new DashboardForm(pageDto);
    }

    public DashboardForm update(final DashboardForm dashboardForm) {
        log.debug("update() - dashboardForm: {}", dashboardForm);

        return change(dashboardForm, false);
    }

    public DashboardForm configure(final DashboardForm dashboardForm) {
        log.debug("configure() - dashboardForm: {}", dashboardForm);

        return change(dashboardForm, true);
    }

    private DashboardForm change(final DashboardForm dashboardForm, final boolean configurationChangeType) {
        final Page page = pageService.find(dashboardForm.getPage().getId());

        if (configurationChangeType) {
            validateConfiguration(dashboardForm, page);
        }

        page.changeDescription(dashboardForm.getPage().getDescription());
        page.changeTitle(dashboardForm.getPage().getTitle());
        page.changeIcon(dashboardForm.getPage().getIcon());
        page.changeLayout(layoutService.find(dashboardForm.getPage().getLayout().getId()));
        pageService.update(page);

        final List<Long> updatedModulesIds = dashboardModuleService.updateExistingModules(dashboardForm.getDashboardModuleDtos());
        final List<Long> createdModulesIds = dashboardModuleService.createNotExistingModules(dashboardForm.getDashboardModuleDtos(), page);

        final List<Long> currentModulesIds = new ArrayList<>();
        currentModulesIds.addAll(updatedModulesIds);
        currentModulesIds.addAll(createdModulesIds);

        dashboardModuleService.deleteAllModulesExcept(page, currentModulesIds);

        //FIXME - create a new instance
        return dashboardForm;
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