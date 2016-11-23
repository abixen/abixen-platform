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

package com.abixen.platform.module.security;

import com.abixen.platform.core.security.PlatformUser;
import org.apache.log4j.Logger;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class PlatformAuditorAware implements AuditorAware<Long> {

    private final Logger log = Logger.getLogger(PlatformAuditorAware.class.getName());

    @Override
    public Long getCurrentAuditor() {
        log.debug("getCurrentAuditor()");

        PlatformUser authorizedUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            authorizedUser = (PlatformUser) authentication.getPrincipal();
        }

        if (authorizedUser == null) {
            return null;
        }

        return authorizedUser.getId();
    }
}