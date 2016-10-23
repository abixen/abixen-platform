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

package com.abixen.platform.module.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;


@Configuration
public class PlatformModulePropertiesConfiguration {

    private final Logger log = LoggerFactory.getLogger(PlatformModulePropertiesConfiguration.class.getName());

    @Autowired
    Environment environment;

    @Bean(name = "jdbcProperties")
    public PropertiesFactoryBean jdbcProperties() {
        log.debug("jdbcProperties()");
        PropertiesFactoryBean settings = new PropertiesFactoryBean();
        settings.setLocation(new ClassPathResource("config/jdbc_" + environment.getActiveProfiles()[0] + ".properties"));
        return settings;
    }

}
