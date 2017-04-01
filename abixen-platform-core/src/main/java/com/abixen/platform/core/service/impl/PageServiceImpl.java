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

import com.abixen.platform.core.converter.PageToPageDtoConverter;
import com.abixen.platform.core.dto.PageDto;
import com.abixen.platform.core.form.PageConfigurationForm;
import com.abixen.platform.core.form.PageForm;
import com.abixen.platform.core.form.PageSearchForm;
import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.Module;
import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.repository.ModuleRepository;
import com.abixen.platform.core.repository.PageRepository;
import com.abixen.platform.common.security.PlatformUser;
import com.abixen.platform.core.service.*;
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
    private final DomainBuilderService domainBuilderService;
    private final LayoutService layoutService;
    private final ModuleService moduleService;
    private final SecurityService securityService;
    private final UserService userService;
    private final PageRepository pageRepository;
    private final ModuleRepository moduleRepository;
    private final PageToPageDtoConverter pageToPageDtoConverter;

    @Autowired
    public PageServiceImpl(AclService aclService,
                           DomainBuilderService domainBuilderService,
                           LayoutService layoutService,
                           ModuleService moduleService,
                           SecurityService securityService,
                           UserService userService,
                           PageRepository pageRepository,
                           ModuleRepository moduleRepository,
                           PageToPageDtoConverter pageToPageDtoConverter) {
        this.aclService = aclService;
        this.domainBuilderService = domainBuilderService;
        this.layoutService = layoutService;
        this.moduleService = moduleService;
        this.securityService = securityService;
        this.userService = userService;
        this.pageRepository = pageRepository;
        this.moduleRepository = moduleRepository;
        this.pageToPageDtoConverter = pageToPageDtoConverter;
    }

    @Override
    public Page buildPage(PageForm pageForm) {
        log.debug("buildPage() - pageForm={}", pageForm);
        return domainBuilderService.newPageBuilderInstance()
                .init(pageForm.getTitle(), layoutService.findLayout(pageForm.getLayout().getId()))
                .description(pageForm.getDescription())
                .icon(pageForm.getIcon())
                .build();
    }

    public Page buildPage(PageConfigurationForm pageConfigurationForm) {
        log.debug("buildPage() - pageConfigurationForm={}", pageConfigurationForm);
        return domainBuilderService.newPageBuilderInstance()
                .init(
                        pageConfigurationForm.getPage().getTitle(),
                        layoutService.findLayout(pageConfigurationForm.getPage().getLayout().getId())
                )
                .description(pageConfigurationForm.getPage().getDescription())
                .icon(pageConfigurationForm.getPage().getIcon())
                .build();
    }

    @Override
    public Page createPage(Page page) {
        log.debug("createPage() - page={} ", page);
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

    @Override
    public PageForm createPage(PageForm pageForm) {
        log.debug("updatePage() - pageForm={}", pageForm);

        Page page = buildPage(pageForm);
        Page createdPage = createPage(page);
        PageDto createdPageDto = pageToPageDtoConverter.convert(createdPage);

        return new PageForm(createdPageDto);
    }

    @PreAuthorize("hasPermission('" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_ADD + "')")
    @Override
    public Page createPage(PageConfigurationForm pageConfigurationForm) {
        Page page = buildPage(pageConfigurationForm);
        return createPage(page);
    }

    @PreAuthorize("hasPermission(#pageForm.id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_EDIT + "')")
    @Override
    public PageForm updatePage(PageForm pageForm) {
        log.debug("updatePage() - pageForm={}", pageForm);

        Page page = findPage(pageForm.getId());
        page.setTitle(pageForm.getTitle());
        page.setDescription(pageForm.getDescription());
        page.setIcon(pageForm.getIcon());
        page.setLayout(layoutService.findLayout(pageForm.getLayout().getId()));

        Page updatedPage = updatePage(page);
        PageDto updatedPageDto = pageToPageDtoConverter.convert(updatedPage);

        return new PageForm(updatedPageDto);
    }

    @PreAuthorize("hasPermission(#page.id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_EDIT + "')")
    @Override
    public Page updatePage(Page page) {
        log.debug("updatePage() - page: " + page);
        return pageRepository.saveAndFlush(page);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_DELETE + "')")
    @Override
    @Transactional
    public void deletePage(Long id) {
        log.debug("deletePage() - id={}", id);
        List<Module> pageModules = moduleService.findAllByPage(pageRepository.findOne(id));
        moduleRepository.deleteInBatch(pageModules);
        pageRepository.delete(id);
    }

    @Override
    public org.springframework.data.domain.Page<Page> findAllPages(Pageable pageable, PageSearchForm pageSearchForm) {
        PlatformUser platformAuthorizedUser = securityService.getAuthorizedUser();
        User authorizedUser = userService.findUser(platformAuthorizedUser.getId());

        return pageRepository.findAllSecured(pageable, pageSearchForm, authorizedUser, PermissionName.PAGE_VIEW);
    }

    @Override
    public List<Page> findAllPages() {
        PlatformUser platformAuthorizedUser = securityService.getAuthorizedUser();
        User authorizedUser = userService.findUser(platformAuthorizedUser.getId());

        return pageRepository.findAllSecured(authorizedUser, PermissionName.PAGE_VIEW);
    }

    @PostAuthorize("hasPermission(returnObject, '" + PermissionName.Values.PAGE_VIEW + "')")
    @Override
    public Page findPage(Long id) {
        log.debug("findPage() - id: " + id);
        return pageRepository.findOne(id);
    }
}