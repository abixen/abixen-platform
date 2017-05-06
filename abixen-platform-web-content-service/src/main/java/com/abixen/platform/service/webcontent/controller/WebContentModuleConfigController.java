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

package com.abixen.platform.service.webcontent.controller;

import com.abixen.platform.service.webcontent.dto.WebContentModuleConfigDto;
import com.abixen.platform.service.webcontent.facade.WebContentModuleConfigFacade;
import com.abixen.platform.service.webcontent.form.WebContentModuleConfigForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/service/abixen/web-content/application/web-content-module-config")
public class WebContentModuleConfigController {

    private final WebContentModuleConfigFacade webContentModuleConfigFacade;

    @Autowired
    public WebContentModuleConfigController(WebContentModuleConfigFacade webContentModuleConfigFacade) {
        this.webContentModuleConfigFacade = webContentModuleConfigFacade;
    }

    @RequestMapping(value = "/{moduleId}", method = RequestMethod.GET)
    public WebContentModuleConfigDto findWebContentModuleConfig(@PathVariable Long moduleId) {
        return webContentModuleConfigFacade.findWebContentModuleConfig(moduleId);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public WebContentModuleConfigDto saveWebContentModuleConfig(@RequestBody WebContentModuleConfigForm webContentModuleConfigForm) {
        return webContentModuleConfigFacade.saveWebContentModuleConfig(webContentModuleConfigForm);
    }
}