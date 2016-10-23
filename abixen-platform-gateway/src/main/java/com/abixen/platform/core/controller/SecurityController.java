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

package com.abixen.platform.core.controller;

import com.abixen.platform.core.security.PlatformWebUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;


@Controller
public class SecurityController {

    private final Logger log = LoggerFactory.getLogger(SecurityController.class.getName());

    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public PlatformWebUser user(Principal principal) {
        log.debug("user: " + principal);
        PlatformWebUser platformWebUser = (PlatformWebUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return platformWebUser;
    }


}
