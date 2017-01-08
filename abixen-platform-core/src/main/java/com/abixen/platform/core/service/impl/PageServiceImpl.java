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

import com.abixen.platform.core.form.PageConfigurationForm;
import com.abixen.platform.core.form.PageForm;
import com.abixen.platform.core.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.Module;
import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.core.repository.ModuleRepository;
import com.abixen.platform.core.repository.PageRepository;
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
    private final PageRepository pageRepository;
    private final ModuleRepository moduleRepository;

    @Autowired
    public PageServiceImpl(AclService aclService,
                           DomainBuilderService domainBuilderService,
                           LayoutService layoutService,
                           ModuleService moduleService,
                           PageRepository pageRepository,
                           ModuleRepository moduleRepository) {
        this.aclService = aclService;
        this.domainBuilderService = domainBuilderService;
        this.layoutService = layoutService;
        this.moduleService = moduleService;
        this.pageRepository = pageRepository;
        this.moduleRepository = moduleRepository;
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

        return new PageForm(createPage(page));
    }

    @Override
    public Page createPage(PageConfigurationForm pageConfigurationForm) {
        Page page = buildPage(pageConfigurationForm);
        return createPage(page);
    }

    @Override
    public PageForm updatePage(PageForm pageForm) {
        log.debug("updatePage() - pageForm={}", pageForm);

        Page page = findPage(pageForm.getId());
        page.setTitle(pageForm.getTitle());
        page.setDescription(pageForm.getDescription());
        page.setIcon(pageForm.getIcon());
        page.setLayout(layoutService.findLayout(pageForm.getLayout().getId()));

        return new PageForm(updatePage(page));
    }

    @PreAuthorize("hasPermission(#page, 'PAGE_EDIT')")
    @Override
    public Page updatePage(Page page) {
        log.debug("updatePage() - page: " + page);
        return pageRepository.saveAndFlush(page);
    }

    @Override
    @Transactional
    public void deletePage(Long id) {
        log.debug("deletePage() - id={}", id);
        List<Module> pageModules = moduleService.findAllByPage(pageRepository.findOne(id));
        moduleRepository.deleteInBatch(pageModules);
        pageRepository.delete(id);
    }

    @Override
    public org.springframework.data.domain.Page<Page> findAllPages(Pageable pageable) {
        log.debug("findAllPages() - pageable={}", pageable);
        return pageRepository.findAll(pageable);
    }

    @Override
    public List<Page> findAllPages() {
        log.debug("findAllPages()");
        return pageRepository.findAll();
    }

    @PostAuthorize("hasPermission(returnObject, 'PAGE_VIEW')")
    @Override
    public Page findPage(Long id) {
        log.debug("findPage() - id: " + id);
        return pageRepository.findOne(id);
    }
}