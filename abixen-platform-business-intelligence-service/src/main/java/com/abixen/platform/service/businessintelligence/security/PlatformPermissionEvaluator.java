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

package com.abixen.platform.service.businessintelligence.security;

import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.security.PlatformUser;
import com.abixen.platform.service.businessintelligence.integration.SecurityIntegrationClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Slf4j
@Component
public class PlatformPermissionEvaluator implements PermissionEvaluator {

    private final SecurityIntegrationClient securityIntegrationClient;

    @Autowired
    public PlatformPermissionEvaluator(SecurityIntegrationClient securityIntegrationClient) {
        this.securityIntegrationClient = securityIntegrationClient;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.debug("hasPermission() - authentication: " + authentication + ", targetDomainObject: " + targetDomainObject + ", permission: " + permission);

        throw new NotImplementedException("Method hasPermission not implemented yet!");
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId,
                                 String targetType, Object permission) {
        log.debug("hasPermission() - authentication: " + authentication + ", targetId: " + targetId + ", targetType: " + targetType + ", permission: " + permission);

        PlatformUser platformUser = (PlatformUser) authentication.getPrincipal();
        log.debug("platformWebUser" + platformUser.getId());

        return securityIntegrationClient.hasPermission(platformUser.getUsername(), (Long) targetId, AclClassName.getByName(targetType), (String) permission);
    }

}