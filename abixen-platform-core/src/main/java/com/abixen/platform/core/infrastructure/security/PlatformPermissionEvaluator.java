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

package com.abixen.platform.core.infrastructure.security;

import com.abixen.platform.core.domain.model.SecurableModel;
import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.application.service.SecuribleModelService;
import com.abixen.platform.core.application.service.SecurityService;
import com.abixen.platform.core.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

@Slf4j
@Component
@Lazy
@Scope(proxyMode = TARGET_CLASS)
public class PlatformPermissionEvaluator implements PermissionEvaluator {

    private SecurityService securityService;
    private SecuribleModelService securibleModelService;
    private UserService userService;

    @Autowired
    public PlatformPermissionEvaluator(SecurityService securityService,
                                       SecuribleModelService securibleModelService,
                                       UserService userService) {
        this.securityService = securityService;
        this.securibleModelService = securibleModelService;
        this.userService = userService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.debug("hasPermission() - authentication: " + authentication + ", targetDomainObject: " + targetDomainObject + ", permission: " + permission);
        log.debug("targetDomainObject class: " + targetDomainObject.getClass());
        //TODO
        User user = userService.find(authentication.getName());

        if (targetDomainObject instanceof String) {
            return securityService.hasUserPermissionToClass(user, PermissionName.valueOf((String) permission), (String) targetDomainObject);
        } else {
            return securityService.hasUserPermissionToObject(user, PermissionName.valueOf((String) permission), (SecurableModel) targetDomainObject);
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId,
                                 String targetType, Object permission) {
        log.debug("hasPermission() - authentication: " + authentication + ", targetId: " + targetId + ", targetType: " + targetType + ", permission: " + permission);

        User user = userService.find(authentication.getName());

        if (targetId == null) {
            return securityService.hasUserPermissionToClass(user, PermissionName.valueOf((String) permission), targetType);
        }

        SecurableModel securibleModel = securibleModelService.getSecuribleModel((Long) targetId, AclClassName.getByName(targetType));

        return securityService.hasUserPermissionToObject(user, PermissionName.valueOf((String) permission), securibleModel);
    }

}