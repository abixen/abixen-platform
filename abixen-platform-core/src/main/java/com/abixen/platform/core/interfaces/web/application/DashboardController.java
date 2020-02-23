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

package com.abixen.platform.core.interfaces.web.application;

import com.abixen.platform.common.application.representation.FormErrorRepresentation;
import com.abixen.platform.common.application.representation.FormValidationResultRepresentation;
import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.common.infrastructure.util.ValidationUtil;
import com.abixen.platform.core.application.dto.DashboardDto;
import com.abixen.platform.core.application.form.DashboardForm;
import com.abixen.platform.core.application.service.dashboard.DashboardService;
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

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_VIEW + "')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DashboardDto find(@PathVariable Long id) {
        log.debug("find() - id: {}", id);

        return dashboardService.find(id);
    }

    @PreAuthorize("hasPermission('" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_ADD + "')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultRepresentation<DashboardForm> create(@RequestBody @Valid DashboardForm dashboardForm, BindingResult bindingResult) {
        log.debug("create() - dashboardForm: {}", dashboardForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorRepresentation> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultRepresentation<>(dashboardForm, formErrors);
        }

        final DashboardForm cratedDashboardForm = dashboardService.create(dashboardForm);

        return new FormValidationResultRepresentation<>(cratedDashboardForm);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_EDIT + "')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultRepresentation<DashboardForm> update(@PathVariable("id") Long id, @RequestBody @Valid DashboardForm dashboardForm, BindingResult bindingResult) {
        log.debug("update() - id: {}, dashboardForm: {}", id, dashboardForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorRepresentation> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultRepresentation<>(dashboardForm, formErrors);
        }

        final DashboardForm updatedDashboardForm = dashboardService.update(dashboardForm);

        return new FormValidationResultRepresentation<>(updatedDashboardForm);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_CONFIGURATION + "')")
    @RequestMapping(value = "/{id}/configure", method = RequestMethod.PUT)
    public FormValidationResultRepresentation<DashboardForm> configure(@PathVariable("id") Long id, @RequestBody @Valid DashboardForm dashboardForm, BindingResult bindingResult) {
        log.debug("configure() - id: {}, dashboardForm: {}", id, dashboardForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorRepresentation> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultRepresentation<>(dashboardForm, formErrors);
        }

        final DashboardForm updatedDashboardForm = dashboardService.configure(dashboardForm);

        return new FormValidationResultRepresentation<>(updatedDashboardForm);
    }

}