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
import com.abixen.platform.core.dto.RoleDto;
import com.abixen.platform.core.dto.UserDto;
import com.abixen.platform.core.dto.UserRoleDto;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


public class UserRolesForm implements Form {

    @NotNull
    private UserDto user;

    @NotNull
    private List<UserRoleDto> userRoles = new ArrayList<>();

    public UserRolesForm() {
    }

    public UserRolesForm(UserDto user, List<RoleDto> allRoles) {
        this.user = user;

        for (RoleDto role : allRoles) {
            Boolean selected = false;

            if (user.getRoles().contains(role)) {
                selected = true;
            }
            userRoles.add(new UserRoleDto(role, selected));
        }
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<UserRoleDto> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoleDto> userRoles) {
        this.userRoles = userRoles;
    }
}
