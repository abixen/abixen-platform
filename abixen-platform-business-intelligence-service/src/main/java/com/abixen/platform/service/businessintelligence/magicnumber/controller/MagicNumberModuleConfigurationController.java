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

package com.abixen.platform.service.businessintelligence.magicnumber.controller;

import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.util.ValidationUtil;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.service.businessintelligence.magicnumber.form.MagicNumberModuleConfigurationForm;
import com.abixen.platform.service.businessintelligence.magicnumber.model.impl.MagicNumberModule;
import com.abixen.platform.service.businessintelligence.magicnumber.service.MagicNumberModuleService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/application/businessintelligence/abixen/magic-number/configuration")
public class MagicNumberModuleConfigurationController {

    @Autowired
    private MagicNumberModuleService magicNumberModuleService;

    @PreAuthorize("hasPermission(#moduleId, 'Module', 'MODULE_VIEW')")
    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{moduleId}", method = RequestMethod.GET)
    public MagicNumberModuleConfigurationForm getMagicNumberModuleConfiguration(@PathVariable Long moduleId) {
        log.debug("getMagicNumberModule() - moduleId: " + moduleId);

        MagicNumberModule magicNumberModule = magicNumberModuleService.findMagicNumberModuleByModuleId(moduleId);

        MagicNumberModuleConfigurationForm magicNumberModuleConfigurationForm;

        if (magicNumberModule == null) {
            magicNumberModuleConfigurationForm = new MagicNumberModuleConfigurationForm();
            magicNumberModuleConfigurationForm.setModuleId(moduleId);
        } else {
            magicNumberModuleConfigurationForm = new MagicNumberModuleConfigurationForm(magicNumberModule);
        }

        return magicNumberModuleConfigurationForm;
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createMagicNumberModuleConfiguration(@RequestBody @Valid MagicNumberModuleConfigurationForm magicNumberModuleConfigurationForm, BindingResult bindingResult) {
        log.debug("createMagicNumberModuleConfiguration() - magicNumberModuleConfigurationForm: " + magicNumberModuleConfigurationForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(magicNumberModuleConfigurationForm, formErrors);
        }

        MagicNumberModuleConfigurationForm magicNumberModuleConfigurationFormResult = magicNumberModuleService.createMagicNumberModule(magicNumberModuleConfigurationForm);

        return new FormValidationResultDto(magicNumberModuleConfigurationFormResult);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateMagicNumberModuleConfiguration(@PathVariable("id") Long id, @RequestBody @Valid MagicNumberModuleConfigurationForm magicNumberModuleConfigurationForm, BindingResult bindingResult) {
        log.debug("updateMagicNumberModuleConfiguration() - id: " + id + ", magicNumberModuleConfigurationForm: " + magicNumberModuleConfigurationForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(magicNumberModuleConfigurationForm, formErrors);
        }

        MagicNumberModuleConfigurationForm magicNumberModuleConfigurationFormResult = magicNumberModuleService.updateMagicNumberModule(magicNumberModuleConfigurationForm);

        return new FormValidationResultDto(magicNumberModuleConfigurationFormResult);
    }
}
