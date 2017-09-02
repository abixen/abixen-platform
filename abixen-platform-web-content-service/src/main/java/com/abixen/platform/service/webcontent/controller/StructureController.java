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

package com.abixen.platform.service.webcontent.controller;

import com.abixen.platform.common.application.dto.FormErrorDto;
import com.abixen.platform.common.application.dto.FormValidationResultDto;
import com.abixen.platform.common.infrastructure.util.ValidationUtil;
import com.abixen.platform.service.webcontent.dto.StructureDto;
import com.abixen.platform.service.webcontent.facade.StructureFacade;
import com.abixen.platform.service.webcontent.form.StructureForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/service/abixen/web-content/control-panel/structures")
public class StructureController {

    private final StructureFacade structureFacade;

    @Autowired
    public StructureController(StructureFacade structureFacade) {
        this.structureFacade = structureFacade;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createStructure(@RequestBody @Valid StructureForm structureForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(structureForm, formErrors);
        }

        structureFacade.createStructure(structureForm);

        return new FormValidationResultDto(structureForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateStructure(@PathVariable("id") Long id, @RequestBody @Valid StructureForm structureForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(structureForm, formErrors);
        }

        structureFacade.updateStructure(structureForm);

        return new FormValidationResultDto(structureForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteStructure(@PathVariable Long id) {
        structureFacade.deleteStructure(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StructureDto getStructure(@PathVariable Long id) {
        return structureFacade.findStructure(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<StructureDto> getStructures(@PageableDefault(size = 1) Pageable pageable) {
        return structureFacade.findAllStructures(pageable);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<StructureDto> getAllStructures(@PageableDefault(size = 1) Pageable pageable) {
        return structureFacade.findAllStructures();
    }
}