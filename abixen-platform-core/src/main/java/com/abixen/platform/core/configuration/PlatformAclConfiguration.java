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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.EhCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;


@Configuration
public class PlatformAclConfiguration {

    @Autowired
    DataSource dataSource;

    @Bean
    EhCacheBasedAclCache aclCache() {
        EhCacheFactoryBean factoryBean = new EhCacheFactoryBean();
        EhCacheManagerFactoryBean cacheManager = new   EhCacheManagerFactoryBean();
        cacheManager.setAcceptExisting(true);
        cacheManager.setCacheManagerName(CacheManager.class.getName());
        cacheManager.afterPropertiesSet();

        factoryBean.setCacheName("aclCache");
        factoryBean.setCacheManager(cacheManager.getObject());
        //factoryBean.setMaxBytesLocalHeap("16M");
        //factoryBean.setMaxEntriesLocalHeap(0L);
        factoryBean.afterPropertiesSet();
        return new EhCacheBasedAclCache(factoryBean.getObject());
    }

    @Bean
    AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"),
                new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"),
                new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"));
    }

    @Bean
    LookupStrategy lookupStrategy() {
        return new BasicLookupStrategy(dataSource, aclCache(),  aclAuthorizationStrategy(), new ConsoleAuditLogger());
    }

    @Bean(name = "mutableAclService")
    JdbcMutableAclService aclService() {
        JdbcMutableAclService service = new     JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
        service.setClassIdentityQuery("select currval(pg_get_serial_sequence('acl_class', 'id'))");
        service.setSidIdentityQuery("select currval(pg_get_serial_sequence('acl_sid', 'id'))");
        return service;
    }
}
