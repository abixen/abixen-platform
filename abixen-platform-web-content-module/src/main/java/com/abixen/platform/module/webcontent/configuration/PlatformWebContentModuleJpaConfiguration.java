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

package com.abixen.platform.module.webcontent.configuration;

import com.abixen.platform.core.configuration.AbstractJapConfiguration;
import com.abixen.platform.core.configuration.properties.AbstractPlatformJdbcConfigurationProperties;
import com.abixen.platform.module.webcontent.security.PlatformAuditorAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static com.abixen.platform.module.webcontent.configuration.PlatformWebContentModulePackages.*;


@Configuration
@Import(PlatformWebContentModuleDataSourceConfiguration.class)
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "platformAuditorAware")
@EnableJpaRepositories(basePackages = {REPOSITORY})
public class PlatformWebContentModuleJpaConfiguration extends AbstractJapConfiguration {

    @Autowired
    public PlatformWebContentModuleJpaConfiguration(DataSource dataSource, AbstractPlatformJdbcConfigurationProperties platformJdbcConfiguration) {
        super(dataSource, platformJdbcConfiguration, REPOSITORY);
    }

    public AuditorAware platformAuditorAware() {
        return new PlatformAuditorAware();
    }
}