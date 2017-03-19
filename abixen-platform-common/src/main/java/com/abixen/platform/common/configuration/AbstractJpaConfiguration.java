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

package com.abixen.platform.common.configuration;


import com.abixen.platform.common.configuration.properties.AbstractPlatformJdbcConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

public abstract class AbstractJpaConfiguration {

    private final DataSource dataSource;
    private final AbstractPlatformJdbcConfigurationProperties platformJdbcConfiguration;
    private final String[] packagesToScan;

    @Autowired
    public AbstractJpaConfiguration(DataSource dataSource,
                                    AbstractPlatformJdbcConfigurationProperties platformJdbcConfiguration,
                                    String... packagesToScan) {
        this.dataSource = dataSource;
        this.platformJdbcConfiguration = platformJdbcConfiguration;
        this.packagesToScan = packagesToScan;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {

        return new PersistenceExceptionTranslationPostProcessor();
    }

    @DependsOn("liquibase")
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan(packagesToScan);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(jpaProperties());
        entityManagerFactoryBean.afterPropertiesSet();
        entityManagerFactoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        return entityManagerFactoryBean;
    }

    private Properties jpaProperties() {

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.setProperty("hibernate.dialect", platformJdbcConfiguration.getDialect());
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "validate");
        return jpaProperties;
    }

    private JpaVendorAdapter jpaVendorAdapter() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(false);
        return vendorAdapter;
    }

    @Bean(name = "platformAuditorAware")
    public abstract AuditorAware platformAuditorAware();
}
