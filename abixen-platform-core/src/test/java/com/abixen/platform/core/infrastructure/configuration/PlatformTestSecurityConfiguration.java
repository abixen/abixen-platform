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

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.abixen.platform.common.infrastructure.util.PlatformProfiles.DEV;
import static com.abixen.platform.common.infrastructure.util.PlatformProfiles.DOCKER;

@Profile("test")
@EnableWebSecurity
@Configuration
public class PlatformTestSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/api-intranet/**").permitAll()
                .antMatchers("/api/control-panel/users/custom/username/*/").permitAll()
                .antMatchers("/api/control-panel/module-types/all").permitAll()
                .antMatchers("/api/control-panel/securities/**").permitAll()
                .antMatchers("/api/resources").permitAll()
                .antMatchers("/api/user-activation/activate/*/").permitAll()
                .antMatchers("/hystrix.stream").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("joe.brown@abixen.com").password("password").roles("ADMIN");
    }

}