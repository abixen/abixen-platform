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

package com.abixen.platform.core.repository;

import com.abixen.platform.core.PlatformApplication;
import com.abixen.platform.core.configuration.*;
import com.abixen.platform.core.configuration.properties.PlatformDataSourceConfigurationProperties;
import com.abixen.platform.core.configuration.properties.PlatformMailConfigurationProperties;
import com.abixen.platform.core.model.impl.Permission;
import com.abixen.platform.core.util.PlatformProfiles;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;


@ActiveProfiles(PlatformProfiles.TEST)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {PlatformAclConfiguration.class, PlatformJpaConfiguration.class, PlatformDataSourceConfigurationProperties.class, PlatformMailConfigurationProperties.class, PlatformDataSourceConfiguration.class, PlatformServiceConfiguration.class, PlatformSecurityConfiguration.class})
//@ContextConfiguration(classes = {PlatformJpaConfiguration.class, PlatformDataSourceConfigurationProperties.class, PlatformMailConfigurationProperties.class, PlatformDataSourceConfiguration.class, PlatformServiceConfiguration.class, PlatformSecurityConfiguration.class})
public class RepositoryTest {

    static Logger log = Logger.getLogger(RepositoryTest.class.getName());

    @Resource
    private PermissionRepository permissionRepository;


    @Test
    public void findAllByJsonCriteria() throws NoSuchFieldException {
        log.debug("findAllByJsonCriteria()");

       /* String jsonCriteria = "{\"and\":[{\"name\":\"title\",\"operation\":\"=\",\"value\":\"Page View\"},{\"name\":\"description\",\"operation\":\"~\",\"value\":\"*Allows to view a page.*\"}]}";


        Page<Permission> permissions = permissionRepository.findAllByJsonCriteria(jsonCriteria, null);
        log.debug("permissions: " + permissions);

        assertEquals(1, permissions.getTotalElements());*/
    }


}
