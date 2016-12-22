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

import com.abixen.platform.core.model.RoleBase;
import com.abixen.platform.core.model.impl.Role;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


public class RoleForm implements Form {

    @NotNull
    @Length(min = RoleBase.ROLE_NAME_MIN_LENGTH, max = RoleBase.ROLE_NAME_MAX_LENGTH)
    private String name;

    public RoleForm() {

    }

    public RoleForm(Role role) {
        this.name = role.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
