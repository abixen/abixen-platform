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

import com.abixen.platform.core.security.PlatformUser;
import com.abixen.platform.service.businessintelligence.client.SecurityClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Slf4j
@Component
public class PlatformPermissionEvaluator implements PermissionEvaluator {

    //@Autowired
    //SecurityService securityService;

    //@Autowired
    //UserService userService;

    @Autowired
    private SecurityClient securityClient;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.debug("hasPermission() - authentication: " + authentication + ", targetDomainObject: " + targetDomainObject + ", permission: " + permission);
        log.debug("targetDomainObject class: " + targetDomainObject.getClass());
        //TODO
        /*User user = userService.findUser(authentication.getName());

        if (targetDomainObject instanceof Page) {
            return securityService.hasUserPermissionToPage(user, (PermissionName) permission, (Page) targetDomainObject);
        }*/

        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId,
                                 String targetType, Object permission) {
        log.debug("hasPermission() - authentication: " + authentication + ", targetId: " + targetId + ", targetType: " + targetType + ", permission: " + permission);

        PlatformUser platformUser = (PlatformUser) authentication.getPrincipal();
        log.debug("platformWebUser" + platformUser.getId());

        boolean hasPermission = securityClient.hasPermission(platformUser.getUsername(), (Long) targetId, targetType, (String) permission);
        //TODO
        return hasPermission;
    }

}
