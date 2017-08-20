/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.core.application.service.impl;

import com.abixen.platform.core.application.dto.DashboardModuleDto;
import com.abixen.platform.core.application.dto.PageDto;
import com.abixen.platform.core.application.dto.PageModelDto;
import com.abixen.platform.core.application.form.PageConfigurationForm;
import com.abixen.platform.core.application.service.LayoutService;
import com.abixen.platform.core.application.service.ModuleService;
import com.abixen.platform.core.application.service.ModuleTypeService;
import com.abixen.platform.core.application.service.PageConfigurationService;
import com.abixen.platform.core.application.service.PageService;
import com.abixen.platform.core.domain.model.Module;
import com.abixen.platform.core.domain.model.ModuleBuilder;
import com.abixen.platform.core.domain.model.ModuleType;
import com.abixen.platform.core.domain.model.Page;
import com.abixen.platform.core.infrastructure.exception.PlatformCoreException;
import com.abixen.platform.core.interfaces.converter.ModuleTypeToModuleTypeDtoConverter;
import com.abixen.platform.core.interfaces.converter.PageToPageDtoConverter;
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
    private final ModuleTypeService moduleTypeService;
    private final LayoutService layoutService;
    private final PageToPageDtoConverter pageToPageDtoConverter;
    private final ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter;

    @Autowired
    public PageConfigurationServiceImpl(PageService pageService,
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
    public PageModelDto getPageConfiguration(Long pageId) {
        log.debug("getPageConfiguration() - pageId: {}", pageId);

        Page page = pageService.find(pageId);
        log.debug("page.getLayout().getContent(): {}", page.getLayout().getContent());

        List<Module> modules = moduleService.findAll(page);
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
        Page page = pageService.create(pageConfigurationForm);
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

    PageConfigurationForm changePageConfiguration(PageConfigurationForm pageConfigurationForm, boolean configurationChangeType) {
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
        if (currentModulesIds.isEmpty()) {
            moduleService.deleteAll(page);
        } else {
            moduleService.deleteAllExcept(page, currentModulesIds);
        }

        //FIXME - build a new instance
        return pageConfigurationForm;
    }

    void updateExistingModules(List<DashboardModuleDto> dashboardModuleDtos, List<Long> modulesIds) {
        dashboardModuleDtos
                .stream()
                .filter(dashboardModuleDto -> dashboardModuleDto.getId() != null)
                .forEach(dashboardModuleDto -> {

                    log.debug("updateExistingModules() - dashboardModuleDto: {}", dashboardModuleDto);

                    Module module = moduleService.find(dashboardModuleDto.getId());
                    module.changeDescription(dashboardModuleDto.getDescription());
                    module.changeTitle(dashboardModuleDto.getTitle());
                    module.changePositionIndexes(dashboardModuleDto.getRowIndex(), dashboardModuleDto.getColumnIndex(), dashboardModuleDto.getOrderIndex());

                    moduleService.update(module);
                    modulesIds.add(module.getId());
                });
    }

    void createNonExistentModules(List<DashboardModuleDto> dashboardModuleDtos, Long pageId, List<Long> modulesIds) {
        dashboardModuleDtos
                .stream()
                .filter(dashboardModuleDto -> dashboardModuleDto.getId() == null)
                .forEach(dashboardModuleDto -> {

                            log.debug("createNonExistentModules() - dashboardModuleDto: {}", dashboardModuleDto);

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