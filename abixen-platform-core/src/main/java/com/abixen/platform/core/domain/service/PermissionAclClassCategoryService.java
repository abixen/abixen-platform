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

import com.abixen.platform.common.infrastructure.annotation.PlatformDomainService;
import com.abixen.platform.core.domain.model.PermissionAclClassCategory;
import com.abixen.platform.core.domain.repository.PermissionAclClassCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@PlatformDomainService
public class PermissionAclClassCategoryService {

    private final PermissionAclClassCategoryRepository permissionAclClassCategoryRepository;

    @Autowired
    public PermissionAclClassCategoryService(final PermissionAclClassCategoryRepository permissionAclClassCategoryRepository) {
        this.permissionAclClassCategoryRepository = permissionAclClassCategoryRepository;
    }

    public PermissionAclClassCategory find(final String permissionAclClassCategoryName) {
        log.debug("find() - permissionAclClassCategoryName: {}", permissionAclClassCategoryName);

        return permissionAclClassCategoryRepository.find(permissionAclClassCategoryName);
    }

}