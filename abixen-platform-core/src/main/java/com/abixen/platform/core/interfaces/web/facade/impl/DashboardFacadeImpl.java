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

package com.abixen.platform.core.interfaces.web.facade.impl;

import com.abixen.platform.core.application.dto.DashboardModuleDto;
import com.abixen.platform.core.application.dto.PageDto;
import com.abixen.platform.core.application.form.DashboardForm;
import com.abixen.platform.core.interfaces.web.facade.dto.DashboardDto;
import com.abixen.platform.core.application.service.DashboardService;
import com.abixen.platform.core.application.service.LayoutService;
import com.abixen.platform.core.application.service.ModuleService;
import com.abixen.platform.core.application.service.PageService;
import com.abixen.platform.core.domain.model.Module;
import com.abixen.platform.core.domain.model.Page;
import com.abixen.platform.core.interfaces.web.facade.DashboardFacade;
import com.abixen.platform.core.interfaces.web.facade.converter.ModuleTypeToModuleTypeDtoConverter;
import com.abixen.platform.core.interfaces.web.facade.converter.PageToPageDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Transactional
@Service
public class DashboardFacadeImpl implements DashboardFacade {

    private final PageService pageService;
    private final ModuleService moduleService;
    private final DashboardService dashboardService;
    private final LayoutService layoutService;
    private final PageToPageDtoConverter pageToPageDtoConverter;
    private final ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter;


    @Autowired
    public DashboardFacadeImpl(PageService pageService,
                               ModuleService moduleService,
                               DashboardService dashboardService,
                               LayoutService layoutService,
                               PageToPageDtoConverter pageToPageDtoConverter,
                               ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter) {
        this.pageService = pageService;
        this.moduleService = moduleService;
        this.dashboardService = dashboardService;
        this.layoutService = layoutService;
        this.pageToPageDtoConverter = pageToPageDtoConverter;
        this.moduleTypeToModuleTypeDtoConverter = moduleTypeToModuleTypeDtoConverter;
    }

    @Override
    public DashboardDto find(Long pageId) {
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

    @Override
    public DashboardForm create(DashboardForm dashboardForm) {
        Page page = pageService.create(dashboardForm);
        layoutService.convertPageLayoutToJson(page);
        PageDto pageDto = pageToPageDtoConverter.convert(page);

        return new DashboardForm(pageDto);
    }

    @Override
    public DashboardForm update(DashboardForm dashboardForm) {
        return dashboardService.update(dashboardForm);
    }

    @Override
    public DashboardForm configure(DashboardForm dashboardForm) {
        return dashboardService.configure(dashboardForm);
    }

}