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

package com.abixen.platform.core.controller.application;

import com.abixen.platform.core.configuration.properties.AbstractPlatformResourceConfigurationProperties;
import com.abixen.platform.core.controller.common.AbstractUserController;
import com.abixen.platform.core.converter.RoleToRoleDtoConverter;
import com.abixen.platform.core.converter.UserToUserDtoConverter;
import com.abixen.platform.core.service.MailService;
import com.abixen.platform.core.service.RoleService;
import com.abixen.platform.core.service.SecurityService;
import com.abixen.platform.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/application/users")
public class ApplicationUserController extends AbstractUserController {

    @Autowired
    public ApplicationUserController(UserService userService,
                                     MailService mailService,
                                     RoleService roleService,
                                     SecurityService securityService,
                                     AbstractPlatformResourceConfigurationProperties platformResourceConfigurationProperties,
                                     MessageSource messageSource,
                                     UserToUserDtoConverter userToUserDtoConverter,
                                     RoleToRoleDtoConverter roleToRoleDtoConverter) {
        super(userService,
                mailService,
                roleService,
                securityService,
                platformResourceConfigurationProperties,
                messageSource,
                userToUserDtoConverter,
                roleToRoleDtoConverter);
    }
}