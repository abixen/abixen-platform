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

package com.abixen.platform.core.util.impl;


import com.abixen.platform.common.model.enumtype.RoleType;
import com.abixen.platform.core.model.impl.Role;
import com.abixen.platform.common.util.EntityBuilder;
import com.abixen.platform.core.util.RoleBuilder;


public class RoleBuilderImpl extends EntityBuilder<Role> implements RoleBuilder {

    @Override
    protected void initProduct() {
        this.product = new Role();
    }

    @Override
    protected Role assembleProduct() {
        return this.product;
    }

    public RoleBuilder name(String name) {
        this.product.setName(name);
        return this;
    }

    public RoleBuilder type(RoleType roleType) {
        this.product.setRoleType(roleType);
        return this;
    }

}
