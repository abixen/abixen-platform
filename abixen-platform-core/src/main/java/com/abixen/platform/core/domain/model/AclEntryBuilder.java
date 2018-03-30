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

package com.abixen.platform.core.domain.model;

import com.abixen.platform.common.domain.model.EntityBuilder;


public class AclEntryBuilder extends EntityBuilder<AclEntry> {

    @Override
    protected void initProduct() {
        this.product = new AclEntry();
    }

    @Override
    protected AclEntry assembleProduct() {
        return this.product;
    }

    public AclEntryBuilder permission(Permission permission) {
        this.product.setPermission(permission);
        return this;
    }

    public AclEntryBuilder aclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
        this.product.setAclObjectIdentity(aclObjectIdentity);
        return this;
    }

    public AclEntryBuilder aclSid(AclSid aclSid) {
        this.product.setAclSid(aclSid);
        return this;
    }
}