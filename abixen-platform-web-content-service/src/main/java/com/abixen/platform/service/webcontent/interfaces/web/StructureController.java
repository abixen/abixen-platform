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

package com.abixen.platform.service.webcontent.interfaces.web;

import com.abixen.platform.common.application.dto.FormErrorDto;
import com.abixen.platform.common.application.dto.FormValidationResultDto;
import com.abixen.platform.common.infrastructure.util.ValidationUtil;
import com.abixen.platform.service.webcontent.application.dto.StructureDto;
import com.abixen.platform.service.webcontent.application.form.StructureForm;
import com.abixen.platform.service.webcontent.application.service.StructureManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

@RestController
@RequestMapping(value = "/api/service/abixen/web-content/control-panel/structures")
public class StructureController {

    private final StructureManagementService structureManagementService;

    @Autowired
    public StructureController(StructureManagementService structureManagementService) {
        this.structureManagementService = structureManagementService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto<StructureForm> createStructure(@RequestBody @Valid StructureForm structureForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(structureForm, formErrors);
        }

        structureManagementService.createStructure(structureForm);

        return new FormValidationResultDto<>(structureForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto<StructureForm> updateStructure(@PathVariable("id") Long id, @RequestBody @Valid StructureForm structureForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(structureForm, formErrors);
        }

        structureManagementService.updateStructure(structureForm);

        return new FormValidationResultDto<>(structureForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteStructure(@PathVariable Long id) {
        structureManagementService.deleteStructure(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StructureDto getStructure(@PathVariable Long id) {
        return structureManagementService.findStructure(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<StructureDto> getStructures(@PageableDefault(size = 1) Pageable pageable) {
        return structureManagementService.findAllStructures(pageable);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<StructureDto> getAllStructures(@PageableDefault(size = 1) Pageable pageable) {
        return structureManagementService.findAllStructures();
    }
}