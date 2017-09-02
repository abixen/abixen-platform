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

package com.abixen.platform.common.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PlatformGlobalMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    private final PlatformPermissionEvaluator platformPermissionEvaluator;

    @Autowired
    public PlatformGlobalMethodSecurityConfiguration(PlatformPermissionEvaluator platformPermissionEvaluator) {
        this.platformPermissionEvaluator = platformPermissionEvaluator;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        PlatformSecurityExpressionHandler expressionHandler = new PlatformSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(platformPermissionEvaluator);
        return expressionHandler;
    }
}