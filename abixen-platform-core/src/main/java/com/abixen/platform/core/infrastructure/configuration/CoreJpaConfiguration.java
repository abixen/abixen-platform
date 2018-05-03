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

package com.abixen.platform.core.infrastructure.configuration;

import com.abixen.platform.common.infrastructure.configuration.AbstractJpaConfiguration;
import com.abixen.platform.common.infrastructure.configuration.properties.AbstractPlatformJdbcConfigurationProperties;
import com.abixen.platform.core.CoreApplication;
import com.abixen.platform.core.infrastructure.security.PlatformAuditorAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@Import(CoreDataSourceConfiguration.class)
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "platformAuditorAware")
@EnableJpaRepositories(basePackageClasses = CoreApplication.class,
        repositoryFactoryBeanClass = CoreJpaRepositoryFactoryBean.class)
public class CoreJpaConfiguration extends AbstractJpaConfiguration {

    @Autowired
    public CoreJpaConfiguration(DataSource dataSource, AbstractPlatformJdbcConfigurationProperties platformJdbcConfiguration) {
        super(dataSource, platformJdbcConfiguration, CoreApplication.class.getPackage().getName());
    }

    public AuditorAware platformAuditorAware() {
        return new PlatformAuditorAware();
    }

}