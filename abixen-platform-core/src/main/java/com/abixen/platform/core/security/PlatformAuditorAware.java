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

package com.abixen.platform.core.security;

import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.service.SecurityService;
import com.abixen.platform.core.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;


public class PlatformAuditorAware implements AuditorAware<User> {

    private static Logger log = Logger.getLogger(PlatformAuditorAware.class.getName());

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

        return userService.findUser(authorizedUser.getId());
    }
}