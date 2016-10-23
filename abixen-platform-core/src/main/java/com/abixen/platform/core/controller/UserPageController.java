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

import com.abixen.platform.core.model.impl.ModuleType;
import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.core.service.ModuleTypeService;
import com.abixen.platform.core.service.PageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user/pages")
public class UserPageController {

    private final Logger log = Logger.getLogger(UserPageController.class.getName());

    private final PageService pageService;

    private final ModuleTypeService moduleTypeService;

    @Autowired
    public UserPageController(PageService pageService, ModuleTypeService moduleTypeService) {
        this.pageService = pageService;
        this.moduleTypeService = moduleTypeService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Page> getPages() {
        log.debug("getPages()");
        List<Page> pages = pageService.findAllPages();
        return pages;
    }

    @RequestMapping(value = "/module-types", method = RequestMethod.GET)
    public List<ModuleType> getModuleTypes() {
        log.debug("getModuleTypes()");
        List<ModuleType> moduleTypes = moduleTypeService.findAllModuleTypes();
        return moduleTypes;
    }
}
