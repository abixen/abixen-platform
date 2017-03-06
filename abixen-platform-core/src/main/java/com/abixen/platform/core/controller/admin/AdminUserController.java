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
import com.abixen.platform.core.form.UserSearchForm;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.service.MailService;
import com.abixen.platform.core.service.RoleService;
import com.abixen.platform.core.service.SecurityService;
import com.abixen.platform.core.service.UserService;
import com.abixen.platform.core.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/api/control-panel/users")
public class AdminUserController extends AbstractUserController {

    private final UserService userService;

    @Autowired
    public AdminUserController(UserService userService, MailService mailService, RoleService roleService, SecurityService securityService, AbstractPlatformResourceConfigurationProperties platformResourceConfigurationProperties) {
        super(userService, mailService, roleService, securityService, platformResourceConfigurationProperties);
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<User> getUsers(@PageableDefault(size = 1, page = 0) Pageable pageable, UserSearchForm userSearchForm) {
        log.debug("getUsers()");

        Page<User> users = userService.findAllUsers(pageable, userSearchForm);
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

    @RequestMapping(value = "/custom/username/{username}/", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable("username") String username) {
        log.debug("getUserByUsername() - username: " + username);
        User user = userService.findUser(username);
        log.debug("fetched user: " + user);
        return user;
    }
}
