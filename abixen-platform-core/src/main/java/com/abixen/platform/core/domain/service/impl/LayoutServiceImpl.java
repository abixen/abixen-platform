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

package com.abixen.platform.core.domain.service.impl;

import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.common.infrastructure.annotation.PlatformDomainService;
import com.abixen.platform.core.application.form.LayoutSearchForm;
import com.abixen.platform.core.domain.model.Layout;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.domain.repository.LayoutRepository;
import com.abixen.platform.core.domain.service.LayoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Slf4j
@PlatformDomainService
public class LayoutServiceImpl implements LayoutService {

    private final LayoutRepository layoutRepository;


    @Autowired
    public LayoutServiceImpl(final LayoutRepository layoutRepository) {
        this.layoutRepository = layoutRepository;
    }

    @Override
    public Layout find(final Long id) {
        log.debug("find() - id: {}", id);

        return layoutRepository.findOne(id);
    }

    @Override
    public List<Layout> findAll(final User authorizedUser) {
        log.debug("findAll() - authorizedUser: {}", authorizedUser);

        return layoutRepository.findAllSecured(authorizedUser, PermissionName.LAYOUT_VIEW);
    }

    @Override
    public Page<Layout> findAll(final Pageable pageable, final LayoutSearchForm layoutSearchForm, final User authorizedUser) {
        log.debug("findAll() - pageable: {}, layoutSearchForm: {}, authorizedUser: {}", pageable, layoutSearchForm, authorizedUser);

        return layoutRepository.findAllSecured(pageable, layoutSearchForm, authorizedUser, PermissionName.LAYOUT_VIEW);
    }

    @Override
    public Layout create(final Layout layout) {
        log.debug("create() - layout: {}", layout);

        return layoutRepository.save(layout);
    }

    @Override
    public Layout update(final Layout layout) {
        log.debug("update() - layout: {}", layout);

        return layoutRepository.save(layout);
    }

    @Override
    public void delete(final Long id) {
        log.debug("delete() - id: {}", id);

        layoutRepository.delete(id);
    }

}