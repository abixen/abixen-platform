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

package com.abixen.platform.core.interfaces.web.common;

import com.abixen.platform.common.dto.FormErrorDto;
import com.abixen.platform.common.dto.FormValidationResultDto;
import com.abixen.platform.common.model.enumtype.UserLanguage;
import com.abixen.platform.common.util.ValidationUtil;
import com.abixen.platform.core.application.dto.UserDto;
import com.abixen.platform.core.application.form.UserChangePasswordForm;
import com.abixen.platform.core.application.form.UserForm;
import com.abixen.platform.core.application.form.UserRolesForm;
import com.abixen.platform.core.interfaces.web.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public abstract class AbstractUserController {

    private final UserFacade userFacade;

    public AbstractUserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserDto find(@PathVariable Long id) {
        log.debug("getUser() - id: " + id);

        return userFacade.find(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto create(@RequestBody @Valid UserForm userForm, BindingResult bindingResult) {
        log.debug("save() - userForm: " + userForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(userForm, formErrors);
        }

        UserForm createdUserForm = userFacade.create(userForm);

        return new FormValidationResultDto(createdUserForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
        log.debug("delete() - id: " + id);
        userFacade.delete(id);
        return new ResponseEntity(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto update(@PathVariable Long id, @RequestBody @Valid UserForm userForm, BindingResult bindingResult) {
        log.debug("update() - id: " + id + ", userForm: " + userForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(userForm, formErrors);
        }

        UserForm updatedUserForm = userFacade.update(userForm);

        return new FormValidationResultDto(updatedUserForm);
    }

    @RequestMapping(value = "/{id}/avatar/{hash}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long id, @PathVariable String hash) throws IOException {
        return userFacade.getAvatar(hash);
    }

    @RequestMapping(value = "/{id}/avatar", method = RequestMethod.POST)
    public UserDto updateAvatar(@PathVariable Long id, @RequestParam("avatarFile") MultipartFile avatarFile) throws IOException {
        return userFacade.updateAvatar(id, avatarFile);
    }

    @RequestMapping(value = "/{id}/roles", method = RequestMethod.GET)
    public UserRolesForm findRoles(@PathVariable Long id) {
        log.debug("getUserRoles() - id: " + id);

        return userFacade.findRoles(id);
    }

    @RequestMapping(value = "/{id}/password", method = RequestMethod.POST)
    public FormValidationResultDto changePassword(@PathVariable Long id, @RequestBody @Valid UserChangePasswordForm userChangePasswordForm, BindingResult bindingResult) {
        log.debug("changePassword() - id: " + id + ", changeUserPasswordForm: " + userChangePasswordForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(userChangePasswordForm, formErrors);
        }

        UserChangePasswordForm userChangePasswordFormResult;

        try {
            userChangePasswordFormResult = userFacade.changePassword(userChangePasswordForm);
        } catch (UsernameNotFoundException e) {
            List<FormErrorDto> formErrors = new ArrayList<>();
            FormErrorDto formErrorDto = new FormErrorDto("currentPassword", "WrongPassword", "Wrong password", userChangePasswordForm.getCurrentPassword());
            formErrors.add(formErrorDto);
            return new FormValidationResultDto(userChangePasswordForm, formErrors);
        }

        return new FormValidationResultDto(userChangePasswordFormResult);
    }

    @RequestMapping(value = "/selected-language/{selectedLanguage}", method = RequestMethod.PUT)
    public ResponseEntity<UserLanguage> updateSelectedLanguage(@PathVariable UserLanguage selectedLanguage) {
        log.debug("updateSelectedLanguage() for logged user : " + selectedLanguage);
        UserLanguage updatedSelectedLanguage = userFacade.updateSelectedLanguage(selectedLanguage);
        return new ResponseEntity(updatedSelectedLanguage, HttpStatus.OK);
    }
}