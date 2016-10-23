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

package com.abixen.platform.core.form;

import com.abixen.platform.core.dto.RolePermissionDto;
import com.abixen.platform.core.model.impl.Permission;
import com.abixen.platform.core.model.impl.Role;
import com.abixen.platform.core.model.web.RoleWeb;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


public class RolePermissionsForm implements Form {

    @NotNull
    private RoleWeb role;

    @NotNull
    private List<RolePermissionDto> rolePermissions = new ArrayList<>();

    public RolePermissionsForm() {
    }

    public RolePermissionsForm(Role role, List<Permission> allPermissions) {
        this.role = role;

        for (Permission permission : allPermissions) {
            Boolean selected = false;

            if (role.getPermissions().contains(permission)) {
                selected = true;
            }
            rolePermissions.add(new RolePermissionDto(permission, selected));
        }
    }

    public RoleWeb getRole() {
        return role;
    }

    public void setRole(RoleWeb role) {
        this.role = role;
    }

    public List<RolePermissionDto> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermissionDto> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

}
