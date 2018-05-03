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

package com.abixen.platform.core.domain.service;

import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.common.infrastructure.annotation.PlatformDomainService;
import com.abixen.platform.core.application.form.PageSearchForm;
import com.abixen.platform.core.domain.model.Module;
import com.abixen.platform.core.domain.model.Page;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.domain.repository.PageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@PlatformDomainService
public class PageService {

    private final AclService aclService;
    private final ModuleService moduleService;
    private final PageRepository pageRepository;

    @Autowired
    public PageService(AclService aclService,
                       ModuleService moduleService,
                       PageRepository pageRepository) {
        this.aclService = aclService;
        this.moduleService = moduleService;
        this.pageRepository = pageRepository;
    }

    public Page find(final Long id) {
        log.debug("find() - id: {}", id);

        return pageRepository.findOne(id);
    }

    public List<Page> findAll(final User authorizedUser) {
        log.debug("findAll() - authorizedUser: {}", authorizedUser);

        return pageRepository.findAllSecured(authorizedUser, PermissionName.PAGE_VIEW);
    }

    public org.springframework.data.domain.Page<Page> findAll(final Pageable pageable, final PageSearchForm pageSearchForm, final User authorizedUser) {
        log.debug("findAll() - pageable: {}, pageSearchForm: {}, authorizedUser: {}", pageable, pageSearchForm, authorizedUser);

        return pageRepository.findAllSecured(pageable, pageSearchForm, authorizedUser, PermissionName.PAGE_VIEW);
    }

    public Page create(final Page page) {
        log.debug("create() - page: {}", page);

        final Page createdPage = pageRepository.save(page);

        aclService.createDefaultAcl(createdPage, new ArrayList<PermissionName>() {
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

    public Page update(final Page page) {
        log.debug("update() - page: {}", page);

        //FIXME - check if flush needed
        return pageRepository.saveAndFlush(page);
    }

    public void delete(final Long id) {
        log.debug("delete() - id: {}", id);

        final Page page = find(id);
        final List<Module> pageModules = moduleService.findAll(page);

        moduleService.deleteAll(pageModules);
        pageRepository.delete(id);
    }

}