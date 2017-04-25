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

package com.abixen.platform.core.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AclRolesPermissionsDto implements Serializable {

    private static final long serialVersionUID = -7435477767491597712L;

    private List<AclRolePermissionsDto> aclRolePermissionsDtos = new ArrayList<>();

    private List<PermissionDto> permissions = new ArrayList<>();

    public AclRolesPermissionsDto() {
    }

    public AclRolesPermissionsDto(List<AclRolePermissionsDto> aclRolePermissionsDtos, List<PermissionDto> permissions) {
        this.aclRolePermissionsDtos = aclRolePermissionsDtos;
        this.permissions = new ArrayList<>();
        this.permissions.addAll(permissions);
    }

    public List<AclRolePermissionsDto> getAclRolePermissionsDtos() {
        return aclRolePermissionsDtos;
    }

    public void setAclRolePermissionsDtos(List<AclRolePermissionsDto> aclRolePermissionsDtos) {
        this.aclRolePermissionsDtos = aclRolePermissionsDtos;
    }

    public List<PermissionDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDto> permissions) {
        this.permissions = permissions;
    }


}