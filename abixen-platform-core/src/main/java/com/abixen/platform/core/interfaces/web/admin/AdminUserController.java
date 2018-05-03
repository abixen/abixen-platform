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

package com.abixen.platform.core.interfaces.web.admin;

import com.abixen.platform.common.application.dto.FormErrorDto;
import com.abixen.platform.common.application.dto.FormValidationResultDto;
import com.abixen.platform.common.infrastructure.util.ValidationUtil;
import com.abixen.platform.common.interfaces.web.page.PlatformPageImpl;
import com.abixen.platform.core.application.dto.UserDto;
import com.abixen.platform.core.application.form.UserRolesForm;
import com.abixen.platform.core.application.form.UserSearchForm;
import com.abixen.platform.core.application.service.UserManagementService;
import com.abixen.platform.core.interfaces.web.common.AbstractUserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/api/control-panel/users")
public class AdminUserController extends AbstractUserController {

    private final UserManagementService userManagementService;

    @Autowired
    public AdminUserController(UserManagementService userManagementService) {
        super(userManagementService);

        this.userManagementService = userManagementService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public PlatformPageImpl<UserDto> findAll(@PageableDefault(size = 1, page = 0) Pageable pageable, UserSearchForm userSearchForm) {
        log.debug("findAll()");

        final Page<UserDto> userDtos = userManagementService.findAllUsers(pageable, userSearchForm);

        return new PlatformPageImpl<>(userDtos);
    }

    @RequestMapping(value = "/{id}/roles", method = RequestMethod.PUT)
    public FormValidationResultDto<UserRolesForm> updateRoles(@PathVariable("id") Long id, @RequestBody @Valid UserRolesForm userRolesForm, BindingResult bindingResult) {
        log.debug("updateRoles() - id: " + id + ", userRolesForm: " + userRolesForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(userRolesForm, formErrors);
        }

        final UserRolesForm updatedUserRolesForm = userManagementService.updateUserRoles(userRolesForm);

        return new FormValidationResultDto<>(updatedUserRolesForm);
    }

    @RequestMapping(value = "/custom/username/{username}/", method = RequestMethod.GET)
    public UserDto find(@PathVariable("username") String username) {
        log.debug("getUserByUsername() - username: " + username);

        return userManagementService.findUser(username);
    }

}