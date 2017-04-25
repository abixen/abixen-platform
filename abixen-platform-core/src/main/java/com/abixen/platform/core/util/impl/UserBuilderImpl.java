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

package com.abixen.platform.core.util.impl;


import com.abixen.platform.common.model.enumtype.UserGender;
import com.abixen.platform.common.model.enumtype.UserLanguage;
import com.abixen.platform.common.model.enumtype.UserState;
import com.abixen.platform.core.model.impl.Role;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.common.util.EntityBuilder;
import com.abixen.platform.core.util.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class UserBuilderImpl extends EntityBuilder<User> implements UserBuilder {

    @Override
    protected void initProduct() {
        this.product = new User();
    }

    @Override
    protected User assembleProduct() {
        return this.product;
    }

    public UserBuilder credentials(String username, String password) {
        this.product.setUsername(username);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.product.setPassword(encoder.encode(password));
        this.product.setHashKey(encoder.encode(username + password + new Date()).replace("/", "."));
        this.product.setState(UserState.CREATED);
        return this;
    }

    @Override
    public UserBuilder screenName(String screenName) {
        this.product.setScreenName(screenName);
        return this;
    }

    public UserBuilder personalData(String firstName, String middleName, String lastName) {
        this.product.setFirstName(firstName);
        this.product.setMiddleName(middleName);
        this.product.setLastName(lastName);
        return this;
    }

    @Override
    public UserBuilder additionalData(Date birthday, String jobTitle, UserLanguage language, UserGender gender) {
        this.product.setBirthday(birthday);
        this.product.setJobTitle(jobTitle);
        this.product.setSelectedLanguage(language);
        this.product.setGender(gender);
        return this;
    }

    public UserBuilder roles(Role... roles) {
        Set<Role> rolesSet = new HashSet<>();
        for (Role role : roles) {
            rolesSet.add(role);
        }
        this.product.setRoles(rolesSet);
        return this;
    }

    public UserBuilder registrationIp(String registrationIp) {
        this.product.setRegistrationIp(registrationIp);
        return this;
    }

}
