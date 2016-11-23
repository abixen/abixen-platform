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

package com.abixen.platform.core.controller.common;

import com.abixen.platform.core.configuration.properties.AbstractPlatformResourceConfigurationProperties;
import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.form.UserChangePasswordForm;
import com.abixen.platform.core.form.UserForm;
import com.abixen.platform.core.form.UserRolesForm;
import com.abixen.platform.core.model.impl.Role;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.service.MailService;
import com.abixen.platform.core.service.RoleService;
import com.abixen.platform.core.service.UserService;
import com.abixen.platform.core.util.ValidationUtil;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class AbstractUserController {

    private final Logger log = Logger.getLogger(AbstractUserController.class.getName());

    private final UserService userService;

    private final MailService mailService;

    private final RoleService roleService;

    private final AbstractPlatformResourceConfigurationProperties platformResourceConfigurationProperties;

    public AbstractUserController(UserService userService, MailService mailService, RoleService roleService, AbstractPlatformResourceConfigurationProperties platformResourceConfigurationProperties) {
        this.userService = userService;
        this.mailService = mailService;
        this.roleService = roleService;
        this.platformResourceConfigurationProperties = platformResourceConfigurationProperties;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        log.debug("getUser() - id: " + id);

        User user = userService.findUser(id);
        return user;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createUser(@RequestBody @Valid UserForm userForm, BindingResult bindingResult) {
        log.debug("save() - userForm: " + userForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(userForm, formErrors);
        }

        String userPassword = userService.generateUserPassword();
        User user = userService.buildUser(userForm, userPassword);
        userService.createUser(user);

        Map<String, String> params = new HashMap<>();
        params.put("email", user.getUsername());
        params.put("password", userPassword);
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getLastName());
        params.put("accountActivationUrl", "http://localhost:8080/login#/?activation-key=" + user.getHashKey());

        //TODO
        mailService.sendMail(user.getUsername(), params, MailService.USER_ACCOUNT_ACTIVATION_MAIL, "activationMessageSubject");

        return new FormValidationResultDto(userForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") long id) {
        log.debug("delete() - id: " + id);
        userService.deleteUser(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateUser(@PathVariable Long id, @RequestBody @Valid UserForm userForm, BindingResult bindingResult) {
        log.debug("update() - id: " + id + ", userForm: " + userForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(userForm, formErrors);
        }

        UserForm userFormResult = userService.updateUser(userForm);
        return new FormValidationResultDto(userFormResult);
    }

    @RequestMapping(value = "/{id}/avatar/{hash}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getUserAvatar(@PathVariable Long id, @PathVariable String hash) throws IOException {
        InputStream in = null;
        try {
            in = new FileInputStream(platformResourceConfigurationProperties.getImageLibraryDirectory() + "/user-avatar/" + hash);
        } catch (FileNotFoundException e) {
            in = new FileInputStream(platformResourceConfigurationProperties.getImageLibraryDirectory() + "/user-avatar/avatar.png");
        }
        byte[] b = IOUtils.toByteArray(in);

        in.close();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(b, headers, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/{id}/avatar", method = RequestMethod.POST)
    public User updateUserAvatar(@PathVariable Long id, @RequestParam("avatarFile") MultipartFile avatarFile) throws IOException {
        return userService.changeUserAvatar(id, avatarFile);
    }

    @RequestMapping(value = "/{id}/roles", method = RequestMethod.GET)
    public UserRolesForm getUserRoles(@PathVariable Long id) {
        log.debug("getUserRoles() - id: " + id);

        User user = userService.findUser(id);
        List<Role> allRoles = roleService.findAllRoles();

        UserRolesForm userRolesForm = new UserRolesForm(user, allRoles);

        return userRolesForm;
    }

    @RequestMapping(value = "/{id}/password", method = RequestMethod.POST)
    public FormValidationResultDto changeUserPassword(@PathVariable Long id, @RequestBody @Valid UserChangePasswordForm userChangePasswordForm, BindingResult bindingResult) {
        log.debug("changeUserPassword() - id: " + id + ", changeUserPasswordForm: " + userChangePasswordForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(userChangePasswordForm, formErrors);
        }

        User user = userService.findUser(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        UserChangePasswordForm userChangePasswordFormResult;

        try {
            userChangePasswordFormResult = userService.changeUserPassword(user, userChangePasswordForm);

        } catch (UsernameNotFoundException e) {
            List<FormErrorDto> formErrors = new ArrayList<>();
            FormErrorDto formErrorDto = new FormErrorDto("currentPassword", "WrongPassword", "Wrong password", userChangePasswordForm.getCurrentPassword());
            formErrors.add(formErrorDto);
            return new FormValidationResultDto(userChangePasswordForm, formErrors);
        }

        Map<String, String> params = new HashMap<>();
        params.put("email", user.getUsername());
        params.put("password", user.getPassword());
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getLastName());

        mailService.sendMail(user.getUsername(), params, MailService.USER_PASSWORD_CHANGE_MAIL, "Password has been changed");

        return new FormValidationResultDto(userChangePasswordFormResult);
    }
}
