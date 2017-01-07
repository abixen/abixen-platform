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

package com.abixen.platform.core.controller.admin;

import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.form.ModuleForm;
import com.abixen.platform.core.model.impl.Module;
import com.abixen.platform.core.model.web.ModuleWeb;
import com.abixen.platform.core.service.ModuleService;
import com.abixen.platform.core.service.SecurityService;
import com.abixen.platform.core.util.ValidationUtil;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/admin/modules")
public class ModuleController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ModuleService moduleService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public org.springframework.data.domain.Page<Module> getModules(@PageableDefault(size = 1, page = 0) Pageable pageable) {
        log.debug("getModules()");

        return moduleService.findAllModules(pageable);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModuleWeb getModule(@PathVariable Long id) {
        log.debug("getModule() - id: " + id);

        return moduleService.findModule(id);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateModule(@PathVariable("id") Long id, @RequestBody @Valid ModuleForm moduleForm, BindingResult bindingResult) {
        log.debug("updateModule() - id: " + id + ", moduleForm: " + moduleForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(moduleForm, formErrors);
        }

        moduleService.updateModule(moduleForm);

        return new FormValidationResultDto(moduleForm);
    }

}
