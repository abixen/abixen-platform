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

package com.abixen.platform.service.businessintelligence.infrastructure.configuration;

import com.abixen.platform.common.client.SecurityClient;
import com.abixen.platform.common.model.enumtype.AclClassName;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class TestSecurityClient implements SecurityClient {

    @Override
    public Boolean hasPermission(@PathVariable("username") String username, @PathVariable("securableObjectId") Long securableObjectId, @PathVariable("aclClassName") AclClassName aclClassName, @PathVariable("permissionName") String permissionName) {
        return true;
    }
}