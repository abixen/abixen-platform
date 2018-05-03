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

package com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.web;

import com.abixen.platform.common.application.dto.FormErrorDto;
import com.abixen.platform.common.application.dto.FormValidationResultDto;
import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.common.infrastructure.util.ValidationUtil;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.ChartConfigurationDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.ChartConfigurationManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@Transactional
@RestController
@RequestMapping(value = "/api/service/abixen/business-intelligence/application/multi-visualisation/configuration")
public class ChartModuleConfigurationController {

    private final ChartConfigurationManagementService chartConfigurationManagementService;

    @Autowired
    public ChartModuleConfigurationController(ChartConfigurationManagementService chartConfigurationManagementService) {
        this.chartConfigurationManagementService = chartConfigurationManagementService;
    }

    @PreAuthorize("hasPermission(#moduleId, '" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_VIEW + "')")
    @RequestMapping(value = "/{moduleId}", method = RequestMethod.GET)
    public ChartConfigurationForm findChartConfigurationForm(@PathVariable Long moduleId) {
        log.debug("findChartConfigurationForm() - moduleId: {}", moduleId);

        final ChartConfigurationDto chartConfiguration = chartConfigurationManagementService.findChartConfiguration(moduleId);

        if (chartConfiguration == null) {
            return null;
        }

        return new ChartConfigurationForm(chartConfiguration);
    }

    @PreAuthorize("hasPermission(#chartConfigurationForm.moduleId, '" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_CONFIGURATION + "')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto<ChartConfigurationForm> createChartConfiguration(@RequestBody @Valid ChartConfigurationForm chartConfigurationForm, BindingResult bindingResult) {
        log.debug("createChartConfiguration() - chartConfigurationForm: {}", chartConfigurationForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(chartConfigurationForm, formErrors);
        }

        final ChartConfigurationForm createdChartConfigurationForm = chartConfigurationManagementService.createChartConfiguration(chartConfigurationForm);

        return new FormValidationResultDto<>(createdChartConfigurationForm);
    }

    @PreAuthorize("hasPermission(#chartConfigurationForm.moduleId, '" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_CONFIGURATION + "')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto<ChartConfigurationForm> updateChartConfiguration(@PathVariable("id") Long id, @RequestBody @Valid ChartConfigurationForm chartConfigurationForm, BindingResult bindingResult) {
        log.debug("updateChartConfiguration() - id: {}, chartConfigurationForm: {}", id, chartConfigurationForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(chartConfigurationForm, formErrors);
        }

        final ChartConfigurationForm updatedChartConfigurationForm = chartConfigurationManagementService.updateChartConfiguration(chartConfigurationForm);

        return new FormValidationResultDto<>(updatedChartConfigurationForm);
    }

}