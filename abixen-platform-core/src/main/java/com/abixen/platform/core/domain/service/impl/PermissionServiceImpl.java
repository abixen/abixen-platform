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

import com.abixen.platform.core.application.form.PermissionSearchForm;
import com.abixen.platform.core.domain.model.Permission;
import com.abixen.platform.core.domain.repository.PermissionRepository;
import com.abixen.platform.core.domain.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionServiceImpl(final PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission find(final Long id) {
        log.debug("find() - id: {}", id);

        return permissionRepository.findOne(id);
    }

    @Override
    public List<Permission> findAll() {
        log.debug("findAll()");

        return permissionRepository.findAll();
    }

    @Override
    public Page<Permission> findAll(final Pageable pageable, final PermissionSearchForm permissionSearchForm) {
        log.debug("findAll() - pageable: {}, permissionSearchForm: {}", pageable, permissionSearchForm);

        return permissionRepository.findAll(pageable, permissionSearchForm);
    }

}