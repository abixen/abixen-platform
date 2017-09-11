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

package com.abixen.platform.core.interfaces.web.admin;

import com.abixen.platform.common.application.dto.FormErrorDto;
import com.abixen.platform.common.application.dto.FormValidationResultDto;
import com.abixen.platform.common.infrastructure.util.ValidationUtil;
import com.abixen.platform.core.application.dto.ModuleDto;
import com.abixen.platform.core.application.form.ModuleForm;
import com.abixen.platform.core.application.form.ModuleSearchForm;
import com.abixen.platform.core.application.service.ModuleManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping(value = "/api/control-panel/modules")
public class ModuleController {

    private final ModuleManagementService moduleManagementService;

    @Autowired
    public ModuleController(ModuleManagementService moduleManagementService) {
        this.moduleManagementService = moduleManagementService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModuleDto find(@PathVariable Long id) {
        log.debug("find() - id: {}", id);

        return moduleManagementService.findModule(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public org.springframework.data.domain.Page<ModuleDto> findAll(@PageableDefault(size = 1, page = 0) Pageable pageable, ModuleSearchForm moduleSearchForm) {
        log.debug("findAll() - pageable: {}, moduleSearchForm: {}");

        return moduleManagementService.findAllModules(pageable, moduleSearchForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto<ModuleForm> updateM(@PathVariable("id") Long id, @RequestBody @Valid ModuleForm moduleForm, BindingResult bindingResult) {
        log.debug("update() - id: {}, moduleForm: {}", id, moduleForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(moduleForm, formErrors);
        }

        final ModuleForm updatedModuleForm = moduleManagementService.updateModule(moduleForm);

        return new FormValidationResultDto<>(updatedModuleForm);
    }

}