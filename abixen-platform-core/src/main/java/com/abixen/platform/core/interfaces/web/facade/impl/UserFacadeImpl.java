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

package com.abixen.platform.core.interfaces.web.facade.impl;

import com.abixen.platform.common.model.enumtype.UserLanguage;
import com.abixen.platform.core.application.dto.RoleDto;
import com.abixen.platform.core.application.dto.UserDto;
import com.abixen.platform.core.application.form.UserChangePasswordForm;
import com.abixen.platform.core.application.form.UserForm;
import com.abixen.platform.core.application.form.UserRolesForm;
import com.abixen.platform.core.application.form.UserSearchForm;
import com.abixen.platform.core.application.service.MailService;
import com.abixen.platform.core.application.service.RoleService;
import com.abixen.platform.core.application.service.SecurityService;
import com.abixen.platform.core.application.service.UserService;
import com.abixen.platform.core.domain.model.Role;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.infrastructure.configuration.properties.AbstractPlatformResourceConfigurationProperties;
import com.abixen.platform.core.interfaces.web.facade.UserFacade;
import com.abixen.platform.core.interfaces.web.facade.converter.RoleToRoleDtoConverter;
import com.abixen.platform.core.interfaces.web.facade.converter.UserToUserDtoConverter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final MailService mailService;
    private final MessageSource messageSource;
    private final UserToUserDtoConverter userToUserDtoConverter;
    private final AbstractPlatformResourceConfigurationProperties platformResourceConfigurationProperties;
    private final SecurityService securityService;
    private final RoleService roleService;
    private final RoleToRoleDtoConverter roleToRoleDtoConverter;

    @Autowired
    public UserFacadeImpl(UserService userService,
                          MailService mailService,
                          MessageSource messageSource,
                          UserToUserDtoConverter userToUserDtoConverter,
                          AbstractPlatformResourceConfigurationProperties platformResourceConfigurationProperties,
                          SecurityService securityService,
                          RoleService roleService,
                          RoleToRoleDtoConverter roleToRoleDtoConverter) {
        this.userService = userService;
        this.mailService = mailService;
        this.messageSource = messageSource;
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.platformResourceConfigurationProperties = platformResourceConfigurationProperties;
        this.securityService = securityService;
        this.roleService = roleService;
        this.roleToRoleDtoConverter = roleToRoleDtoConverter;
    }

    @Override
    public UserDto find(Long id) {
        User user = userService.find(id);

        return userToUserDtoConverter.convert(user);
    }

    @Override
    public UserDto find(String username) {
        User user = userService.find(username);

        return userToUserDtoConverter.convert(user);
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable, UserSearchForm userSearchForm) {
        Page<User> users = userService.findAll(pageable, userSearchForm);

        return userToUserDtoConverter.convertToPage(users);
    }

    @Override
    public UserRolesForm findRoles(Long id) {
        User user = userService.find(id);
        List<Role> allRoles = roleService.findAll();

        UserDto userDto = userToUserDtoConverter.convert(user);
        List<RoleDto> allRolesDto = roleToRoleDtoConverter.convertToList(allRoles);
        UserRolesForm userRolesForm = new UserRolesForm(userDto, allRolesDto);

        return userRolesForm;
    }

    @Override
    public UserForm create(final UserForm userForm) {
        String userPassword = userService.generatePassword();
        User createdUser = userService.create(userForm, userPassword);
        userForm.setId(createdUser.getId());

        Map<String, String> params = new HashMap<>();
        params.put("email", createdUser.getUsername());
        params.put("password", userPassword);
        params.put("firstName", createdUser.getFirstName());
        params.put("lastName", createdUser.getLastName());
        //FIXME - parametrize url
        params.put("accountActivationUrl", "http://localhost:8080/login#/?activation-key=" + createdUser.getHashKey());

        String subject = messageSource.getMessage(
                "email.userAccountActivation.subject",
                null,
                LocaleUtils.toLocale(userForm.getSelectedLanguage().getSelectedLanguage().toLowerCase()));

        mailService.sendMail(
                createdUser.getUsername(),
                params,
                MailService.USER_ACCOUNT_ACTIVATION_MAIL + "_" + userForm.getSelectedLanguage().getSelectedLanguage().toLowerCase(),
                subject);

        return userForm;
    }

    @Override
    public UserForm update(UserForm userForm) {
        return userService.update(userForm);
    }

    @Override
    public UserRolesForm updateRoles(UserRolesForm userRolesForm) {
        userService.updateRoles(userRolesForm);

        return userRolesForm;
    }

    @Override
    public void delete(Long id) {
        userService.delete(id);
    }

    @Override
    public ResponseEntity<byte[]> getAvatar(String hash) throws IOException {
        InputStream in;
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

    @Override
    public UserDto updateAvatar(Long id, MultipartFile avatarFile) throws IOException {
        User user = userService.changeAvatar(id, avatarFile);

        return userToUserDtoConverter.convert(user);
    }

    @Override
    public UserChangePasswordForm changePassword(UserChangePasswordForm userChangePasswordForm) {
        User user = userService.find(userChangePasswordForm.getId());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        userService.changePassword(user, userChangePasswordForm);

        Map<String, String> params = new HashMap<>();
        params.put("email", user.getUsername());
        params.put("password", userChangePasswordForm.getNewPassword());
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getLastName());

        String subject = messageSource.getMessage(
                "email.userPasswordChanged.subject",
                null,
                LocaleUtils.toLocale(user.getSelectedLanguage().getSelectedLanguage().toLowerCase()));

        mailService.sendMail(
                user.getUsername(),
                params,
                MailService.USER_PASSWORD_CHANGE_MAIL + "_" + user.getSelectedLanguage().getSelectedLanguage().toLowerCase(),
                subject);

        return userChangePasswordForm;
    }

    @Override
    public UserLanguage updateSelectedLanguage(UserLanguage selectedLanguage) {
        return userService.updateSelectedLanguage(securityService.getAuthorizedUser().getId(), selectedLanguage);
    }
}