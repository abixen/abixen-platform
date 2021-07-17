/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.gateway.security;

import com.abixen.platform.common.domain.model.enumtype.UserState;
import com.abixen.platform.common.infrastructure.security.PlatformUser;
import com.abixen.platform.gateway.integration.UserIntegrationClient;
import com.abixen.platform.gateway.model.Role;
import com.abixen.platform.gateway.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Component
public class PlatformUserDetailsService implements ReactiveUserDetailsService {

    private final Logger log = LoggerFactory.getLogger(PlatformUserDetailsService.class.getName());

    @Autowired
    private UserIntegrationClient userIntegrationClient;

    @Override
    public Mono<UserDetails> findByUsername(final String username) {
        log.debug("findByUsername - username: {}", username);

        final Mono<User> user = userIntegrationClient.getUserByUsername(username);
        log.debug("found user: {}", user);


        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return user
                .filter(u -> UserState.ACTIVE.equals(u.getState()))
                .map(u -> new PlatformUser(u.getUsername(),
                        u.getPassword(),
                        enabled,
                        accountNonExpired,
                        credentialsNonExpired,
                        accountNonLocked,
                        getAuthorities(u),
                        u.getFirstName(),
                        u.getLastName(),
                        isAdmin(getAuthorities(u)),
                        u.getId(),
                        u.getSelectedLanguage(),
                        u.getAvatarFileName())
                );
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
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleType().getName()));
        }
        return grantedAuthorities;
    }
}