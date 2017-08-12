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

import com.abixen.platform.core.infrastructure.configuration.PlatformConfiguration;
import com.abixen.platform.core.infrastructure.configuration.properties.PlatformTestResourceConfigurationProperties;
import com.abixen.platform.core.application.form.UserChangePasswordForm;
import com.abixen.platform.core.application.form.UserSearchForm;
import com.abixen.platform.common.model.enumtype.UserGender;
import com.abixen.platform.common.model.enumtype.UserLanguage;
import com.abixen.platform.core.domain.model.impl.User;
import com.abixen.platform.core.infrastructure.util.UserBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformConfiguration.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DomainBuilderService domainBuilderService;

    @Autowired
    private PlatformTestResourceConfigurationProperties platformResourceConfigurationProperties;

    private File avatarFile;

    @Test
    public void createUser() {
        log.debug("createUser()");
        UserBuilder userBuilder = domainBuilderService.newUserBuilderInstance();
        userBuilder.credentials("username", "password");
        userBuilder.screenName("screenName");
        userBuilder.personalData("firstName", "middleName", "lastName");
        userBuilder.additionalData(new Date(), "jobTitle", UserLanguage.ENGLISH, UserGender.MALE);
        userBuilder.registrationIp("127.0.0.1");
        User user = userBuilder.build();
        userService.createUser(user);
        User userFromDB = userService.findUser("username");
        assertNotNull(userFromDB);
    }

    @Test
    public void changeUserPasswordPositiveCase() {
        log.debug("changeUserPassword() positive case");
        String newpassword = "newPassword";

        UserBuilder userBuilder = domainBuilderService.newUserBuilderInstance();
        userBuilder.credentials("usernameA", "password");
        userBuilder.screenName("screenNameA");
        userBuilder.personalData("firstName", "middleName", "lastName");
        userBuilder.additionalData(new Date(), "jobTitle", UserLanguage.ENGLISH, UserGender.MALE);
        userBuilder.registrationIp("127.0.0.1");
        User user = userBuilder.build();
        userService.createUser(user);

        UserChangePasswordForm passwordForm = new UserChangePasswordForm();
        passwordForm.setCurrentPassword("password");
        passwordForm.setNewPassword(newpassword);

        UserChangePasswordForm newPasswordForm = userService.changeUserPassword(user, passwordForm);
        User userFromDB = userService.findUser("usernameA");

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        assertNotNull(userFromDB);
        assertTrue(encoder.matches(newpassword, userFromDB.getPassword()));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void changeUserPasswordNegativeCase() {
        log.debug("changeUserPassword() negative case");
        String newpassword = "newPassword";

        UserBuilder userBuilder = domainBuilderService.newUserBuilderInstance();
        userBuilder.credentials("usernameB", "password");
        userBuilder.screenName("screenNameB");
        userBuilder.personalData("firstName", "middleName", "lastName");
        userBuilder.additionalData(new Date(), "jobTitle", UserLanguage.ENGLISH, UserGender.MALE);
        userBuilder.registrationIp("127.0.0.1");
        User user = userBuilder.build();
        userService.createUser(user);

        UserChangePasswordForm passwordForm = new UserChangePasswordForm();
        passwordForm.setCurrentPassword("someNotCorrectpassword");
        passwordForm.setNewPassword(newpassword);

        userService.changeUserPassword(user, passwordForm);
    }

    @Test
    public void changeUserAvatar() throws IOException {
        log.debug("changeUserAvatar() positive case");

        UserBuilder userBuilder = domainBuilderService.newUserBuilderInstance();
        userBuilder.credentials("usernameC", "password");
        userBuilder.screenName("screenNameC");
        userBuilder.personalData("firstName", "middleName", "lastName");
        userBuilder.additionalData(new Date(), "jobTitle", UserLanguage.ENGLISH, UserGender.MALE);
        userBuilder.registrationIp("127.0.0.1");
        User user = userBuilder.build();
        user.setAvatarFileName("oldAvatarName");
        userService.createUser(user);


        MultipartFile newAvatarFile = mock(MultipartFile.class);
        when(newAvatarFile.getName()).thenReturn("newAvatarFile");
        byte[] bytes = new byte[32];
        new Random().nextBytes(bytes);
        when(newAvatarFile.getBytes()).thenReturn(bytes);

        User updatedUser = null;
        try {
            updatedUser = userService.changeUserAvatar(user.getId(), newAvatarFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        avatarFile = new File(platformResourceConfigurationProperties.getImageLibraryDirectory() + "/user-avatar/" + updatedUser.getAvatarFileName());

        assertNotNull(updatedUser);
        assertNotEquals(updatedUser.getAvatarFileName(), user.getAvatarFileName());
        assertTrue(avatarFile.exists());
        avatarFile.delete();
    }

    @Test
    public void findAllUsers() {

        UserSearchForm searchForm = new UserSearchForm();
        searchForm.setSelectedLanguage(UserLanguage.ENGLISH);

        Pageable pageable = new PageRequest(0, 10, Sort.Direction.ASC, "firstName");
        Page<User> usersPage = userService.findAllUsers(pageable, searchForm);
        log.debug("usersPage.getTotalElements(): {}", usersPage.getTotalElements());

        assertEquals(5, usersPage.getTotalElements());
    }

    @Test
    public void updateSelectedLanguage() {

        UserBuilder userBuilder = domainBuilderService.newUserBuilderInstance();
        userBuilder.credentials("usertestlang", "password");
        userBuilder.screenName("screentestlang");
        userBuilder.personalData("firstName", "middleName", "lastName");
        userBuilder.additionalData(new Date(), "jobTitle", UserLanguage.ENGLISH, UserGender.MALE);
        userBuilder.registrationIp("127.0.0.1");
        User user = userBuilder.build();
        user.setAvatarFileName("oldAvatarName");
        User createdUser = userService.createUser(user);
        userService.updateSelectedLanguage(createdUser.getId(), UserLanguage.POLISH);
        User lookupUser = userService.findUser(createdUser.getId());

        assertEquals(lookupUser.getSelectedLanguage(), UserLanguage.POLISH);
        userService.deleteUser(lookupUser.getId());
    }

}