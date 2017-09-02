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

import com.abixen.platform.common.infrastructure.security.PlatformUser;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.application.service.SecurityService;
import com.abixen.platform.core.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

@Slf4j
public class PlatformAuditorAware implements AuditorAware<User> {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Override
    public User getCurrentAuditor() {
        log.debug("getCurrentAuditor()");

        PlatformUser authorizedUser = securityService.getAuthorizedUser();
        if (authorizedUser == null) {
            return null;
        }

        return userService.find(authorizedUser.getId());
    }
}