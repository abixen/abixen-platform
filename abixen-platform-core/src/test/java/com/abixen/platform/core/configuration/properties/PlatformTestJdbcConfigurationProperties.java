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

package com.abixen.platform.core.configuration.properties;

import com.abixen.platform.common.configuration.properties.AbstractPlatformJdbcConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@Primary
public class PlatformTestJdbcConfigurationProperties extends AbstractPlatformJdbcConfigurationProperties {

    @PostConstruct
    public void init() {
        setDatabaseUrl("jdbc:h2:mem:AZ;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        setDriverClassName("org.h2.Driver");
        setUsername("sa");
        setPassword("");
        setDialect("org.hibernate.dialect.H2Dialect");
    }

}
