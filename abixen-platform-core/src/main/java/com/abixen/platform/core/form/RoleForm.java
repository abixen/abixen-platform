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
import com.abixen.platform.common.model.RoleBase;
import com.abixen.platform.common.model.enumtype.RoleType;
import com.abixen.platform.core.model.impl.Role;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


public class RoleForm implements Form {

    private Long id;

    @NotNull
    @Length(max = RoleBase.ROLE_NAME_MAX_LENGTH)
    private String name;

    private RoleType roleType;

    public RoleForm() {

    }

    public RoleForm(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
}
