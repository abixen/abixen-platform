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

import com.abixen.platform.core.dto.DashboardModuleDto;
import com.abixen.platform.core.dto.PageModelDto;
import com.abixen.platform.core.model.impl.Module;
import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.core.service.LayoutService;
import com.abixen.platform.core.service.ModuleService;
import com.abixen.platform.core.service.PageModelService;
import com.abixen.platform.core.service.PageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class PageModelServiceImpl implements PageModelService {

    private final Logger log = Logger.getLogger(PageModelServiceImpl.class.getName());

    @Autowired
    private PageService pageService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private LayoutService layoutService;

    //@PreAuthorize("hasPermission(#pageId, 'Page', 'DELETE')")
    @Override
    public PageModelDto getPageModel(Long pageId) {

        Page page = pageService.findPage(pageId);
        convertPageLayoutToJson(page);
        List<Module> modules = moduleService.findAllByPage(page);
        List<DashboardModuleDto> dashboardModuleDtos = new ArrayList<>();

        modules
                .stream()
                .forEach(module ->
                                dashboardModuleDtos.add(new DashboardModuleDto(module.getId(), module.getDescription(), module.getModuleType().getName(), module.getModuleType(),
                                        module.getTitle(), module.getRowIndex(), module.getColumnIndex(), module.getOrderIndex()))
                );

        PageModelDto pageModelDto = new PageModelDto(page, dashboardModuleDtos);

        return pageModelDto;
    }

    @Override
    @Transactional
    public PageModelDto updatePageModel(PageModelDto currentPageModelDto) {

        log.debug("updatePageModel() - currentPageModelDto: " + currentPageModelDto);

        List<Long> currentModulesIds = new ArrayList<>();

        Page page = pageService.findPage(currentPageModelDto.getPage().getId());
        page.setDescription(currentPageModelDto.getPage().getDescription());
        page.setName(currentPageModelDto.getPage().getName());
        page.setTitle(currentPageModelDto.getPage().getTitle());
        page.setLayout(layoutService.findLayout(currentPageModelDto.getPage().getLayout().getId()));
        pageService.updatePage(page);

        updateExistingModules(currentPageModelDto.getDashboardModuleDtos(), currentModulesIds);
        createNonExistentModules(currentPageModelDto.getDashboardModuleDtos(), currentPageModelDto.getPage().getId(), currentModulesIds);
        if (currentModulesIds.isEmpty()) {
            moduleService.removeAll(page);
        } else {
            moduleService.removeAllExcept(page, currentModulesIds);
        }

        return currentPageModelDto;
    }

    private void updateExistingModules(List<DashboardModuleDto> dashboardModuleDtos, List<Long> modulesIds) {

        dashboardModuleDtos
                .stream()
                .filter(dashboardModuleDto -> dashboardModuleDto.getId() != null)
                .forEach(dashboardModuleDto -> {

                    log.debug("updateExistingModules() - dashboardModuleDto: " + dashboardModuleDto);

                    Module module = moduleService.findModule(dashboardModuleDto.getId());
                    module.setDescription(dashboardModuleDto.getDescription());
                    module.setTitle(dashboardModuleDto.getTitle());
                    module.setRowIndex(dashboardModuleDto.getRowIndex());
                    module.setColumnIndex(dashboardModuleDto.getColumnIndex());
                    module.setOrderIndex(dashboardModuleDto.getOrderIndex());

                    moduleService.updateModule(module);
                    modulesIds.add(module.getId());
                });

    }

    private void createNonExistentModules(List<DashboardModuleDto> dashboardModuleDtos, Long pageId, List<Long> modulesIds) {

        dashboardModuleDtos
                .stream()
                .filter(dashboardModuleDto -> dashboardModuleDto.getId() == null)
                .forEach(dashboardModuleDto -> {

                            log.debug("createNonExistentModules() - dashboardModuleDto: " + dashboardModuleDto);

                            Module module = moduleService.buildModule(dashboardModuleDto, pageService.findPage(pageId));
                            dashboardModuleDto.setId(moduleService.createModule(module).getId());
                            modulesIds.add(dashboardModuleDto.getId());
                        }
                );
    }

    private void convertPageLayoutToJson(Page page) {

        String html = page.getLayout().getContent();
        page.getLayout().setContent(layoutService.htmlLayoutToJson(html));

    }

}
