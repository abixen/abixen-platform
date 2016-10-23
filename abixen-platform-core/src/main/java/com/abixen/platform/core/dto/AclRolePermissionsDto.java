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

import com.abixen.platform.core.model.web.RoleWeb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AclRolePermissionsDto implements Serializable {

    private static final long serialVersionUID = -7432477767491597712L;

    /**
     * Null means that this is the owner
     */
    private RoleWeb role;

    private List<AclPermissionDto> aclPermissionDtos = new ArrayList<>();

    public AclRolePermissionsDto() {
    }

    public AclRolePermissionsDto(RoleWeb roleWeb, List<AclPermissionDto> aclPermissionDtos) {
        this.role = roleWeb;
        this.aclPermissionDtos = aclPermissionDtos;
    }

    public RoleWeb getRole() {
        return role;
    }

    public void setRole(RoleWeb roleWeb) {
        this.role = roleWeb;
    }

    public List<AclPermissionDto> getAclPermissionDtos() {
        return aclPermissionDtos;
    }

    public void setAclPermissionDtos(List<AclPermissionDto> aclPermissionDtos) {
        this.aclPermissionDtos = aclPermissionDtos;
    }


}
