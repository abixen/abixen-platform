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

import com.abixen.platform.core.dto.UserRoleDto;
import com.abixen.platform.core.exception.UserActivationException;
import com.abixen.platform.core.form.UserChangePasswordForm;
import com.abixen.platform.core.form.UserForm;
import com.abixen.platform.core.form.UserRolesForm;
import com.abixen.platform.core.model.enumtype.UserState;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.repository.UserRepository;
import com.abixen.platform.core.service.DomainBuilderService;
import com.abixen.platform.core.service.PasswordGeneratorService;
import com.abixen.platform.core.service.RoleService;
import com.abixen.platform.core.service.UserService;
import com.abixen.platform.core.util.UserBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service
public class UserServiceImpl implements UserService {

    static Logger log = Logger.getLogger(UserServiceImpl.class.getName());


    @Resource
    private UserRepository userRepository;

    @Autowired
    DomainBuilderService domainBuilderService;

    @Autowired
    PasswordGeneratorService passwordGeneratorService;

    @Autowired
    RoleService roleService;

    @Override
    public String generateUserPassword() {
        return passwordGeneratorService.generate(12, 2, 8, 2);
    }

    @Override
    public User buildUser(UserForm userForm, String userPassword) {
        log.debug("buildUser() - userForm: " + userForm);

        log.debug("Generated password: " + userPassword);

        UserBuilder userBuilder = domainBuilderService.newUserBuilderInstance();
        userBuilder.credentials(userForm.getUsername(), userPassword);
        userBuilder.screenName(userForm.getScreenName());
        userBuilder.personalData(userForm.getFirstName(), userForm.getMiddleName(), userForm.getLastName());
        userBuilder.additionalData(userForm.getBirthday(), userForm.getJobTitle(), userForm.getGender());
        userBuilder.registrationIp("127.0.0.1");
        return userBuilder.build();
    }

    @Override
    public User createUser(User user) {
        log.debug("createUser() - user: " + user);
        return userRepository.save(user);
    }

    @Override
    public UserForm updateUser(UserForm userForm) {
        log.debug("updateUser() - userForm: " + userForm);

        User user = findUser(userForm.getId());
        user.setUsername(userForm.getUsername());
        user.setScreenName(userForm.getScreenName());
        user.setFirstName(userForm.getFirstName());
        user.setMiddleName(userForm.getMiddleName());
        user.setLastName(userForm.getLastName());
        user.setJobTitle(userForm.getJobTitle());
        user.setBirthday(userForm.getBirthday());
        user.setGender(userForm.getGender());

        return new UserForm(updateUser(user));
    }

    @Override
    public User updateUser(User user) {
        log.debug("updateUser() - user: " + user);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        log.debug("deleteUser() - id: " + id);
        userRepository.delete(id);
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        log.debug("findAllUsers() - pageable: " + pageable);
        return userRepository.findAll(pageable);
    }

    @Override
    public User findUser(Long id) {
        log.debug("findUser() - id: " + id);
        return userRepository.findOne(id);
    }

    @Override
    public User buildUserRoles(UserRolesForm userRolesForm) {
        log.debug("buildUserRoles() - userRolesForm: " + userRolesForm);

        User user = findUser(userRolesForm.getUser().getId());
        user.getRoles().clear();

        for (UserRoleDto userRoleDto : userRolesForm.getUserRoles()) {
            if (userRoleDto.isSelected()) {
                user.getRoles().add(roleService.findRole(userRoleDto.getRole().getId()));
            }
        }
        return user;
    }

    @Override
    public User findUser(String username) {
        log.debug("findUser() - username: " + username);
        return userRepository.findByUsername(username);
    }

    @Override
    public User activate(String userHashKey) {
        log.info("Activation user with hash key " + userHashKey);
        User user = userRepository.findByHashKey(userHashKey);

        if (user == null) {
            log.error("Cannot activate user with hash key " + userHashKey + ". Wrong hash key.");
            throw new UserActivationException("Cannot activate user because a hash key is wrong.");
        }

        if (user.getState().equals(UserState.ACTIVE)) {
            log.warn("Cannot activate user " + user.getUsername() + " with hash key " + userHashKey + ". User is active already.");
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

}
