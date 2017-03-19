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

package com.abixen.platform.gateway.integration;

import com.abixen.platform.gateway.client.UserClient;
import com.abixen.platform.gateway.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserIntegrationClient {

    @Autowired
    private UserClient userClient;

    @HystrixCommand(fallbackMethod = "getUserByUsernameFallback")
    public User getUserByUsername(String username) {
        log.debug("getUserByUsername: " + username);
        return userClient.getUserByUsername(username);
    }

    private User getUserByUsernameFallback(String username) {
        log.error("getUserByUsernameFallback: " + username);
        return null;
    }
}