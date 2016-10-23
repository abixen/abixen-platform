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

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;


@Configuration
@Import(PlatformModulePropertiesConfiguration.class)
public class PlatformModuleDataSourceConfiguration {

    @Value("#{jdbcProperties['jdbc.driverClassName']}")
    private String driverClassName;

    //@Value("#{jdbcProperties['jdbc.dialect']}")
    //private String dialect;

    @Value("#{jdbcProperties['jdbc.databaseUrl']}")
    private String databaseUrl;

    @Value("#{jdbcProperties['jdbc.username']}")
    private String username;

    @Value("#{jdbcProperties['jdbc.password']}")
    private String password;

    //http://stackoverflow.com/questions/20039333/how-to-spring-3-2-hibernate-4-on-javaconfig-correctly


    @Profile("dev")
    @Bean(destroyMethod = "close")
    public DataSource devDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Profile("cloud")
    @Bean(destroyMethod = "close")
    public DataSource cloudDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Profile("test")
    @Bean(destroyMethod = "close")
    public DataSource testDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        // DatabasePopulatorUtils.execute(databasePopulator(), dataSource);
        return dataSource;
    }

}
