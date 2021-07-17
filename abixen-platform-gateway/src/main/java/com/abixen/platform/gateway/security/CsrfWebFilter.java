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


import org.springframework.http.ResponseCookie;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class CsrfWebFilter implements WebFilter {

    private static final Duration COOKIE_MAX_AGE = Duration.ofMinutes(30);

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
        final String key = CsrfToken.class.getName();
        final Mono<CsrfToken> attribute = exchange.getAttribute(key);
        final Mono<CsrfToken> csrfToken = attribute != null ? attribute : Mono.empty();

        return csrfToken.doOnSuccess(token -> {
            final ResponseCookie cookie = ResponseCookie.from("XSRF-TOKEN", token.getToken())
                    .maxAge(COOKIE_MAX_AGE)
                    .httpOnly(false)
                    .path("/")
                    .build();
            exchange.getResponse().getCookies().add("XSRF-TOKEN", cookie);
        }).then(chain.filter(exchange));
    }

}
