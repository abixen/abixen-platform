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

package com.abixen.platform.core.application.service;

import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.common.infrastructure.annotation.PlatformApplicationService;
import com.abixen.platform.common.infrastructure.security.PlatformUser;
import com.abixen.platform.core.application.converter.PageToPageDtoConverter;
import com.abixen.platform.core.application.dto.PageDto;
import com.abixen.platform.core.application.form.PageForm;
import com.abixen.platform.core.application.form.PageSearchForm;
import com.abixen.platform.core.domain.model.Page;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.domain.service.LayoutService;
import com.abixen.platform.core.domain.service.PageService;
import com.abixen.platform.core.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Transactional
@PlatformApplicationService
public class PageManagementService {

    private final PageService pageService;
    private final LayoutService layoutService;
    private final SecurityService securityService;
    private final UserService userService;
    private final PageToPageDtoConverter pageToPageDtoConverter;

    @Autowired
    public PageManagementService(PageService pageService,
                                 LayoutService layoutService,
                                 SecurityService securityService,
                                 UserService userService,
                                 PageToPageDtoConverter pageToPageDtoConverter) {
        this.pageService = pageService;
        this.layoutService = layoutService;
        this.securityService = securityService;
        this.userService = userService;
        this.pageToPageDtoConverter = pageToPageDtoConverter;
    }

    @PostAuthorize("hasPermission(returnObject, '" + PermissionName.Values.PAGE_VIEW + "')")
    public PageDto findPage(Long id) {
        log.debug("findPage() - id: {}", id);

        final Page page = pageService.find(id);

        return pageToPageDtoConverter.convert(page);
    }

    public List<PageDto> findAllPages() {
        log.debug("findAllPages()");

        final PlatformUser platformAuthorizedUser = securityService.getAuthorizedUser();
        final User authorizedUser = userService.find(platformAuthorizedUser.getId());

        final List<Page> pages = pageService.findAll(authorizedUser);

        return pageToPageDtoConverter.convertToList(pages);
    }

    public org.springframework.data.domain.Page<PageDto> findAllPages(Pageable pageable, PageSearchForm pageSearchForm) {
        log.debug("findAllPages() - pageable: {}, pageSearchForm: {}", pageable, pageSearchForm);

        final PlatformUser platformAuthorizedUser = securityService.getAuthorizedUser();
        final User authorizedUser = userService.find(platformAuthorizedUser.getId());

        final org.springframework.data.domain.Page<Page> pages = pageService.findAll(pageable, pageSearchForm, authorizedUser);

        return pageToPageDtoConverter.convertToPage(pages);
    }

    public PageForm createPage(PageForm pageForm) {
        log.debug("createPage() - pageForm: {}", pageForm);

        final Page page = Page.builder()
                .layout(layoutService.find(pageForm.getLayout().getId()))
                .title(pageForm.getTitle())
                .description(pageForm.getDescription())
                .icon(pageForm.getIcon())
                .build();

        final Page createdPage = pageService.create(page);
        final PageDto createdPageDto = pageToPageDtoConverter.convert(createdPage);

        return new PageForm(createdPageDto);
    }

    @PreAuthorize("hasPermission(#pageForm.id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_EDIT + "')")
    public PageForm updatePage(PageForm pageForm) {
        log.debug("updatePage() - pageForm: {}", pageForm);

        final Page page = pageService.find(pageForm.getId());
        page.changeTitle(pageForm.getTitle());
        page.changeDescription(pageForm.getDescription());
        page.changeIcon(pageForm.getIcon());
        page.changeLayout(layoutService.find(pageForm.getLayout().getId()));

        final Page updatedPage = pageService.update(page);
        final PageDto updatedPageDto = pageToPageDtoConverter.convert(updatedPage);

        return new PageForm(updatedPageDto);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_DELETE + "')")
    public void deletePage(Long id) {
        log.debug("deletePage() - id: {}", id);

        pageService.delete(id);
    }

}