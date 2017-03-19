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

package com.abixen.platform.gateway.security;

import com.abixen.platform.common.security.PlatformUser;
import com.abixen.platform.gateway.integration.UserIntegrationClient;
import com.abixen.platform.gateway.model.Role;
import com.abixen.platform.gateway.model.User;
import com.abixen.platform.common.model.enumtype.UserState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class PlatformUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(PlatformUserDetailsService.class.getName());

    @Autowired
    private UserIntegrationClient userIntegrationClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername(" + username + ")");

        User user = userIntegrationClient.getUserByUsername(username);
        log.debug("user: " + user);


        if (user == null) {
            log.error("Wrong username: " + username);
            throw new UsernameNotFoundException("Wrong username and / or password.");
        }

        if (!UserState.ACTIVE.equals(user.getState())) {
            log.error("User is inactive: " + username);
            throw new UsernameNotFoundException("User is inactive.");
        }

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        Collection<? extends GrantedAuthority> authorities = getAuthorities(user);
        boolean admin = isAdmin(authorities);

        PlatformUser platformUser = new PlatformUser(user.getUsername(),
                user.getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities,
                user.getFirstName(),
                user.getLastName(),
                admin,
                user.getId(),
                user.getSelectedLanguage(),
                user.getAvatarFileName());

        return platformUser;
    }

    private boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority grantedAuthority : authorities) {
            if ("ROLE_ADMIN".equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleType().getName()));
        }
        return grantedAuthorities;
    }

}