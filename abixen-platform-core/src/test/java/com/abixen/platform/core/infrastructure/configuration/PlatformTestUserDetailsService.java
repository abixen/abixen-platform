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

package com.abixen.platform.core.infrastructure.configuration;

import com.abixen.platform.common.domain.model.enumtype.UserState;
import com.abixen.platform.common.infrastructure.security.PlatformUser;
import com.abixen.platform.core.application.dto.UserDto;
import com.abixen.platform.core.application.service.UserManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;


public class PlatformTestUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(PlatformTestUserDetailsService.class.getName());

    @Autowired
    private UserManagementService userManagementService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername({})", username);

        final UserDto user = userManagementService.findUser(username);
        log.debug("user: {}", user);

        if (user == null) {
            log.error("Wrong username: {}", username);
            throw new UsernameNotFoundException("Wrong username and / or password.");
        }

        if (!UserState.ACTIVE.equals(user.getState())) {
            log.error("User is inactive: {}", username);
            throw new UsernameNotFoundException("User is inactive.");
        }

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        final Collection<? extends GrantedAuthority> authorities = getAuthorities(user);
        final boolean admin = isAdmin(authorities);

        final PlatformUser platformUser = new PlatformUser(user.getUsername(),
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

    private Collection<? extends GrantedAuthority> getAuthorities(UserDto user) {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleType().getName()))
                .collect(Collectors.toList());
    }

}