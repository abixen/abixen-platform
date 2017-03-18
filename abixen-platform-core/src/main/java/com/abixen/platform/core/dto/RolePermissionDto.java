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


public class RolePermissionDto implements Serializable {

    private static final long serialVersionUID = -7430477767491597712L;

    private PermissionDto permission;

    private Boolean selected;

    public RolePermissionDto() {

    }

    public RolePermissionDto(PermissionDto permission, Boolean selected) {
        this.permission = permission;
        this.selected = selected;
    }

    public PermissionDto getPermission() {
        return permission;
    }

    public void setPermission(PermissionDto permission) {
        this.permission = permission;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }


}
