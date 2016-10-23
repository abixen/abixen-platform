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

package com.abixen.platform.module.kpichart.controller;

import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.util.ValidationUtil;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.module.kpichart.form.KpiChartConfigurationForm;
import com.abixen.platform.module.kpichart.model.impl.KpiChartConfiguration;
import com.abixen.platform.module.kpichart.service.KpiChartConfigurationService;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/application/modules/abixen/kpi-chart/configuration")
public class KpiChartConfigurationController {

    private final Logger log = LoggerFactory.getLogger(KpiChartConfigurationController.class.getName());

    @Autowired
    KpiChartConfigurationService kpiChartConfigurationService;

    @PreAuthorize("hasPermission(#moduleId, 'Module', 'MODULE_VIEW')")
    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{moduleId}", method = RequestMethod.GET)
    public KpiChartConfigurationForm getKpiChartConfiguration(@PathVariable Long moduleId) {
        log.debug("getKpiChartConfiguration() - moduleId: " + moduleId);

        KpiChartConfiguration kpiChartConfiguration = kpiChartConfigurationService.findKpiChartConfigurationByModuleId(moduleId);

        KpiChartConfigurationForm kpiChartConfigurationForm;

        if (kpiChartConfiguration == null) {
            kpiChartConfigurationForm = new KpiChartConfigurationForm();
            kpiChartConfigurationForm.setModuleId(moduleId);
        } else {
            kpiChartConfigurationForm = new KpiChartConfigurationForm(kpiChartConfiguration);
        }

        return kpiChartConfigurationForm;
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createKpiChartConfiguration(@RequestBody @Valid KpiChartConfigurationForm kpiChartConfigurationForm, BindingResult bindingResult) {
        log.debug("createKpiChartConfiguration() - kpiChartConfigurationForm: " + kpiChartConfigurationForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(kpiChartConfigurationForm, formErrors);
        }

        KpiChartConfigurationForm kpiChartConfigurationFormResult = kpiChartConfigurationService.createKpiChartConfiguration(kpiChartConfigurationForm);

        return new FormValidationResultDto(kpiChartConfigurationFormResult);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateKpiChartConfiguration(@PathVariable("id") Long id, @RequestBody @Valid KpiChartConfigurationForm kpiChartConfigurationForm, BindingResult bindingResult) {
        log.debug("updateKpiChartConfiguration() - id: " + id + ", kpiChartConfigurationForm: " + kpiChartConfigurationForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(kpiChartConfigurationForm, formErrors);
        }

        KpiChartConfigurationForm kpiChartConfigurationFormResult = kpiChartConfigurationService.updateKpiChartConfiguration(kpiChartConfigurationForm);

        return new FormValidationResultDto(kpiChartConfigurationFormResult);
    }


}
