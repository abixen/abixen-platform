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

import com.abixen.platform.common.model.enumtype.UserGender;
import com.abixen.platform.common.model.enumtype.UserLanguage;
import com.abixen.platform.common.model.enumtype.UserState;
import com.abixen.platform.core.application.exception.UserActivationException;
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


@JsonSerialize(as = User.class)
@Entity
@Table(name = "user_")
@SequenceGenerator(sequenceName = "user_seq", name = "user_seq", allocationSize = 1)
public class User extends AuditingModel {

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

    User() {
    }

    @Override
    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    public String getScreenName() {
        return screenName;
    }

    void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Date getBirthday() {
        return birthday;
    }

    void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public UserGender getGender() {
        return gender;
    }

    void setGender(UserGender gender) {
        this.gender = gender;
    }

    public UserLanguage getSelectedLanguage() {
        return selectedLanguage;
    }

    void setSelectedLanguage(UserLanguage selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    public String getAvatarFileName() {
        return avatarFileName;
    }

    void setAvatarFileName(String avatarFileName) {
        this.avatarFileName = avatarFileName;
    }

    public String getRegistrationIp() {
        return registrationIp;
    }

    void setRegistrationIp(String registrationIp) {
        this.registrationIp = registrationIp;
    }

    public UserState getState() {
        return state;
    }

    void setState(UserState state) {
        this.state = state;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getHashKey() {
        return hashKey;
    }

    void setHashKey(String hashKey) {
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
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(currentPassword, getPassword())) {
            throw new UsernameNotFoundException("Wrong username and / or password.");
        }

        setPassword(encoder.encode(newPassword));
    }

    public void changeAvatar(String imageLibraryDirectory, MultipartFile avatarFile) throws IOException {
        File currentAvatarFile = new File(imageLibraryDirectory + "/user-avatar/" + getAvatarFileName());
        if (currentAvatarFile.exists()) {
            if (!currentAvatarFile.delete()) {
                throw new FileExistsException();
            }
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String newAvatarFileName = encoder.encode(avatarFile.getName() + new Date().getTime()).replaceAll("\"", "s").replaceAll("/", "a").replace(".", "sde");
        File newAvatarFile = new File(imageLibraryDirectory + "/user-avatar/" + newAvatarFileName);
        FileOutputStream out = new FileOutputStream(newAvatarFile);
        out.write(avatarFile.getBytes());
        out.close();
        setAvatarFileName(newAvatarFileName);
    }

    public void changeLanguage(UserLanguage selectedLanguage) {
        setSelectedLanguage(selectedLanguage);
    }

}