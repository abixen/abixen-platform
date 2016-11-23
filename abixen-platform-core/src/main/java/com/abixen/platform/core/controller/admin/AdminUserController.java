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

package com.abixen.platform.core.controller.admin;

import com.abixen.platform.core.configuration.properties.AbstractPlatformResourceConfigurationProperties;
import com.abixen.platform.core.controller.common.AbstractUserController;
import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.form.UserRolesForm;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.service.MailService;
import com.abixen.platform.core.service.RoleService;
import com.abixen.platform.core.service.UserService;
import com.abixen.platform.core.util.ValidationUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/admin/users")
public class AdminUserController extends AbstractUserController {

    private static Logger log = Logger.getLogger(AdminUserController.class.getName());

    private final UserService userService;

    private final MailService mailService;

    private final RoleService roleService;

    private final AbstractPlatformResourceConfigurationProperties platformResourceConfigurationProperties;

    @Autowired
    public AdminUserController(UserService userService, MailService mailService, RoleService roleService, AbstractPlatformResourceConfigurationProperties platformResourceConfigurationProperties) {
        super(userService, mailService, roleService, platformResourceConfigurationProperties);
        this.userService = userService;
        this.mailService = mailService;
        this.roleService = roleService;
        this.platformResourceConfigurationProperties = platformResourceConfigurationProperties;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<User> getUsers(@PageableDefault(size = 1, page = 0) Pageable pageable) {
        log.debug("getUsers()");

        Page<User> users = userService.findAllUsers(pageable);
        for (User user : users) {
            log.debug("user: " + user);
        }

        return users;
    }

    @RequestMapping(value = "/{id}/roles", method = RequestMethod.PUT)
    public FormValidationResultDto updateUserRoles(@PathVariable("id") Long id, @RequestBody @Valid UserRolesForm userRolesForm, BindingResult bindingResult) {
        log.debug("updateUserRoles() - id: " + id + ", userRolesForm: " + userRolesForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(userRolesForm, formErrors);
        }

        User user = userService.buildUserRoles(userRolesForm);
        userService.updateUser(user);

        return new FormValidationResultDto(userRolesForm);
    }

    //:.+
    @RequestMapping(value = "/custom/username/{username}/", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable("username") String username) {
        log.debug("getUserByUsername() - username: " + username);
        User user = userService.findUser(username);
        log.debug("fetched user: " + user);
        return user;
    }
}
