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

import com.abixen.platform.core.configuration.PlatformConfiguration;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@PropertySource("classpath:application.yml")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformConfiguration.class)
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
