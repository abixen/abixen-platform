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
import com.abixen.platform.core.converter.RoleToRoleDtoConverter;
import com.abixen.platform.core.converter.UserToUserDtoConverter;
import com.abixen.platform.common.dto.FormErrorDto;
import com.abixen.platform.common.dto.FormValidationResultDto;
import com.abixen.platform.core.dto.UserDto;
import com.abixen.platform.core.form.UserRolesForm;
import com.abixen.platform.core.form.UserSearchForm;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.service.MailService;
import com.abixen.platform.core.service.RoleService;
import com.abixen.platform.core.service.SecurityService;
import com.abixen.platform.core.service.UserService;
import com.abixen.platform.common.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    private final UserToUserDtoConverter userToUserDtoConverter;

    @Autowired
    public AdminUserController(UserService userService,
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

        this.userService = userService;
        this.userToUserDtoConverter = userToUserDtoConverter;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<UserDto> getUsers(@PageableDefault(size = 1, page = 0) Pageable pageable, UserSearchForm userSearchForm) {
        log.debug("getUsers()");

        Page<User> users = userService.findAllUsers(pageable, userSearchForm);
        Page<UserDto> userDtos = userToUserDtoConverter.convertToPage(users);

        return userDtos;
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
    public UserDto getUserByUsername(@PathVariable("username") String username) {
        log.debug("getUserByUsername() - username: " + username);
        User user = userService.findUser(username);

        UserDto userDto = userToUserDtoConverter.convert(user);
        log.debug("fetched user: {}", userDto);
        return userDto;
    }
}
