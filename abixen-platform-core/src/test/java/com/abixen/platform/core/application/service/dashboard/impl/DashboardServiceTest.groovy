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

package com.abixen.platform.core.application.service.dashboard.impl

import com.abixen.platform.core.application.converter.ModuleTypeToModuleTypeDtoConverter
import com.abixen.platform.core.application.converter.PageToPageDtoConverter
import com.abixen.platform.core.application.dto.DashboardDto
import com.abixen.platform.core.application.dto.LayoutDto
import com.abixen.platform.core.application.dto.ModuleTypeDto
import com.abixen.platform.core.application.dto.PageDto
import com.abixen.platform.core.application.form.DashboardForm
import com.abixen.platform.core.application.service.dashboard.DashboardService
import com.abixen.platform.core.domain.model.Layout
import com.abixen.platform.core.domain.model.Module
import com.abixen.platform.core.domain.model.ModuleType
import com.abixen.platform.core.domain.model.Page
import com.abixen.platform.core.domain.model.PageBuilder
import com.abixen.platform.core.domain.service.LayoutService
import com.abixen.platform.core.domain.service.PageService
import spock.lang.Specification

class DashboardServiceTest extends Specification {

    private PageService pageService
    private LayoutService layoutService
    private DashboardModuleService dashboardModuleService
    private PageToPageDtoConverter pageToPageDtoConverter
    private ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter
    private DashboardService dashboardService

    void setup() {
        pageService = Mock()
        layoutService = Mock()
        dashboardModuleService = Mock()
        pageToPageDtoConverter = Mock()
        moduleTypeToModuleTypeDtoConverter = Mock()
        dashboardService = new DashboardServiceImpl(
                pageService,
                layoutService,
                dashboardModuleService,
                pageToPageDtoConverter,
                moduleTypeToModuleTypeDtoConverter
        )
    }


    void "should find DashboardDto by page id"() {
        given:
        final List<Module> modules = new ArrayList<>()
        final ModuleType moduleType0 = [id: 1L] as ModuleType
        final ModuleType moduleType1 = [id: 2L] as ModuleType
        modules.add([id: 1L, moduleType: moduleType0] as Module)
        modules.add([id: 2L, moduleType: moduleType1] as Module)

        final ModuleTypeDto moduleTypeDto0 = new ModuleTypeDto()
        moduleTypeDto0.id = moduleType0.id
        final ModuleTypeDto moduleTypeDto1 = new ModuleTypeDto()
        moduleTypeDto1.id = moduleType1.id

        final Long pageId = 1L
        final Page page = [id: pageId] as Page
        final PageDto pageDto = new PageDto()
        pageDto.id = pageId

        pageService.find(pageId) >> page
        dashboardModuleService.findAllModules(page) >> modules
        moduleTypeToModuleTypeDtoConverter.convert(moduleType0) >> moduleTypeDto0
        moduleTypeToModuleTypeDtoConverter.convert(moduleType1) >> moduleTypeDto1
        pageToPageDtoConverter.convert(page) >> pageDto

        when:
        final DashboardDto dashboardDto = dashboardService.find(pageId)

        then:
        dashboardDto != null
        dashboardDto.page == pageDto
        dashboardDto.dashboardModuleDtos.size() == 2
        dashboardDto.dashboardModuleDtos[0].id == modules[0].id
        dashboardDto.dashboardModuleDtos[0].moduleType == moduleTypeDto0
        dashboardDto.dashboardModuleDtos[1].id == modules[1].id
        dashboardDto.dashboardModuleDtos[1].moduleType == moduleTypeDto1

        1 * pageService.find(pageId) >> page
        1 * dashboardModuleService.findAllModules(page) >> modules
        1 * moduleTypeToModuleTypeDtoConverter.convert(moduleType0) >> moduleTypeDto0
        1 * moduleTypeToModuleTypeDtoConverter.convert(moduleType1) >> moduleTypeDto1
        1 * pageToPageDtoConverter.convert(page) >> pageDto
        0 * _
    }

    void "should create dashboard"() {
        given:
        final Long layoutId = 1L
        final Layout layout = [id: layoutId] as Layout
        final Page page = new PageBuilder()
                .layout(layout)
                .title("title")
                .description("description")
                .icon("icon")
                .build();

        final PageDto pageDto = new PageDto()
        final LayoutDto layoutDto = new LayoutDto()
        layoutDto.id = layoutId
        pageDto.layout = layoutDto
        final DashboardForm dashboardForm = new DashboardForm(pageDto)

        pageService.create(page) >> page
        pageToPageDtoConverter.convert(page) >> pageDto
        layoutService.find(layoutId) >> layout

        when:
        final DashboardForm createdDashboardForm = dashboardService.create(dashboardForm)

        then:
        createdDashboardForm != null
        createdDashboardForm.page == dashboardForm.page
        createdDashboardForm.dashboardModuleDtos.size() == 0

        1 * pageService.create(page) >> page
        1 * pageToPageDtoConverter.convert(page) >> pageDto
        1 * layoutService.find(layoutId) >> layout
        0 * _
    }

}