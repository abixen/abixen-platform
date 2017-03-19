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

package com.abixen.platform.core.service;

import com.abixen.platform.core.dto.AclRolesPermissionsDto;
import com.abixen.platform.common.model.SecurableModel;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.PermissionAclClassCategory;

import java.util.List;


public interface AclService {

    void insertDefaultAcl(SecurableModel securableModel, List<PermissionName> permissionNames);

    void insertDefaultAcl(SecurableModel securableModel, PermissionName permissionName);

    AclRolesPermissionsDto getAclRolesPermissionsDto(String permissionAclClassCategoryName, Long objectId);

    AclRolesPermissionsDto getAclRolesPermissionsDto(PermissionAclClassCategory permissionAclClassCategory, Long objectId);

    AclRolesPermissionsDto updateAclRolesPermissionsDto(AclRolesPermissionsDto aclRolesPermissionsDto, String permissionAclClassCategoryName, Long objectId);
}
