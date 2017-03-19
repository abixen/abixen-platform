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

package com.abixen.platform.service.businessintelligence.configuration;

import com.abixen.platform.common.configuration.AbstractJpaConfiguration;
import com.abixen.platform.common.configuration.properties.AbstractPlatformJdbcConfigurationProperties;
import com.abixen.platform.service.businessintelligence.security.PlatformAuditorAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@Import(PlatformModuleDataSourceConfiguration.class)
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "platformAuditorAware")
@EnableJpaRepositories(basePackages = {PlatformModulesPackages.CHART_REPOSITORY})
public class PlatformModuleJpaConfiguration extends AbstractJpaConfiguration {

    @Autowired
    public PlatformModuleJpaConfiguration(DataSource dataSource, AbstractPlatformJdbcConfigurationProperties platformJdbcConfiguration) {
        super(dataSource, platformJdbcConfiguration, new String[]{PlatformModulesPackages.CHART_DOMAIN});
    }

    public AuditorAware platformAuditorAware() {
        return new PlatformAuditorAware();
    }
}