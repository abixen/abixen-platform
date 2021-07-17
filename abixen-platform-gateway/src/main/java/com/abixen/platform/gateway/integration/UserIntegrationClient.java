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

package com.abixen.platform.gateway.integration;

import com.abixen.platform.gateway.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserIntegrationClient {

    private final WebClient.Builder webClientBuilder;

    public Mono<User> getUserByUsername(final String username) {
        log.debug("getUserByUsername: {}", username);

        return webClientBuilder
                .build()
                .get()
                .uri("http://abixen-platform-core/api/control-panel/users/custom/username/{username}/", username)
                .retrieve()
                .bodyToMono(User.class);
    }
}