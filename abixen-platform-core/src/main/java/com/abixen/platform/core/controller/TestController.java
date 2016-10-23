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

package com.abixen.platform.core.controller;

import com.abixen.platform.core.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.core.model.impl.Permission;
import com.abixen.platform.core.repository.PageRepository;
import com.abixen.platform.core.service.PageService;
import com.abixen.platform.core.service.PermissionService;
import com.abixen.platform.core.service.SecurityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.Sid;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;



@RestController
@RequestMapping(value = "/api/admin/tests")
public class TestController {

    static Logger log = Logger.getLogger(TestController.class.getName());

    @Autowired
    SecurityService securityService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    Environment environment;

    @Autowired
    PageService pageService;

    @Resource
    PageRepository pageRepository;


    @PostAuthorize("hasPermission(returnObject, 'PAGE_EDIT')")
    @RequestMapping(value = "/test1/{id}", method = RequestMethod.GET)
    public Page getPageTest1(@PathVariable Long id) {
        log.debug("getPage() - id: " + id);

        Page page = pageService.findPage(id);

        return page;
    }


    @PostFilter("hasPermission(filterObject, 'PAGE_EDIT')")
    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public List<Page> getPageTest2() {
        log.debug("getPage() - id: ");

        List<Page> pages = pageRepository.findAll();

        return pages;
    }

    @RequestMapping(value = "/x", method = RequestMethod.GET)
    public void createPage() {
        log.debug("createPage()");

        Page page = pageService.findPage(2l);

       // Sid sid = new PrincipalSid("admin");
        //Sid sid = new PrincipalSid("owner");
        //Sid sidUser = new GrantedAuthoritySid("ROLE_ADMIN");

        //aclSecurityUtil.addPermission(customer, sid, BasePermission.ADMINISTRATION, Customer.class);
        //permissionService.addPermission(page, sid, BasePermission.WRITE, Page.class);
        //permissionService.addPermission(page, sidUser, BasePermission.READ, Page.class);

       // return pageService.createPage(page);
    }


}
