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

package com.abixen.platform.service.businessintelligence.multivisualisation.controller;

import com.abixen.platform.common.dto.FormErrorDto;
import com.abixen.platform.common.dto.FormValidationResultDto;
import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.common.util.ValidationUtil;
import com.abixen.platform.common.util.WebModelJsonSerialize;
import com.abixen.platform.service.businessintelligence.multivisualisation.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.ChartConfiguration;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.ChartConfigurationService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@Transactional
@RestController
@RequestMapping(value = "/api/service/abixen/business-intelligence/application/multi-visualisation/configuration")
public class ChartModuleConfigurationController {

    private final ChartConfigurationService chartConfigurationService;

    @Autowired
    public ChartModuleConfigurationController(ChartConfigurationService chartConfigurationService) {
        this.chartConfigurationService = chartConfigurationService;
    }

    @PreAuthorize("hasPermission(#moduleId, '" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_VIEW + "')")
    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{moduleId}", method = RequestMethod.GET)
    public ChartConfigurationForm getChartConfigurationForm(@PathVariable Long moduleId) {
        log.debug("getChartConfigurationForm() - moduleId: " + moduleId);

        ChartConfiguration chartConfiguration = chartConfigurationService.findChartConfigurationByModuleId(moduleId);

        ChartConfigurationForm chartConfigurationForm;

        if (chartConfiguration == null) {
            return null;
        } else {
            chartConfigurationForm = new ChartConfigurationForm(chartConfiguration);
        }

        return chartConfigurationForm;
    }

    @PreAuthorize("hasPermission(#chartConfigurationForm.moduleId, '" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_CONFIGURATION + "')")
    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createChartConfiguration(@RequestBody @Valid ChartConfigurationForm chartConfigurationForm, BindingResult bindingResult) {
        log.debug("createChartConfiguration() - chartConfigurationForm: " + chartConfigurationForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(chartConfigurationForm, formErrors);
        }

        ChartConfigurationForm chartConfigurationFormResult = chartConfigurationService.createChartConfiguration(chartConfigurationForm);

        return new FormValidationResultDto(chartConfigurationFormResult);
    }

    @PreAuthorize("hasPermission(#chartConfigurationForm.moduleId, '" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_CONFIGURATION + "')")
    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateChartConfiguration(@PathVariable("id") Long id, @RequestBody @Valid ChartConfigurationForm chartConfigurationForm, BindingResult bindingResult) {
        log.debug("updateChartConfiguration() - id: " + id + ", chartConfigurationForm: " + chartConfigurationForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(chartConfigurationForm, formErrors);
        }

        ChartConfigurationForm chartConfigurationFormResult = chartConfigurationService.updateChartConfiguration(chartConfigurationForm);

        return new FormValidationResultDto(chartConfigurationFormResult);
    }

}