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

package com.abixen.platform.core.service.impl;

import com.abixen.platform.core.configuration.properties.AbstractPlatformResourceConfigurationProperties;
import com.abixen.platform.core.dto.UserRoleDto;
import com.abixen.platform.core.exception.UserActivationException;
import com.abixen.platform.core.form.UserChangePasswordForm;
import com.abixen.platform.core.form.UserForm;
import com.abixen.platform.core.form.UserRolesForm;
import com.abixen.platform.core.form.UserSearchForm;
import com.abixen.platform.common.model.enumtype.UserLanguage;
import com.abixen.platform.common.model.enumtype.UserState;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.repository.UserRepository;
import com.abixen.platform.common.security.PlatformUser;
import com.abixen.platform.core.service.DomainBuilderService;
import com.abixen.platform.core.service.PasswordGeneratorService;
import com.abixen.platform.core.service.RoleService;
import com.abixen.platform.core.service.UserService;
import com.abixen.platform.core.util.IpAddressUtil;
import com.abixen.platform.core.util.UserBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final int generatorLength = 12;
    private final int generatorNoOfCAPSAlpha = 2;
    private final int generatorNoOfDigits = 8;
    private final int generatorNoOfSpecialChars = 2;

    private final UserRepository userRepository;
    private final DomainBuilderService domainBuilderService;
    private final PasswordGeneratorService passwordGeneratorService;
    private final RoleService roleService;
    private final AbstractPlatformResourceConfigurationProperties platformResourceConfigurationProperties;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           DomainBuilderService domainBuilderService,
                           PasswordGeneratorService passwordGeneratorService,
                           RoleService roleService,
                           AbstractPlatformResourceConfigurationProperties platformResourceConfigurationProperties) {
        this.userRepository = userRepository;
        this.domainBuilderService = domainBuilderService;
        this.passwordGeneratorService = passwordGeneratorService;
        this.roleService = roleService;
        this.platformResourceConfigurationProperties = platformResourceConfigurationProperties;
    }

    @Override
    public String generateUserPassword() {
        return passwordGeneratorService.generate(generatorLength, generatorNoOfCAPSAlpha, generatorNoOfDigits, generatorNoOfSpecialChars);
    }

    @Override
    public User buildUser(UserForm userForm, String userPassword) {
        log.debug("buildUser() - userForm: {}", userForm);

        log.debug("Generated password: {}", userPassword);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        UserBuilder userBuilder = domainBuilderService.newUserBuilderInstance();
        userBuilder.credentials(userForm.getUsername(), userPassword);
        userBuilder.screenName(userForm.getScreenName());
        userBuilder.personalData(userForm.getFirstName(), userForm.getMiddleName(), userForm.getLastName());
        userBuilder.additionalData(userForm.getBirthday(), userForm.getJobTitle(), userForm.getSelectedLanguage(), userForm.getGender());
        userBuilder.registrationIp(IpAddressUtil.getClientIpAddress(request));
        return userBuilder.build();
    }

    @Override
    public User createUser(User user) {
        log.debug("createUser() - user: {}", user);
        return userRepository.save(user);
    }

    @Override
    public UserForm updateUser(UserForm userForm) {
        log.debug("updateUser() - userForm: {}", userForm);

        User user = findUser(userForm.getId());
        user.setUsername(userForm.getUsername());
        user.setScreenName(userForm.getScreenName());
        user.setFirstName(userForm.getFirstName());
        user.setMiddleName(userForm.getMiddleName());
        user.setLastName(userForm.getLastName());
        user.setJobTitle(userForm.getJobTitle());
        user.setSelectedLanguage(userForm.getSelectedLanguage());
        user.setBirthday(userForm.getBirthday());
        user.setGender(userForm.getGender());

        return new UserForm(updateUser(user));
    }

    @Override
    public User updateUser(User user) {
        log.debug("updateUser() - user: {}", user);

        User updatedUser = userRepository.save(user);
        updatePlatformUser(user);

        return updatedUser;
    }

    @Override
    public void deleteUser(Long id) {
        log.debug("deleteUser() - id: {}", id);
        userRepository.delete(id);
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable, UserSearchForm userSearchForm) {
        log.debug("findAllUsers() - pageable: {}", pageable);
        return userRepository.findAll(pageable, userSearchForm);
    }

    @Override
    public User findUser(Long id) {
        log.debug("findUser() - id: {}", id);
        return userRepository.findOne(id);
    }

    @Override
    public User buildUserRoles(UserRolesForm userRolesForm) {
        log.debug("buildUserRoles() - userRolesForm: {}", userRolesForm);

        User user = findUser(userRolesForm.getUser().getId());
        user.getRoles().clear();

        userRolesForm.getUserRoles().stream().filter(UserRoleDto::isSelected).forEach(userRoleDto -> {
            user.getRoles().add(roleService.findRole(userRoleDto.getRole().getId()));
        });

        return user;
    }

    @Override
    public User findUser(String username) {
        log.debug("findUser() - username: {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public User activate(String userHashKey) {
        log.info("Activation user with hash key {}", userHashKey);
        User user = userRepository.findByHashKey(userHashKey);

        if (user == null) {
            log.error("Cannot activate user with hash key {}. Wrong hash key.", userHashKey);
            throw new UserActivationException("Cannot activate user because a hash key is wrong.");
        }

        if (user.getState().equals(UserState.ACTIVE)) {
            log.warn("Cannot activate user {} with hash key {}. User is active already.", user.getUsername(), userHashKey);
            throw new UserActivationException("Cannot activate user because the user is active already.");
        }

        user.setState(UserState.ACTIVE);
        return updateUser(user);
    }

    @Override
    public UserChangePasswordForm changeUserPassword(User user, UserChangePasswordForm userChangePasswordForm) {
        log.info("changeUserPassword()");

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = userChangePasswordForm.getCurrentPassword();
        if (!encoder.matches(password, user.getPassword())) {
            throw new UsernameNotFoundException("Wrong username and / or password.");
        }

        user.setPassword(encoder.encode(userChangePasswordForm.getNewPassword()));
        updateUser(user);

        return userChangePasswordForm;
    }

    @Override
    public User changeUserAvatar(Long userId, MultipartFile avatarFile) throws IOException {
        User user = findUser(userId);
        File currentAvatarFile = new File(platformResourceConfigurationProperties.getImageLibraryDirectory() + "/user-avatar/" + user.getAvatarFileName());
        if (currentAvatarFile.exists()) {
            if (!currentAvatarFile.delete()) {
                throw new FileExistsException();
            }
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String newAvatarFileName = encoder.encode(avatarFile.getName() + new Date().getTime()).replaceAll("\"", "s").replaceAll("/", "a").replace(".", "sde");
        File newAvatarFile = new File(platformResourceConfigurationProperties.getImageLibraryDirectory() + "/user-avatar/" + newAvatarFileName);
        FileOutputStream out = new FileOutputStream(newAvatarFile);
        out.write(avatarFile.getBytes());
        out.close();
        user.setAvatarFileName(newAvatarFileName);
        updateUser(user);
        return findUser(userId);
    }

    @Override
    public UserLanguage updateSelectedLanguage(Long userId, UserLanguage selectedLanguage) {
        User user = findUser(userId);
        user.setSelectedLanguage(selectedLanguage);
        updateUser(user);
        return selectedLanguage;
    }

    private void updatePlatformUser(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PlatformUser currentPlatformUser = null;

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            currentPlatformUser = (PlatformUser) authentication.getPrincipal();
        }

        if (currentPlatformUser != null && currentPlatformUser.getId().equals(user.getId())) {
            log.info("Update of signed in user {}", currentPlatformUser.getUsername());

            PlatformUser newPlatformUser = new PlatformUser(user.getUsername(),
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