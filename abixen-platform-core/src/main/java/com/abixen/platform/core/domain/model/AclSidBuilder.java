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

import com.abixen.platform.common.model.enumtype.AclSidType;
import com.abixen.platform.common.util.EntityBuilder;


public class AclSidBuilder extends EntityBuilder<AclSid> {

    @Override
    protected void initProduct() {
        this.product = new AclSid();
    }

    @Override
    protected AclSid assembleProduct() {
        return this.product;
    }

    public AclSidBuilder sidId(Long sidId) {
        this.product.setSidId(sidId);
        return this;
    }

    public AclSidBuilder aclSidType(AclSidType sidType) {
        this.product.setSidType(sidType);
        return this;
    }
}