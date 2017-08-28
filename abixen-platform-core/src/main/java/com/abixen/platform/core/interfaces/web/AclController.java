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

import com.abixen.platform.core.application.dto.AclRolesPermissionsDto;
import com.abixen.platform.core.application.service.AclManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/control-panel/acls")
public class AclController {

    private final AclManagementService aclManagementService;

    @Autowired
    public AclController(AclManagementService aclManagementService) {
        this.aclManagementService = aclManagementService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public AclRolesPermissionsDto find(@RequestParam("permissionAclClassCategoryName") String permissionAclClassCategoryName, @RequestParam("objectId") Long objectId) {
        log.debug("find() - permissionAclClassCategoryName: {}, objectId: {}", permissionAclClassCategoryName, objectId);

        return aclManagementService.findAclRolesPermissions(permissionAclClassCategoryName, objectId);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public AclRolesPermissionsDto update(@RequestParam("permissionAclClassCategoryName") String permissionAclClassCategoryName, @RequestParam("objectId") Long objectId, @RequestBody AclRolesPermissionsDto aclRolesPermissionsDto) {
        log.debug("update() - permissionAclClassCategoryName: {}, objectId: {}, aclRolesPermissionsDto: {}", permissionAclClassCategoryName, objectId, aclRolesPermissionsDto);

        return aclManagementService.updateAclRolesPermissions(aclRolesPermissionsDto, permissionAclClassCategoryName, objectId);
    }

}