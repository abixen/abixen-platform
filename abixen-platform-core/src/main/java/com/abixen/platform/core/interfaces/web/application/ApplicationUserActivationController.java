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

package com.abixen.platform.core.interfaces.web.application;

import com.abixen.platform.core.application.service.UserManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/user-activation")
public class ApplicationUserActivationController {

    private final UserManagementService userManagementService;

    @Autowired
    public ApplicationUserActivationController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    //FIXME - change mapping
    @RequestMapping(value = "/activate/{userHashKey}/", method = RequestMethod.GET)
    public void activate(@PathVariable String userHashKey) {
        log.debug("activate() - userHashKey: {}", userHashKey);

        userManagementService.activate(userHashKey);
    }

}