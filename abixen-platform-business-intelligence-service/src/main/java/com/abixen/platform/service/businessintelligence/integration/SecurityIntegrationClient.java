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

package com.abixen.platform.service.businessintelligence.integration;

import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.service.businessintelligence.client.SecurityClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecurityIntegrationClient {

    private final SecurityClient securityClient;

    @Autowired
    public SecurityIntegrationClient(SecurityClient securityClient) {
        this.securityClient = securityClient;
    }

    @HystrixCommand(fallbackMethod = "hasPermissionFallback")
    public boolean hasPermission(String username,
                                 Long securableObjectId,
                                 AclClassName aclClassName,
                                 String permissionName) {
        return securityClient.hasPermission(username, securableObjectId, aclClassName, permissionName);
    }

    private boolean hasPermissionFallback(String username,
                                          Long securableObjectId,
                                          AclClassName aclClassName,
                                          String permissionName) {
        log.error("hasPermissionFallback: {}", username);
        return false;
    }
}