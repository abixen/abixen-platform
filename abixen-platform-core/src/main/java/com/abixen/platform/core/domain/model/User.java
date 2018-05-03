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

package com.abixen.platform.core.domain.model;

import com.abixen.platform.common.domain.model.EntityBuilder;
import com.abixen.platform.common.domain.model.enumtype.UserGender;
import com.abixen.platform.common.domain.model.enumtype.UserLanguage;
import com.abixen.platform.common.domain.model.enumtype.UserState;
import com.abixen.platform.core.domain.exception.UserActivationException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.io.FileExistsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


//FIXME - remove it
@JsonSerialize(as = User.class)
@Entity
@Table(name = "user_")
@SequenceGenerator(sequenceName = "user_seq", name = "user_seq", allocationSize = 1)
public final class User extends AuditingModel {

    public static final int USERNAME_MAX_LENGTH = 32;
    public static final int USERNAME_MIN_LENGTH = 3;
    public static final int PASSWORD_MAX_LENGTH = 60;
    public static final int FIRST_NAME_MAX_LENGTH = 64;
    public static final int FIRST_NAME_MIN_LENGTH = 2;
    public static final int LAST_NAME_MAX_LENGTH = 64;
    public static final int LAST_NAME_MIN_LENGTH = 2;
    public static final int SCREEN_NAME_MIN_LENGTH = 3;
    public static final int SCREEN_NAME_MAX_LENGTH = 32;
    public static final int MIDDLE_NAME_MIN_LENGTH = 2;
    public static final int MIDDLE_NAME_MAX_LENGTH = 64;
    /**
     * IPv4-mapped IPv6 (45 bytes)
     */
    public static final int REGISTRATION_IP_MAX_LENGTH = 45;
    public static final int HASH_KEY_MAX_LENGTH = 60;

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
    @Column(name = "selected_language", nullable = false)
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

    private User() {
    }

    @Override
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String getScreenName() {
        return screenName;
    }

    private void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    private void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    private void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Date getBirthday() {
        return birthday;
    }

    private void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public UserGender getGender() {
        return gender;
    }

    private void setGender(UserGender gender) {
        this.gender = gender;
    }

    public UserLanguage getSelectedLanguage() {
        return selectedLanguage;
    }

    private void setSelectedLanguage(UserLanguage selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    public String getAvatarFileName() {
        return avatarFileName;
    }

    private void setAvatarFileName(String avatarFileName) {
        this.avatarFileName = avatarFileName;
    }

    public String getRegistrationIp() {
        return registrationIp;
    }

    private void setRegistrationIp(String registrationIp) {
        this.registrationIp = registrationIp;
    }

    public UserState getState() {
        return state;
    }

    private void setState(UserState state) {
        this.state = state;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    private void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getHashKey() {
        return hashKey;
    }

    private void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public void changeUsername(String username) {
        setUsername(username);
    }

    public void changeScreenName(String screenName) {
        setScreenName(screenName);
    }

    public void changePersonalData(String firstName, String middleName, String lastName) {
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
    }

    public void changeAdditionalData(Date birthday, String jobTitle, UserLanguage language, UserGender gender) {
        setBirthday(birthday);
        setJobTitle(jobTitle);
        setSelectedLanguage(language);
        setGender(gender);
    }

    public void activate() {
        if (UserState.ACTIVE.equals(getState())) {
            throw new UserActivationException("Cannot activate user because the user is active already.");
        }
        setState(UserState.ACTIVE);
    }

    public void changePassword(String currentPassword, String newPassword) {
        final PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(currentPassword, getPassword())) {
            throw new UsernameNotFoundException("Wrong username and / or password.");
        }

        setPassword(encoder.encode(newPassword));
    }

    public void changeAvatar(String imageLibraryDirectory, MultipartFile avatarFile) throws IOException {
        final File currentAvatarFile = new File(imageLibraryDirectory + "/user-avatar/" + getAvatarFileName());
        if (currentAvatarFile.exists()) {
            if (!currentAvatarFile.delete()) {
                throw new FileExistsException();
            }
        }
        final PasswordEncoder encoder = new BCryptPasswordEncoder();
        final String newAvatarFileName = encoder.encode(avatarFile.getName() + new Date().getTime()).replaceAll("\"", "s").replaceAll("/", "a").replace(".", "sde");
        final File newAvatarFile = new File(imageLibraryDirectory + "/user-avatar/" + newAvatarFileName);
        final FileOutputStream out = new FileOutputStream(newAvatarFile);
        out.write(avatarFile.getBytes());
        out.close();
        setAvatarFileName(newAvatarFileName);
    }

    public void changeLanguage(UserLanguage selectedLanguage) {
        setSelectedLanguage(selectedLanguage);
    }

    public void changeRoles(Set<Role> roles) {
        getRoles().clear();
        getRoles().addAll(roles);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<User> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new User();
        }

        public Builder credentials(String username, String password) {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            this.product.setUsername(username);
            this.product.setPassword(encoder.encode(password));
            this.product.setHashKey(encoder.encode(username + password + new Date()).replace("/", "."));
            this.product.setState(UserState.CREATED);
            return this;
        }

        public Builder screenName(String screenName) {
            this.product.setScreenName(screenName);
            return this;
        }

        public Builder personalData(String firstName, String middleName, String lastName) {
            this.product.setFirstName(firstName);
            this.product.setMiddleName(middleName);
            this.product.setLastName(lastName);
            return this;
        }

        public Builder additionalData(Date birthday, String jobTitle, UserLanguage language, UserGender gender) {
            this.product.setBirthday(birthday);
            this.product.setJobTitle(jobTitle);
            this.product.setSelectedLanguage(language);
            this.product.setGender(gender);
            return this;
        }

        //FIXME unused?
        public Builder roles(Role... roles) {
            Set<Role> rolesSet = new HashSet<>();
            for (Role role : roles) {
                rolesSet.add(role);
            }
            this.product.setRoles(rolesSet);
            return this;
        }

        public Builder registrationIp(String registrationIp) {
            this.product.setRegistrationIp(registrationIp);
            return this;
        }

    }

}