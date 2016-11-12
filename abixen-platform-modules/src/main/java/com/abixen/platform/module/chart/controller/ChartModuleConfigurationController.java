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

package com.abixen.platform.module.chart.controller;

import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.util.ValidationUtil;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.module.chart.form.ChartConfigurationForm;
import com.abixen.platform.module.chart.model.impl.ChartConfiguration;
import com.abixen.platform.module.chart.service.ChartConfigurationService;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Transactional
@RestController
@RequestMapping(value = "/application/modules/abixen/multi-visualization/configuration")
public class ChartModuleConfigurationController {

    private final Logger log = LoggerFactory.getLogger(ChartModuleConfigurationController.class.getName());

    @Autowired
    private ChartConfigurationService chartConfigurationService;

    @PreAuthorize("hasPermission(#moduleId, 'Module', 'MODULE_VIEW')")
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
