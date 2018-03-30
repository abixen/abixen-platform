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

package com.abixen.platform.core.infrastructure.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Component
@EnableConfigurationProperties(PlatformMailConfigurationProperties.class)
@ConfigurationProperties(prefix = "platform.core.mail.outgoing")
public class PlatformMailConfigurationProperties {

    private static final int MIN_PORT_NUMBER = 0;

    private static final int MAX_PORT_NUMBER = 65535;

    @NotNull
    private String host;

    @NotNull
    @Min(MIN_PORT_NUMBER)
    @Max(MAX_PORT_NUMBER)
    private Integer port;

    @NotNull
    private Boolean debug;

    @NotNull
    private PlatformMailConfigurationProperties.User user;

    @NotNull
    private PlatformMailConfigurationProperties.Transport transport;

    @NotNull
    private PlatformMailConfigurationProperties.Smtp smtp;

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class User {

        @NotNull
        private String username;

        @NotNull
        private String name;

        @NotNull
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Transport {

        @NotNull
        private String protocol;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Smtp {

        @NotNull
        private Boolean auth;

        @NotNull
        private PlatformMailConfigurationProperties.Smtp.Starttls starttls;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Starttls {

            @NotNull
            private Boolean enable;
        }
    }

}