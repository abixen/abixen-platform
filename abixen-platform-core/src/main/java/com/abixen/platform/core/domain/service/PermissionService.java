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
import com.abixen.platform.core.application.form.PermissionSearchForm;
import com.abixen.platform.core.domain.model.Permission;
import com.abixen.platform.core.domain.model.PermissionAclClassCategory;
import com.abixen.platform.core.domain.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@PlatformDomainService
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(final PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission find(final Long id) {
        log.debug("find() - id: {}", id);

        return permissionRepository.findOne(id);
    }

    public Permission find(final PermissionName permissionName) {
        log.debug("find() - permissionName: {}", permissionName);

        return permissionRepository.find(permissionName);
    }

    public List<Permission> findAll() {
        log.debug("findAll()");

        return permissionRepository.findAll();
    }

    public List<Permission> findAll(final PermissionAclClassCategory permissionAclClassCategory) {
        log.debug("findAll() - permissionAclClassCategory: {}", permissionAclClassCategory);

        return permissionRepository.findAll(permissionAclClassCategory);
    }

    public Page<Permission> findAll(final Pageable pageable, final PermissionSearchForm permissionSearchForm) {
        log.debug("findAll() - pageable: {}, permissionSearchForm: {}", pageable, permissionSearchForm);

        return permissionRepository.findAll(pageable, permissionSearchForm);
    }

}