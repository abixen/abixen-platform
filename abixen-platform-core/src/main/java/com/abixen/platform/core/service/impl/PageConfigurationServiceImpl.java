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

import com.abixen.platform.core.converter.ModuleTypeToModuleTypeDtoConverter;
import com.abixen.platform.core.converter.PageToPageDtoConverter;
import com.abixen.platform.core.dto.DashboardModuleDto;
import com.abixen.platform.core.dto.PageDto;
import com.abixen.platform.core.dto.PageModelDto;
import com.abixen.platform.core.exception.PlatformCoreException;
import com.abixen.platform.core.form.PageConfigurationForm;
import com.abixen.platform.core.model.impl.Module;
import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.core.service.LayoutService;
import com.abixen.platform.core.service.ModuleService;
import com.abixen.platform.core.service.PageConfigurationService;
import com.abixen.platform.core.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Transactional
@Service
public class PageConfigurationServiceImpl implements PageConfigurationService {

    private final PageService pageService;
    private final ModuleService moduleService;
    private final LayoutService layoutService;
    private final PageToPageDtoConverter pageToPageDtoConverter;
    private final ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter;

    @Autowired
    public PageConfigurationServiceImpl(PageService pageService,
                                        ModuleService moduleService,
                                        LayoutService layoutService,
                                        PageToPageDtoConverter pageToPageDtoConverter,
                                        ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter) {
        this.pageService = pageService;
        this.moduleService = moduleService;
        this.layoutService = layoutService;
        this.pageToPageDtoConverter = pageToPageDtoConverter;
        this.moduleTypeToModuleTypeDtoConverter = moduleTypeToModuleTypeDtoConverter;
    }

    @Override
    public PageModelDto getPageConfiguration(Long pageId) {
        log.debug("getPageConfiguration() - pageId: {}", pageId);

        Page page = pageService.findPage(pageId);
        log.debug("page.getLayout().getContent(): {}", page.getLayout().getContent());

        List<Module> modules = moduleService.findAllByPage(page);
        List<DashboardModuleDto> dashboardModuleDtos = new ArrayList<>();

        modules
                .stream()
                .forEach(module ->
                        dashboardModuleDtos.add(new DashboardModuleDto(
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
        return new PageModelDto(pageDto, dashboardModuleDtos);
    }

    @Override
    public PageConfigurationForm createPageConfiguration(PageConfigurationForm pageConfigurationForm) {
        Page page = pageService.createPage(pageConfigurationForm);
        layoutService.convertPageLayoutToJson(page);
        PageDto pageDto = pageToPageDtoConverter.convert(page);
        return new PageConfigurationForm(pageDto);
    }

    @Override
    public PageConfigurationForm updatePageConfiguration(PageConfigurationForm pageConfigurationForm) {

        return changePageConfiguration(pageConfigurationForm, false);
    }

    @Override
    public PageConfigurationForm configurePageConfiguration(PageConfigurationForm pageConfigurationForm) {

        return changePageConfiguration(pageConfigurationForm, true);
    }

    private PageConfigurationForm changePageConfiguration(PageConfigurationForm pageConfigurationForm, boolean configurationChangeType) {
        List<Long> currentModulesIds = new ArrayList<>();

        Page page = pageService.findPage(pageConfigurationForm.getPage().getId());

        if (configurationChangeType) {
            validateConfiguration(pageConfigurationForm, page);
        }

        page.setDescription(pageConfigurationForm.getPage().getDescription());
        page.setTitle(pageConfigurationForm.getPage().getTitle());
        page.setIcon(pageConfigurationForm.getPage().getIcon());
        page.setLayout(layoutService.findLayout(pageConfigurationForm.getPage().getLayout().getId()));
        pageService.updatePage(page);

        updateExistingModules(pageConfigurationForm.getDashboardModuleDtos(), currentModulesIds);
        createNonExistentModules(pageConfigurationForm.getDashboardModuleDtos(), pageConfigurationForm.getPage().getId(), currentModulesIds);
        if (currentModulesIds.isEmpty()) {
            moduleService.removeAll(page);
        } else {
            moduleService.removeAllExcept(page, currentModulesIds);
        }

        //FIXME - build a new instance
        return pageConfigurationForm;
    }

    private void updateExistingModules(List<DashboardModuleDto> dashboardModuleDtos, List<Long> modulesIds) {

        dashboardModuleDtos
                .stream()
                .filter(dashboardModuleDto -> dashboardModuleDto.getId() != null)
                .forEach(dashboardModuleDto -> {

                    log.debug("updateExistingModules() - dashboardModuleDto: {}", dashboardModuleDto);

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

                            log.debug("createNonExistentModules() - dashboardModuleDto: {}", dashboardModuleDto);

                            Module module = moduleService.buildModule(dashboardModuleDto, pageService.findPage(pageId));
                            dashboardModuleDto.setId(moduleService.createModule(module).getId());
                            modulesIds.add(dashboardModuleDto.getId());
                        }
                );
    }

    private void validateConfiguration(PageConfigurationForm pageConfigurationForm, Page page) {
        boolean validationFailed = false;

        log.debug("pageConfigurationForm.getPage()={}, page={}", pageConfigurationForm.getPage(), page);

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