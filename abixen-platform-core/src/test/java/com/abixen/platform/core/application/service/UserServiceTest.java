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

import com.abixen.platform.common.domain.model.enumtype.UserGender;
import com.abixen.platform.common.domain.model.enumtype.UserLanguage;
import com.abixen.platform.core.application.form.UserChangePasswordForm;
import com.abixen.platform.core.application.form.UserSearchForm;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.domain.model.UserBuilder;
import com.abixen.platform.core.domain.service.UserService;
import com.abixen.platform.core.infrastructure.configuration.properties.PlatformResourceConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PlatformResourceConfigurationProperties platformResourceConfigurationProperties;

    private File avatarFile;

    @Ignore
    @Test
    public void createUser() {
        log.debug("create()");
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.credentials("username", "password");
        userBuilder.screenName("screenName");
        userBuilder.personalData("firstName", "middleName", "lastName");
        userBuilder.additionalData(new Date(), "jobTitle", UserLanguage.ENGLISH, UserGender.MALE);
        userBuilder.registrationIp("127.0.0.1");
        User user = userBuilder.build();
        //userService.create(user);
        User userFromDB = userService.find("username");
        assertNotNull(userFromDB);
    }

    @Ignore
    @Test
    public void changeUserPasswordPositiveCase() {
        log.debug("changePassword() positive case");
        String newpassword = "newPassword";

        UserBuilder userBuilder = new UserBuilder();
        userBuilder.credentials("usernameA", "password");
        userBuilder.screenName("screenNameA");
        userBuilder.personalData("firstName", "middleName", "lastName");
        userBuilder.additionalData(new Date(), "jobTitle", UserLanguage.ENGLISH, UserGender.MALE);
        userBuilder.registrationIp("127.0.0.1");
        User user = userBuilder.build();
        //userService.create(user);

        UserChangePasswordForm passwordForm = new UserChangePasswordForm();
        passwordForm.setCurrentPassword("password");
        passwordForm.setNewPassword(newpassword);

        //UserChangePasswordForm newPasswordForm = userService.changePassword(user, passwordForm);
        User userFromDB = userService.find("usernameA");

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        assertNotNull(userFromDB);
        assertTrue(encoder.matches(newpassword, userFromDB.getPassword()));
    }

    @Ignore
    @Test(expected = UsernameNotFoundException.class)
    public void changeUserPasswordNegativeCase() {
        log.debug("changePassword() negative case");
        String newpassword = "newPassword";

        UserBuilder userBuilder = new UserBuilder();
        userBuilder.credentials("usernameB", "password");
        userBuilder.screenName("screenNameB");
        userBuilder.personalData("firstName", "middleName", "lastName");
        userBuilder.additionalData(new Date(), "jobTitle", UserLanguage.ENGLISH, UserGender.MALE);
        userBuilder.registrationIp("127.0.0.1");
        User user = userBuilder.build();
        //userService.create(user);

        UserChangePasswordForm passwordForm = new UserChangePasswordForm();
        passwordForm.setCurrentPassword("someNotCorrectpassword");
        passwordForm.setNewPassword(newpassword);

        //userService.changePassword(user, passwordForm);
    }

    @Ignore
    @Test
    public void changeUserAvatar() throws IOException {
        log.debug("changeAvatar() positive case");

        UserBuilder userBuilder = new UserBuilder();
        userBuilder.credentials("usernameC", "password");
        userBuilder.screenName("screenNameC");
        userBuilder.personalData("firstName", "middleName", "lastName");
        userBuilder.additionalData(new Date(), "jobTitle", UserLanguage.ENGLISH, UserGender.MALE);
        userBuilder.registrationIp("127.0.0.1");
        User user = userBuilder.build();
        //FIXME
        //user.setAvatarFileName("oldAvatarName");
        //userService.create(user);


        MultipartFile newAvatarFile = mock(MultipartFile.class);
        when(newAvatarFile.getName()).thenReturn("newAvatarFile");
        byte[] bytes = new byte[32];
        new Random().nextBytes(bytes);
        when(newAvatarFile.getBytes()).thenReturn(bytes);

        User updatedUser = null;
        //try {
        //updatedUser = userService.changeAvatar(user.getId(), newAvatarFile);
        // catch (IOException e) {
        //    e.printStackTrace();
        //}

        avatarFile = new File(platformResourceConfigurationProperties.getImageLibraryDirectory() + "/user-avatar/" + updatedUser.getAvatarFileName());

        assertNotNull(updatedUser);
        assertNotEquals(updatedUser.getAvatarFileName(), user.getAvatarFileName());
        assertTrue(avatarFile.exists());
        avatarFile.delete();
    }

    @Ignore
    @Test
    public void findAllUsers() {

        UserSearchForm searchForm = new UserSearchForm();
        searchForm.setSelectedLanguage(UserLanguage.ENGLISH);

        Pageable pageable = new PageRequest(0, 10, Sort.Direction.ASC, "firstName");
        Page<User> usersPage = userService.findAll(pageable, searchForm);
        log.debug("usersPage.getTotalElements(): {}", usersPage.getTotalElements());

        assertEquals(5, usersPage.getTotalElements());
    }

    @Ignore
    @Test
    public void updateSelectedLanguage() {

        UserBuilder userBuilder = new UserBuilder();
        userBuilder.credentials("usertestlang", "password");
        userBuilder.screenName("screentestlang");
        userBuilder.personalData("firstName", "middleName", "lastName");
        userBuilder.additionalData(new Date(), "jobTitle", UserLanguage.ENGLISH, UserGender.MALE);
        userBuilder.registrationIp("127.0.0.1");
        User user = userBuilder.build();
        //FIXME
        //user.setAvatarFileName("oldAvatarName");
        //User createdUser = userService.create(user);
        //userService.updateSelectedLanguage(createdUser.getId(), UserLanguage.POLISH);
        //User lookupUser = userService.find(createdUser.getId());

        //assertEquals(lookupUser.getSelectedLanguage(), UserLanguage.POLISH);
        //userService.delete(lookupUser.getId());
    }

}