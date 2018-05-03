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

package com.abixen.platform.core.application.service;

import com.abixen.platform.common.domain.model.enumtype.UserLanguage;
import com.abixen.platform.common.infrastructure.annotation.PlatformApplicationService;
import com.abixen.platform.common.infrastructure.security.PlatformUser;
import com.abixen.platform.core.application.converter.RoleToRoleDtoConverter;
import com.abixen.platform.core.application.converter.UserToUserDtoConverter;
import com.abixen.platform.core.application.dto.RoleDto;
import com.abixen.platform.core.application.dto.UserDto;
import com.abixen.platform.core.application.dto.UserRoleDto;
import com.abixen.platform.core.application.form.UserChangePasswordForm;
import com.abixen.platform.core.application.form.UserForm;
import com.abixen.platform.core.application.form.UserRolesForm;
import com.abixen.platform.core.application.form.UserSearchForm;
import com.abixen.platform.core.application.util.IpAddressUtil;
import com.abixen.platform.core.domain.model.Role;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.domain.service.RoleService;
import com.abixen.platform.core.domain.service.UserService;
import com.abixen.platform.core.infrastructure.configuration.properties.PlatformResourceConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@PlatformApplicationService
public class UserManagementService {

    private final UserService userService;
    private final MailService mailService;
    private final MessageSource messageSource;
    private final UserToUserDtoConverter userToUserDtoConverter;
    private final PlatformResourceConfigurationProperties platformResourceConfigurationProperties;
    private final SecurityService securityService;
    private final RoleService roleService;
    private final RoleToRoleDtoConverter roleToRoleDtoConverter;

    @Autowired
    public UserManagementService(UserService userService,
                                 MailService mailService,
                                 MessageSource messageSource,
                                 UserToUserDtoConverter userToUserDtoConverter,
                                 PlatformResourceConfigurationProperties platformResourceConfigurationProperties,
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

    public UserDto findUser(final Long id) {
        log.debug("findUser() - id: {}", id);

        User user = userService.find(id);

        return userToUserDtoConverter.convert(user);
    }

    public UserDto findUser(final String username) {
        log.debug("findUser() - username: {}", username);

        User user = userService.find(username);

        return userToUserDtoConverter.convert(user);
    }

    public Page<UserDto> findAllUsers(final Pageable pageable, final UserSearchForm userSearchForm) {
        log.debug("findAllUsers() - userSearchForm: {}, pageable: {}", userSearchForm, pageable);

        Page<User> users = userService.findAll(pageable, userSearchForm);

        return userToUserDtoConverter.convertToPage(users);
    }

    public UserRolesForm findUserRoles(final Long id) {
        log.debug("findUserRoles() - id: {}", id);

        final User user = userService.find(id);
        final List<Role> allRoles = roleService.findAll();

        final UserDto userDto = userToUserDtoConverter.convert(user);
        final List<RoleDto> allRolesDto = roleToRoleDtoConverter.convertToList(allRoles);
        final UserRolesForm userRolesForm = new UserRolesForm(userDto, allRolesDto);

        return userRolesForm;
    }

    public UserForm createUser(final UserForm userForm) {
        log.debug("createUser() - userForm: {}", userForm);

        final String userPassword = userService.generatePassword();

        //FIXME - move to IpAddressUtil?
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        final User user =  User.builder()
                .credentials(userForm.getUsername(), userPassword)
                .screenName(userForm.getScreenName())
                .personalData(userForm.getFirstName(), userForm.getMiddleName(), userForm.getLastName())
                .additionalData(userForm.getBirthday(), userForm.getJobTitle(), userForm.getSelectedLanguage(), userForm.getGender())
                .registrationIp(IpAddressUtil.getClientIpAddress(request))
                .build();

        final User createdUser = userService.create(user);
        final UserForm createdUserForm = new UserForm(createdUser);

        final Map<String, String> params = new HashMap<>();
        params.put("email", createdUser.getUsername());
        params.put("password", userPassword);
        params.put("firstName", createdUser.getFirstName());
        params.put("lastName", createdUser.getLastName());
        //FIXME - parametrize url
        params.put("accountActivationUrl", "http://localhost:8080/login#/?activation-key=" + createdUser.getHashKey());

        String subject = messageSource.getMessage(
                "email.userAccountActivation.subject",
                null,
                LocaleUtils.toLocale(createdUserForm.getSelectedLanguage().getSelectedLanguage().toLowerCase()));

        mailService.sendMail(
                createdUser.getUsername(),
                params,
                MailService.USER_ACCOUNT_ACTIVATION_MAIL + "_" + createdUserForm.getSelectedLanguage().getSelectedLanguage().toLowerCase(),
                subject);

        return createdUserForm;
    }

    public UserForm updateUser(final UserForm userForm) {
        log.debug("updateUser() - userForm: {}", userForm);

        final User user = userService.find(userForm.getId());
        user.changeUsername(userForm.getUsername());
        user.changeScreenName(userForm.getScreenName());
        user.changePersonalData(userForm.getFirstName(), userForm.getMiddleName(), userForm.getLastName());
        user.changeAdditionalData(userForm.getBirthday(), userForm.getJobTitle(), userForm.getSelectedLanguage(), userForm.getGender());

        final User updatedUser = updateUser(user);

        return new UserForm(updatedUser);
    }

    public UserRolesForm updateUserRoles(final UserRolesForm userRolesForm) {
        log.debug("updateUserRoles() - userRolesForm: {}", userRolesForm);

        final User user = userService.find(userRolesForm.getUser().getId());

        final Set<Role> newRoles = userRolesForm.getUserRoles()
                .stream()
                .filter(UserRoleDto::isSelected)
                .map(userRoleDto -> roleService.find(userRoleDto.getRole().getId()))
                .collect(Collectors.toSet());

        user.changeRoles(newRoles);

        final User updatedUser = updateUser(user);
        final List<Role> allRoles = roleService.findAll();

        final UserDto updatedUserDto = userToUserDtoConverter.convert(updatedUser);
        final List<RoleDto> allRolesDto = roleToRoleDtoConverter.convertToList(allRoles);

        return new UserRolesForm(updatedUserDto, allRolesDto);
    }

    public void deleteUser(final Long id) {
        log.debug("deleteUser() - id: {}", id);

        userService.delete(id);
    }

    public ResponseEntity<byte[]> getUserAvatar(String hash) throws IOException {
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

        return new ResponseEntity(b, headers, HttpStatus.CREATED);
    }

    public UserDto updateUserAvatar(final Long id, final MultipartFile avatarFile) throws IOException {
        log.debug("updateUserAvatar() - id: {}", id);

        final User user = userService.find(id);
        user.changeAvatar(platformResourceConfigurationProperties.getImageLibraryDirectory(), avatarFile);

        final User updatedUser = updateUser(user);

        return userToUserDtoConverter.convert(updatedUser);
    }

    public UserChangePasswordForm changeUserPassword(final UserChangePasswordForm userChangePasswordForm) {
        log.debug("changeUserPassword() - userChangePasswordForm: {}", userChangePasswordForm);

        final User user = userService.find(userChangePasswordForm.getId());

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        user.changePassword(userChangePasswordForm.getCurrentPassword(), userChangePasswordForm.getNewPassword());

        updateUser(user);

        final Map<String, String> params = new HashMap<>();
        params.put("email", user.getUsername());
        params.put("password", userChangePasswordForm.getNewPassword());
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getLastName());

        final String subject = messageSource.getMessage(
                "email.userPasswordChanged.subject",
                null,
                LocaleUtils.toLocale(user.getSelectedLanguage().getSelectedLanguage().toLowerCase()));

        mailService.sendMail(
                user.getUsername(),
                params,
                MailService.USER_PASSWORD_CHANGE_MAIL + "_" + user.getSelectedLanguage().getSelectedLanguage().toLowerCase(),
                subject);

        //FIXME - make return type void
        return userChangePasswordForm;
    }

    public UserLanguage updateUserSelectedLanguage(final UserLanguage selectedLanguage) {
        log.debug("updateUserSelectedLanguage() - selectedLanguage: {}", selectedLanguage);

        final User user = userService.find(securityService.getAuthorizedUser().getId());
        user.changeLanguage(selectedLanguage);

        final User updatedUser = updateUser(user);

        return updatedUser.getSelectedLanguage();
    }

    public void activate(String userHashKey) {
        log.debug("activate() - userHashKey: {}", userHashKey);

        userService.activate(userHashKey);
    }

    private User updateUser(final User user) {
        final User updatedUser = userService.update(user);

        updatePlatformUser(user);

        return updatedUser;
    }

    private void updatePlatformUser(User user) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PlatformUser currentPlatformUser = null;

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            currentPlatformUser = (PlatformUser) authentication.getPrincipal();
        }

        if (currentPlatformUser != null && currentPlatformUser.getId().equals(user.getId())) {
            log.info("Update of signed in user {}", currentPlatformUser.getUsername());

            final PlatformUser newPlatformUser = new PlatformUser(user.getUsername(),
                    user.getPassword(),
                    currentPlatformUser.isEnabled(),
                    currentPlatformUser.isAccountNonExpired(),
                    currentPlatformUser.isCredentialsNonExpired(),
                    currentPlatformUser.isAccountNonLocked(),
                    currentPlatformUser.getAuthorities(),
                    user.getFirstName(),
                    user.getLastName(),
                    currentPlatformUser.isAdmin(),
                    user.getId(),
                    user.getSelectedLanguage(),
                    user.getAvatarFileName());

            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(newPlatformUser, newPlatformUser.getPassword(), newPlatformUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        }
    }

}