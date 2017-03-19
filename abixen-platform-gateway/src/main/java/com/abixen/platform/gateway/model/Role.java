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

package com.abixen.platform.gateway.model;

import com.abixen.platform.common.model.Model;
import com.abixen.platform.common.model.SimpleRoleBase;
import com.abixen.platform.common.model.enumtype.RoleType;


public class Role extends Model implements SimpleRoleBase {

    private Long id;
    private RoleType roleType;
    private String name;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    @Override
    public RoleType getRoleType() {
        return roleType;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
