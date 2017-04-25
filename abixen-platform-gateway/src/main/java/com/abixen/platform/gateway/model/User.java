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

package com.abixen.platform.gateway.model;

import com.abixen.platform.common.model.Model;
import com.abixen.platform.common.model.SimpleUserBase;
import com.abixen.platform.common.model.enumtype.UserGender;
import com.abixen.platform.common.model.enumtype.UserLanguage;
import com.abixen.platform.common.model.enumtype.UserState;

import java.util.Date;
import java.util.Set;


public class User extends Model implements SimpleUserBase<Role> {

    private Long id;
    private String username;
    private String password;
    private String screenName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String jobTitle;
    private Date birthday;
    private UserGender gender;
    private UserLanguage selectLanguage;
    private String avatarFileName;
    private String registrationIp;
    private UserState state;
    private Set<Role> roles;
    private String hashKey;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    @Override
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getJobTitle() {
        return jobTitle;
    }

    @Override
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public Date getBirthday() {
        return birthday;
    }

    @Override
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public UserGender getGender() {
        return gender;
    }

    @Override
    public void setGender(UserGender gender) {
        this.gender = gender;
    }

    @Override
    public UserLanguage getSelectedLanguage() {
        return selectLanguage;
    }

    @Override
    public void setSelectedLanguage(UserLanguage selectedLanguage) {
            this.selectLanguage = selectedLanguage;
    }

    @Override
    public String getAvatarFileName() {
        return avatarFileName;
    }

    @Override
    public void setAvatarFileName(String avatarFileName) {
        this.avatarFileName = avatarFileName;
    }

    @Override
    public String getRegistrationIp() {
        return registrationIp;
    }

    @Override
    public void setRegistrationIp(String registrationIp) {
        this.registrationIp = registrationIp;
    }

    @Override
    public UserState getState() {
        return state;
    }

    @Override
    public void setState(UserState state) {
        this.state = state;
    }

    @Override
    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String getHashKey() {
        return hashKey;
    }

    @Override
    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

}

