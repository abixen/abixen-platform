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

import com.abixen.platform.core.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.Module;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.service.ModuleService;
import com.abixen.platform.core.service.SecurityService;
import com.abixen.platform.core.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import com.abixen.platform.core.service.MailService;


@RestController
@RequestMapping(value = "/api/admin/securities")
public class SecurityController {

    private static Logger log = Logger.getLogger(SecurityController.class.getName());

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;


    @RequestMapping(value = "/has-permission/{username}/{securableObjectId}/{securableObjectClassName}/{permissionName}", method = RequestMethod.GET)
    public boolean hasPermission(@PathVariable String username, @PathVariable Long securableObjectId, @PathVariable String securableObjectClassName, @PathVariable String permissionName) {
        log.debug("hasPermission() - username: " + username + ", securableObjectId: " + securableObjectId + ", securableObjectClassName: " + securableObjectClassName + ", permissionName: " + permissionName);

        User user = userService.findUser(username);

        Module module = moduleService.findModule(securableObjectId);

        boolean hasPermission = securityService.hasUserPermissionToObject(user, PermissionName.valueOf(permissionName), module);
        log.debug("hasPermission: " + hasPermission);
        // HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.setLocation(location);
        //responseHeaders.set("MyResponseHeader", "MyValue");
        //return new ResponseEntity<Boolean>();
        return hasPermission;
    }


}
