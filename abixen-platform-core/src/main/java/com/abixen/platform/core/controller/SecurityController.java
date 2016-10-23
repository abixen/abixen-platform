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

import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.form.UserForm;
import com.abixen.platform.core.form.UserRolesForm;
import com.abixen.platform.core.model.SecurableModel;
import com.abixen.platform.core.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.Module;
import com.abixen.platform.core.model.impl.Role;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.security.PlatformUser;
import com.abixen.platform.core.service.*;
import com.abixen.platform.core.util.ValidationUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.abixen.platform.core.service.MailService;


@RestController
@RequestMapping(value = "/api/admin/securities")
public class SecurityController {

    static Logger log = Logger.getLogger(SecurityController.class.getName());

    @Autowired
    SecurityService securityService;

    @Autowired
    UserService userService;

    @Autowired
    ModuleService moduleService;


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
