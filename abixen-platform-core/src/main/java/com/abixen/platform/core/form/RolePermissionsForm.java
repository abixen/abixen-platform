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

import com.abixen.platform.common.form.Form;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.dto.PermissionDto;
import com.abixen.platform.core.dto.RoleDto;
import com.abixen.platform.core.dto.RolePermissionDto;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RolePermissionsForm implements Form {

    @NotNull
    private RoleDto role;

    @NotNull
    private List<RolePermissionDto> rolePermissions = new ArrayList<>();

    public RolePermissionsForm() {
    }

    public RolePermissionsForm(RoleDto role, List<PermissionDto> allPermissions) {
        this.role = role;

        List<PermissionName> rolePermissionsName = role.getPermissions().stream()
                .map(permissionDto -> permissionDto.getPermissionName())
                .collect(Collectors.toList());

        for (PermissionDto permission : allPermissions) {
            Boolean selected = false;

            if (rolePermissionsName.contains(permission.getPermissionName())) {
                selected = true;
            }
            rolePermissions.add(new RolePermissionDto(permission, selected));
        }
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    public List<RolePermissionDto> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermissionDto> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

}
