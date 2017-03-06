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

package com.abixen.platform.core.controller;

import com.abixen.platform.core.dto.AclRolesPermissionsDto;
import com.abixen.platform.core.service.AclService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/control-panel/acls")
public class AclController {

    @Autowired
    private AclService aclService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public AclRolesPermissionsDto getAclRolesPermissionsDto(@RequestParam("permissionAclClassCategoryName") String permissionAclClassCategoryName, @RequestParam("objectId") Long objectId) {
        log.debug("getAclRolesPermissionsDto()");
        return aclService.getAclRolesPermissionsDto(permissionAclClassCategoryName, objectId);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public AclRolesPermissionsDto updateAclRolesPermissions(@RequestParam("permissionAclClassCategoryName") String permissionAclClassCategoryName, @RequestParam("objectId") Long objectId, @RequestBody AclRolesPermissionsDto aclRolesPermissionsDto) {
        log.debug("updateAclRolesPermissions() - aclRolesPermissionsDto: " + aclRolesPermissionsDto);
        return aclService.updateAclRolesPermissionsDto(aclRolesPermissionsDto, permissionAclClassCategoryName, objectId);
    }

}
