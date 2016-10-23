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

package com.abixen.platform.core.util;

import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.service.DomainBuilderService;
import com.abixen.platform.core.service.impl.DomainBuilderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;


@RunWith(MockitoJUnitRunner.class)
public class UserBuilderTest {

    @InjectMocks
    public DomainBuilderService domainBuilderService = new DomainBuilderServiceImpl();

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindUserByUsername() {
        UserBuilder userBuilder = domainBuilderService.newUserBuilderInstance();
        userBuilder.credentials("username", "password");
        User user = userBuilder.build();
        assertNotNull(user);
    }

}
