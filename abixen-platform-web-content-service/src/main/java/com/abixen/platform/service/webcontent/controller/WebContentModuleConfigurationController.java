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

import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.service.webcontent.dto.WebContentModuleConfigurationDto;
import com.abixen.platform.service.webcontent.facade.WebContentModuleConfigurationFacade;
import com.abixen.platform.service.webcontent.form.WebContentModuleConfigForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/service/abixen/web-content/application/web-content-module-config")
public class WebContentModuleConfigurationController {

    private final WebContentModuleConfigurationFacade webContentModuleConfigurationFacade;

    @Autowired
    public WebContentModuleConfigurationController(WebContentModuleConfigurationFacade webContentModuleConfigurationFacade) {
        this.webContentModuleConfigurationFacade = webContentModuleConfigurationFacade;
    }

    @PreAuthorize("hasPermission(#moduleId, '" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_VIEW + "')")
    @RequestMapping(value = "/{moduleId}", method = RequestMethod.GET)
    public WebContentModuleConfigurationDto findWebContentModuleConfig(@PathVariable Long moduleId) {
        return webContentModuleConfigurationFacade.findWebContentModuleConfiguration(moduleId);
    }

    @PreAuthorize("hasPermission(#webContentModuleConfigForm.moduleId, '" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_CONFIGURATION + "')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public WebContentModuleConfigurationDto createWebContentModuleConfig(@RequestBody WebContentModuleConfigForm webContentModuleConfigForm) {
        return webContentModuleConfigurationFacade.createWebContentModuleConfiguration(webContentModuleConfigForm);
    }

    @PreAuthorize("hasPermission(#webContentModuleConfigForm.moduleId, '" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_CONFIGURATION + "')")
    @RequestMapping(value = "/{moduleId}", method = RequestMethod.PUT)
    public WebContentModuleConfigurationDto updateWebContentModuleConfig(@RequestBody WebContentModuleConfigForm webContentModuleConfigForm) {
        return webContentModuleConfigurationFacade.updateWebContentModuleConfiguration(webContentModuleConfigForm);
    }
}