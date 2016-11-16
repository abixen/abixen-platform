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

package com.abixen.platform.core.configuration.properties;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
@Primary
public class PlatformTestMailConfigurationProperties extends AbstractPlatformMailConfigurationProperties{
    @PostConstruct
    public void init() {
        setDebug(false);
        setHost("test");
        setPort(587);
        setTransport(new Transport("smtp"));
        setSmtp(new Smtp()
                .setAuth(true)
                .setStarttls(new Smtp.Starttls(true)));
        setUser(new User()
                .setName("test")
                .setUsername("test")
                .setPassword("test"));
    }
}
