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

package com.abixen.platform.core.interfaces.web;

import com.abixen.platform.core.application.dto.PermissionDto;
import com.abixen.platform.core.application.form.PermissionSearchForm;
import com.abixen.platform.core.application.service.PermissionManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "/api/control-panel/permissions")
public class PermissionController {


    private final PermissionManagementService permissionManagementService;

    @Autowired
    public PermissionController(PermissionManagementService permissionManagementService) {
        this.permissionManagementService = permissionManagementService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<PermissionDto> findAll(@PageableDefault(size = 1, page = 0) Pageable pageable, PermissionSearchForm permissionSearchForm) throws IOException, NoSuchFieldException {
        log.debug("findAll()");

        return permissionManagementService.findAllPermissions(pageable, permissionSearchForm);
    }

}