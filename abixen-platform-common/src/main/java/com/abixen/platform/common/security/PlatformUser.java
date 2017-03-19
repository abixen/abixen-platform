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

package com.abixen.platform.common.security;

import com.abixen.platform.common.model.enumtype.UserLanguage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class PlatformUser extends User implements PlatformWebUser {

    private static final long serialVersionUID = 8506897113062768629L;

    private Long id;
    private String firstname;
    private String lastname;
    private boolean admin;
    private UserLanguage selectedLanguage;
    private String avatarFileName;

    public PlatformUser(String username,
                        String password,
                        boolean enabled,
                        boolean accountNonExpired,
                        boolean credentialsNonExpired,
                        boolean accountNonLocked,
                        Collection<? extends GrantedAuthority> authorities,
                        String firstname,
                        String lastname,
                        boolean admin,
                        Long id,
                        UserLanguage selectedLanguage,
                        String avatarFileName) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.admin = admin;
        this.firstname = firstname;
        this.lastname = lastname;
        this.id = id;
        this.selectedLanguage = selectedLanguage;
        this.avatarFileName = avatarFileName;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public boolean isAdmin() {
        return admin;
    }

    @Override
    public UserLanguage getSelectedLanguage() {
        return selectedLanguage;
    }

    @Override
    public String getAvatarFileName() {
        return avatarFileName;
    }
}