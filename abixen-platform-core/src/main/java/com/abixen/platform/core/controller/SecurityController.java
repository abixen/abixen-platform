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


import com.abixen.platform.common.model.SecurableModel;
import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.core.service.SecuribleModelService;
import com.abixen.platform.core.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/control-panel/securities")
public class SecurityController {

    private final SecurityService securityService;
    private final SecuribleModelService securibleModelService;

    @Autowired
    public SecurityController(SecurityService securityService,
                              SecuribleModelService securibleModelService) {
        this.securityService = securityService;
        this.securibleModelService = securibleModelService;
    }


    @RequestMapping(value = "/has-permission/{username}/{securableObjectId}/{aclClassName}/{permissionName}", method = RequestMethod.GET)
    public boolean hasPermission(@PathVariable String username, @PathVariable Long securableObjectId, @PathVariable AclClassName aclClassName, @PathVariable String permissionName) {
        SecurableModel securibleModel = securibleModelService.getSecuribleModel(securableObjectId, aclClassName);
        return securityService.hasPermission(username, securibleModel, permissionName);
    }

}