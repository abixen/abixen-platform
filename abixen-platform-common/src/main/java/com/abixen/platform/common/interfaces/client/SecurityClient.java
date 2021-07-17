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

package com.abixen.platform.common.interfaces.client;

import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(value = "abixen-platform-core", contextId = "security")
public interface SecurityClient {

    @RequestMapping(method = RequestMethod.GET,
            value = "/api/control-panel/securities/has-permission/{username}/{securableObjectId}/{aclClassName}/{permissionName}")
    Boolean hasPermission(@PathVariable("username") String username,
                          @PathVariable("securableObjectId") Long securableObjectId,
                          @PathVariable("aclClassName") AclClassName aclClassName,
                          @PathVariable("permissionName") String permissionName);

}