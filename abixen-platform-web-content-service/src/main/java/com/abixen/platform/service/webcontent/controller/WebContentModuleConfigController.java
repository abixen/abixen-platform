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

import com.abixen.platform.service.webcontent.form.WebContentModuleConfigForm;
import com.abixen.platform.service.webcontent.service.WebContentModuleConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/service/abixen/web-content/application/web-content-module-config")
public class WebContentModuleConfigController {

    private WebContentModuleConfigService webContentModuleConfigService;

    @Autowired
    public WebContentModuleConfigController(WebContentModuleConfigService webContentModuleConfigService) {
        this.webContentModuleConfigService = webContentModuleConfigService;
    }

    @RequestMapping(value = "/{moduleId}", method = RequestMethod.GET)
    public WebContentModuleConfigForm findByModuleId(@PathVariable Long moduleId) {
        return webContentModuleConfigService.findByModuleId(moduleId);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public WebContentModuleConfigForm save(@RequestBody WebContentModuleConfigForm webContentModuleConfigForm) {
        webContentModuleConfigService.save(webContentModuleConfigForm);
        return webContentModuleConfigService.findByModuleId(webContentModuleConfigForm.getModuleId());
    }
}
