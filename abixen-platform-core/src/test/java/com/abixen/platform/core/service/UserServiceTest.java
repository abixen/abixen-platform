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

package com.abixen.platform.core.service;

import com.abixen.platform.core.configuration.PlatformConfiguration;
import com.abixen.platform.core.form.UserChangePasswordForm;
import com.abixen.platform.core.model.enumtype.UserGender;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.util.UserBuilder;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformConfiguration.class)
public class UserServiceTest {

    static Logger log = Logger.getLogger(UserServiceTest.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private DomainBuilderService domainBuilderService;

    @Test
    public void createUser() {
        log.debug("createUser()");
        UserBuilder userBuilder = domainBuilderService.newUserBuilderInstance();
        userBuilder.credentials("username", "password");
        userBuilder.screenName("screenName");
        userBuilder.personalData("firstName", "middleName", "lastName");
        userBuilder.additionalData(new Date(), "jobTitle", UserGender.MALE);
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
        userBuilder.additionalData(new Date(), "jobTitle", UserGender.MALE);
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
        userBuilder.additionalData(new Date(), "jobTitle", UserGender.MALE);
        userBuilder.registrationIp("127.0.0.1");
        User user = userBuilder.build();
        userService.createUser(user);

        UserChangePasswordForm passwordForm = new UserChangePasswordForm();
        passwordForm.setCurrentPassword("someNotCorrectpassword");
        passwordForm.setNewPassword(newpassword);

        UserChangePasswordForm newPasswordForm = userService.changeUserPassword(user, passwordForm);
    }


    /*@Test
    public void updateUser() {
        log.debug("updateUser()");
        User user = userService.findUser("username");
        user.setFirstname("firstname2");
        userService.updateUser(user);
        User userAfterUpdate = userService.findUser("username");
        assertEquals("firstname2", userAfterUpdate.getFirstname());
    }*/


}
