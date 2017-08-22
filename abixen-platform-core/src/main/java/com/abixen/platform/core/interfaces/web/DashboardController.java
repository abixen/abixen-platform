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

package com.abixen.platform.core.interfaces.web;

import com.abixen.platform.common.dto.FormErrorDto;
import com.abixen.platform.common.dto.FormValidationResultDto;
import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.common.util.ValidationUtil;
import com.abixen.platform.core.application.dto.PageModelDto;
import com.abixen.platform.core.application.form.PageConfigurationForm;
import com.abixen.platform.core.interfaces.web.facade.DashboardFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/api/page-configurations")
public class DashboardController {

    private final DashboardFacade dashboardFacade;

    @Autowired
    public DashboardController(DashboardFacade dashboardFacade) {
        this.dashboardFacade = dashboardFacade;
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_VIEW + "')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PageModelDto find(@PathVariable Long id) {
        return dashboardFacade.find(id);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_ADD + "')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto create(@RequestBody @Valid PageConfigurationForm pageConfigurationForm, BindingResult bindingResult) {
        log.debug("create() - pageConfigurationForm: {}", pageConfigurationForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(pageConfigurationForm, formErrors);
        }

        PageConfigurationForm updatedPageConfigurationForm = dashboardFacade.create(pageConfigurationForm);

        return new FormValidationResultDto(updatedPageConfigurationForm);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_EDIT + "')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto update(@PathVariable("id") Long id, @RequestBody @Valid PageConfigurationForm pageConfigurationForm, BindingResult bindingResult) {
        log.debug("update() - id: {}, pageConfigurationForm: {}", id, pageConfigurationForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(pageConfigurationForm, formErrors);
        }

        PageConfigurationForm updatedPageConfigurationForm = dashboardFacade.update(pageConfigurationForm);

        return new FormValidationResultDto(updatedPageConfigurationForm);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_CONFIGURATION + "')")
    @RequestMapping(value = "/{id}/configure", method = RequestMethod.PUT)
    public FormValidationResultDto configure(@PathVariable("id") Long id, @RequestBody @Valid PageConfigurationForm pageConfigurationForm, BindingResult bindingResult) {
        log.debug("configure() - id: {}, pageConfigurationForm: {}", id, pageConfigurationForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(pageConfigurationForm, formErrors);
        }

        PageConfigurationForm updatedPageConfigurationForm = dashboardFacade.configure(pageConfigurationForm);

        return new FormValidationResultDto(updatedPageConfigurationForm);
    }
}