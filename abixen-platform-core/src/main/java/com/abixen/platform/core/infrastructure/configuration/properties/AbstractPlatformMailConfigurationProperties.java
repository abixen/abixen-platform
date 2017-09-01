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


import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class AbstractPlatformMailConfigurationProperties {

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
    private User user;

    @NotNull
    private Transport transport;

    @NotNull
    private Smtp smtp;

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
        private Starttls starttls;

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
