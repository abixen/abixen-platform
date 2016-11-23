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

package com.abixen.platform.core.configuration;

import com.abixen.platform.core.configuration.properties.AbstractPlatformJdbcConfigurationProperties;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public abstract class AbstractPlatformDataSourceConfiguration {
    @Autowired
    AbstractPlatformJdbcConfigurationProperties platformJdbcConfiguration;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(platformJdbcConfiguration.getDriverClassName());
        dataSource.setUrl(platformJdbcConfiguration.getDatabaseUrl());
        dataSource.setUsername(platformJdbcConfiguration.getUsername());
        dataSource.setPassword(platformJdbcConfiguration.getPassword());
        return dataSource;
    }
}
