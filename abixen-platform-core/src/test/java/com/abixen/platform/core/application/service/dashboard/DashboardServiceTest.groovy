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

package com.abixen.platform.core.application.service.dashboard

import com.abixen.platform.core.application.converter.ModuleToDashboardModuleDtoConverter
import com.abixen.platform.core.application.converter.PageToPageDtoConverter
import com.abixen.platform.core.application.dto.DashboardDto
import com.abixen.platform.core.application.dto.DashboardModuleDto
import com.abixen.platform.core.application.dto.LayoutDto
import com.abixen.platform.core.application.dto.PageDto
import com.abixen.platform.core.application.form.DashboardForm
import com.abixen.platform.core.application.service.dashboard.DashboardModuleService
import com.abixen.platform.core.application.service.dashboard.DashboardService
import com.abixen.platform.core.domain.model.Layout
import com.abixen.platform.core.domain.model.Module
import com.abixen.platform.core.domain.model.Page
import com.abixen.platform.core.domain.service.LayoutService
import com.abixen.platform.core.domain.service.PageService
import spock.lang.Specification

class DashboardServiceTest extends Specification {

    private PageService pageService
    private LayoutService layoutService
    private DashboardModuleService dashboardModuleService
    private PageToPageDtoConverter pageToPageDtoConverter
    private ModuleToDashboardModuleDtoConverter moduleToDashboardModuleDtoConverter
    private DashboardService dashboardService

    void setup() {
        pageService = Mock()
        layoutService = Mock()
        dashboardModuleService = Mock()
        pageToPageDtoConverter = Mock()
        moduleToDashboardModuleDtoConverter = Mock()
        dashboardService = new DashboardService(
                pageService,
                layoutService,
                dashboardModuleService,
                pageToPageDtoConverter,
                moduleToDashboardModuleDtoConverter
        )
    }


    void "should find DashboardDto by page id"() {
        given:
        final List<Module> modules = new ArrayList<>()
        modules.add([id: 1L] as Module)
        modules.add([id: 2L] as Module)

        final List<DashboardModuleDto> dashboardModuleDtos = new ArrayList<>()
        dashboardModuleDtos.add([id: modules[0].id] as DashboardModuleDto)
        dashboardModuleDtos.add([id: modules[1].id] as DashboardModuleDto)

        final Long pageId = 1L
        final Page page = [id: pageId] as Page
        final PageDto pageDto = new PageDto()
        pageDto.id = pageId

        pageService.find(pageId) >> page
        dashboardModuleService.findAllModules(page) >> modules
        moduleToDashboardModuleDtoConverter.convertToList(modules) >> dashboardModuleDtos
        pageToPageDtoConverter.convert(page) >> pageDto

        when:
        final DashboardDto dashboardDto = dashboardService.find(pageId)

        then:
        dashboardDto != null
        dashboardDto.page == pageDto
        dashboardDto.dashboardModuleDtos.size() == 2
        dashboardDto.dashboardModuleDtos[0] == dashboardModuleDtos[0]
        dashboardDto.dashboardModuleDtos[1] == dashboardModuleDtos[1]

        1 * pageService.find(pageId) >> page
        1 * dashboardModuleService.findAllModules(page) >> modules
        1 * moduleToDashboardModuleDtoConverter.convertToList(modules) >> dashboardModuleDtos
        1 * pageToPageDtoConverter.convert(page) >> pageDto
        0 * _
    }

    void "should create dashboard"() {
        given:
        final Long layoutId = 1L
        final Layout layout = [id: layoutId] as Layout
        final Page page = Page.builder()
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