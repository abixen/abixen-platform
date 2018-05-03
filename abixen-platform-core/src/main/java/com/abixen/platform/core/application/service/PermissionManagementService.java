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

import com.abixen.platform.common.infrastructure.annotation.PlatformApplicationService;
import com.abixen.platform.core.application.converter.PermissionToPermissionDtoConverter;
import com.abixen.platform.core.application.dto.PermissionDto;
import com.abixen.platform.core.application.form.PermissionSearchForm;
import com.abixen.platform.core.domain.model.Permission;
import com.abixen.platform.core.domain.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional
@PlatformApplicationService
public class PermissionManagementService {

    private final PermissionService permissionService;
    private final PermissionToPermissionDtoConverter permissionToPermissionDtoConverter;

    @Autowired
    public PermissionManagementService(PermissionService permissionService,
                                       PermissionToPermissionDtoConverter permissionToPermissionDtoConverter) {
        this.permissionService = permissionService;
        this.permissionToPermissionDtoConverter = permissionToPermissionDtoConverter;
    }


    public Page<PermissionDto> findAllPermissions(final Pageable pageable, final PermissionSearchForm permissionSearchForm) {
        log.debug("findAllPermissions() - permissionSearchForm: {}", permissionSearchForm);

        final Page<Permission> permissions = permissionService.findAll(pageable, permissionSearchForm);

        return permissionToPermissionDtoConverter.convertToPage(permissions);
    }

}