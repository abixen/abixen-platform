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

package com.abixen.platform.core.model.impl;

import com.abixen.platform.common.model.UserBase;
import com.abixen.platform.common.model.enumtype.UserGender;
import com.abixen.platform.common.model.enumtype.UserLanguage;
import com.abixen.platform.common.model.enumtype.UserState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@JsonSerialize(as = User.class)
@Entity
@Table(name = "user_")
@SequenceGenerator(sequenceName = "user_seq", name = "user_seq", allocationSize = 1)
public class User extends AuditingModel implements UserBase<Role> {

    /**
     *
     */
    private static final long serialVersionUID = -1247915331100609524L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "username", unique = true, length = USERNAME_MAX_LENGTH, nullable = false)
    private String username;

    @Column(name = "password", length = PASSWORD_MAX_LENGTH, nullable = false)
    private String password;

    @Column(name = "screen_name")
    private String screenName;

    @Column(name = "first_name", length = FIRST_NAME_MAX_LENGTH, nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", length = LAST_NAME_MAX_LENGTH, nullable = false)
    private String lastName;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "gender")
    private UserGender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "selected_language",  nullable = false)
    private UserLanguage selectedLanguage = UserLanguage.ENGLISH;

    @Column(name = "avatar_file_name")
    private String avatarFileName;

    @Column(name = "registration_ip", length = REGISTRATION_IP_MAX_LENGTH)
    private String registrationIp;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private UserState state;

    @Column(name = "hash_key", length = HASH_KEY_MAX_LENGTH)
    private String hashKey;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false, updatable = false)})
    private Set<Role> roles = new HashSet<>();

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
        return selectedLanguage;
    }

    @Override
    public void setSelectedLanguage(UserLanguage selectedLanguage) {
            this.selectedLanguage = selectedLanguage;
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
