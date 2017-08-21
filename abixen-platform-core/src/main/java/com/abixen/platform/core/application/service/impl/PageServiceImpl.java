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
import com.abixen.platform.common.security.PlatformUser;
import com.abixen.platform.core.application.dto.PageDto;
import com.abixen.platform.core.application.form.PageConfigurationForm;
import com.abixen.platform.core.application.form.PageForm;
import com.abixen.platform.core.application.form.PageSearchForm;
import com.abixen.platform.core.application.service.AclService;
import com.abixen.platform.core.application.service.LayoutService;
import com.abixen.platform.core.application.service.ModuleService;
import com.abixen.platform.core.application.service.PageService;
import com.abixen.platform.core.application.service.SecurityService;
import com.abixen.platform.core.application.service.UserService;
import com.abixen.platform.core.domain.model.Module;
import com.abixen.platform.core.domain.model.Page;
import com.abixen.platform.core.domain.model.PageBuilder;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.domain.repository.ModuleRepository;
import com.abixen.platform.core.domain.repository.PageRepository;
import com.abixen.platform.core.interfaces.web.facade.converter.PageToPageDtoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Transactional
@Service
public class PageServiceImpl implements PageService {

    private final AclService aclService;
    private final LayoutService layoutService;
    private final ModuleService moduleService;
    private final SecurityService securityService;
    private final UserService userService;
    private final PageRepository pageRepository;
    private final ModuleRepository moduleRepository;
    private final PageToPageDtoConverter pageToPageDtoConverter;

    @Autowired
    public PageServiceImpl(AclService aclService,
                           LayoutService layoutService,
                           ModuleService moduleService,
                           SecurityService securityService,
                           UserService userService,
                           PageRepository pageRepository,
                           ModuleRepository moduleRepository,
                           PageToPageDtoConverter pageToPageDtoConverter) {
        this.aclService = aclService;
        this.layoutService = layoutService;
        this.moduleService = moduleService;
        this.securityService = securityService;
        this.userService = userService;
        this.pageRepository = pageRepository;
        this.moduleRepository = moduleRepository;
        this.pageToPageDtoConverter = pageToPageDtoConverter;
    }

    @PostAuthorize("hasPermission(returnObject, '" + PermissionName.Values.PAGE_VIEW + "')")
    @Override
    public Page find(Long id) {
        log.debug("find() - id: " + id);
        return pageRepository.findOne(id);
    }

    @Override
    public List<Page> findAll() {
        PlatformUser platformAuthorizedUser = securityService.getAuthorizedUser();
        User authorizedUser = userService.find(platformAuthorizedUser.getId());

        return pageRepository.findAllSecured(authorizedUser, PermissionName.PAGE_VIEW);
    }

    @Override
    public org.springframework.data.domain.Page<Page> findAll(Pageable pageable, PageSearchForm pageSearchForm) {
        PlatformUser platformAuthorizedUser = securityService.getAuthorizedUser();
        User authorizedUser = userService.find(platformAuthorizedUser.getId());

        return pageRepository.findAllSecured(pageable, pageSearchForm, authorizedUser, PermissionName.PAGE_VIEW);
    }

    @Override
    public PageForm create(PageForm pageForm) {
        log.debug("update() - pageForm={}", pageForm);

        Page page = new PageBuilder()
                .layout(layoutService.findLayout(pageForm.getLayout().getId()))
                .title(pageForm.getTitle())
                .description(pageForm.getDescription())
                .icon(pageForm.getIcon())
                .build();

        Page createdPage = create(page);
        PageDto createdPageDto = pageToPageDtoConverter.convert(createdPage);

        return new PageForm(createdPageDto);
    }

    @PreAuthorize("hasPermission('" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_ADD + "')")
    @Override
    public Page create(PageConfigurationForm pageConfigurationForm) {
        Page page = new PageBuilder()
                .layout(layoutService.findLayout(pageConfigurationForm.getPage().getLayout().getId()))
                .title(pageConfigurationForm.getPage().getTitle())
                .description(pageConfigurationForm.getPage().getDescription())
                .icon(pageConfigurationForm.getPage().getIcon())
                .build();

        return create(page);
    }

    @PreAuthorize("hasPermission(#pageForm.id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_EDIT + "')")
    @Override
    public PageForm update(PageForm pageForm) {
        log.debug("update() - pageForm={}", pageForm);

        Page page = find(pageForm.getId());
        page.changeTitle(pageForm.getTitle());
        page.changeDescription(pageForm.getDescription());
        page.changeIcon(pageForm.getIcon());
        page.changeLayout(layoutService.findLayout(pageForm.getLayout().getId()));

        Page updatedPage = update(page);
        PageDto updatedPageDto = pageToPageDtoConverter.convert(updatedPage);

        return new PageForm(updatedPageDto);
    }

    @PreAuthorize("hasPermission(#page.id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_EDIT + "')")
    @Override
    public Page update(Page page) {
        log.debug("update() - page: " + page);
        return pageRepository.saveAndFlush(page);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_DELETE + "')")
    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("delete() - id={}", id);
        List<Module> pageModules = moduleService.findAll(pageRepository.findOne(id));
        moduleRepository.deleteInBatch(pageModules);
        pageRepository.delete(id);
    }

    private Page create(Page page) {
        log.debug("create() - page={} ", page);
        Page createdPage = pageRepository.save(page);
        aclService.insertDefaultAcl(createdPage, new ArrayList<PermissionName>() {
            {
                add(PermissionName.PAGE_VIEW);
                add(PermissionName.PAGE_EDIT);
                add(PermissionName.PAGE_DELETE);
                add(PermissionName.PAGE_CONFIGURATION);
                add(PermissionName.PAGE_PERMISSION);
            }
        });
        return createdPage;
    }

}