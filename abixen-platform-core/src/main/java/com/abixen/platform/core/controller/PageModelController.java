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

import com.abixen.platform.core.dto.PageModelDto;
import com.abixen.platform.core.service.PageModelService;
import com.abixen.platform.core.service.SecurityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/pages")
public class PageModelController {

    private final Logger log = Logger.getLogger(PageModelController.class.getName());

    @Autowired
    private SecurityService securityService;

    @Autowired
    private Environment environment;

    @Autowired
    private PageModelService pageModelService;

    //@PreAuthorize("hasPermission(#pageName, 'Page', 'DELETE')")
    //@PreAuthorize("canViewPage(#id)")
    @RequestMapping(value = "/{id}/model", method = RequestMethod.GET)
    public PageModelDto getPageModel(@PathVariable Long id) {
        log.debug("getPageModel() - id:" + id);

        return pageModelService.getPageModel(id);
    }

    //public PageModelDto updatePageModel(@PathVariable("id") Long id, @RequestBody @Valid PageModelDto pageModelDto, BindingResult bindingResult)


/*    @RequestMapping(value = "", method = RequestMethod.POST)
    public PageModelDto createPageModel(@RequestBody @Valid PageModelDto pageModelDto) {
        log.debug("createPageModel() - pageModelDto: " + pageModelDto);

        return null;
    }*/

    @RequestMapping(value = "/{id}/model", method = RequestMethod.PUT)
    public PageModelDto updatePageModel(@PathVariable("id") Long id, @RequestBody @Valid PageModelDto pageModelDto, BindingResult bindingResult) {
        log.debug("updatePageModel() - id: " + id + ", pageModelDto: " + pageModelDto);

        /*if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(rolePermissionsForm, formErrors);
        }*/

        /*
        Role role = roleService.buildRolePermissions(rolePermissionsForm);
        roleService.updateRole(role);*/

        return pageModelService.updatePageModel(pageModelDto);
    }
}
