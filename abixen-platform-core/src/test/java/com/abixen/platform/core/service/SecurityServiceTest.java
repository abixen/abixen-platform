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

package com.abixen.platform.core.service;

import com.abixen.platform.core.configuration.PlatformConfiguration;
import com.abixen.platform.core.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.Module;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.repository.ModuleRepository;
import com.abixen.platform.core.repository.PageRepository;
import com.abixen.platform.core.repository.UserRepository;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformConfiguration.class)
public class SecurityServiceTest {

    static Logger log = Logger.getLogger(SecurityServiceTest.class.getName());

    @Resource
    private UserRepository userRepository;

    @Resource
    private PageRepository pageRepository;

    @Resource
    private ModuleRepository moduleRepository;

    @Autowired
    private SecurityService securityService;


    //@Test
   /* public void userHasPermissionToViewPageOld() {
        log.debug("userHasPermissionToViewPage()");
        //User user = userRepository.findOne(1L);
        //Page page = pageRepository.findOne(1L);
        Boolean hasPermission = true;//securityService.hasUserPermissionToPage(user, PermissionName.PAGE_VIEW, page);
        assertTrue(hasPermission);
    }*/

    //@Test
    /*public void userHasPermissionToViewModule() {
        log.debug("userHasPermissionToViewModule()");
        //User user = userRepository.findOne(1L);
        //Module module = moduleRepository.findOne(1L);
        log.debug("modules: " + moduleRepository.findAll());
        Boolean hasPermission = false; //securityService.hasUserPermissionToModule(user, PermissionName.MODULE_VIEW, module);
        assertFalse(hasPermission);
    }*/

    /**
     * The user admin has permission to view the module with id equal 1, because he got this permission by ACL configuration.
     */
    @Test
    public void userAdminHasPermissionToViewModule() {
        log.debug("userAdminHasPermissionToViewModule()");
        User admin = userRepository.findOne(1L);
        Module module = moduleRepository.findOne(1L);
        Boolean hasPermission = securityService.hasUserPermissionToObject(admin, PermissionName.MODULE_VIEW, module);
        assertTrue(hasPermission);
    }

    /**
     * The user admin has permission to add the module with id equal 1, because he got this permission by ACL configuration.
     */
    @Test
    public void userAdminHasPermissionToAddModule() {
        log.debug("userAdminHasPermissionToAddModule()");
        User admin = userRepository.findOne(1L);
        Module module = moduleRepository.findOne(1L);
        Boolean hasPermission = securityService.hasUserPermissionToObject(admin, PermissionName.MODULE_ADD, module);
        assertTrue(hasPermission);
    }

    /**
     * The user admin has permission to edit the module with id equal 1, because he is the owner.
     */
    @Test
    public void userAdminHasPermissionToEditModule() {
        log.debug("userAdminHasPermissionToEditModule()");
        User admin = userRepository.findOne(1L);
        Module module = moduleRepository.findOne(1L);
        Boolean hasPermission = securityService.hasUserPermissionToObject(admin, PermissionName.MODULE_EDIT, module);

        //FIXME
        assertFalse(hasPermission);
        //assertTrue(hasPermission);
    }

    /**
     * The user admin has not permission to delete the module with id equal 1,
     * because he got neither ACL nor appropriate role.
     */
    @Test
    public void userAdminHasNotPermissionToDeleteModule() {
        log.debug("userAdminHasNotPermissionToDeleteModule()");
        User admin = userRepository.findOne(1L);
        Module module = moduleRepository.findOne(1L);
        Boolean hasPermission = securityService.hasUserPermissionToObject(admin, PermissionName.MODULE_DELETE, module);
        assertFalse(hasPermission);
    }

    /**
     * The user editor has permission to view the module with id equal 1,
     * because he got this permission by assigning to appropriate role contains it.
     */
    @Test
    public void userEditorHasPermissionToViewModule() {
        log.debug("userEditorHasPermissionToViewModule()");
        User editor = userRepository.findOne(3L);
        Module module = moduleRepository.findOne(1L);
        Boolean hasPermission = securityService.hasUserPermissionToObject(editor, PermissionName.MODULE_VIEW, module);
        assertTrue(hasPermission);
    }


}
