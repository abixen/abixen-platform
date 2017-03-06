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

import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.util.ValidationUtil;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.service.webcontent.form.StructureForm;
import com.abixen.platform.service.webcontent.model.impl.Structure;
import com.abixen.platform.service.webcontent.model.web.StructureWeb;
import com.abixen.platform.service.webcontent.service.StructureService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/service/abixen/web-content/control-panel/structures")
public class StructureController {

    private final StructureService structureService;

    @Autowired
    public StructureController(StructureService structureService) {
        this.structureService = structureService;
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createStructure(@RequestBody @Valid StructureForm structureForm, BindingResult bindingResult) {
        log.debug("createStructure() - structureForm: {}", structureForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(structureForm, formErrors);
        }

        structureService.createStructure(structureForm);

        return new FormValidationResultDto(structureForm);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateStructure(@PathVariable("id") Long id, @RequestBody @Valid StructureForm structureForm, BindingResult bindingResult) {
        log.debug("updateStructure() - id: {}, structureForm: {}", id, structureForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(structureForm, formErrors);
        }

        structureService.updateStructure(structureForm);

        return new FormValidationResultDto(structureForm);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeStructure(@PathVariable Long id) {
        log.debug("removeStructure() - id: {}", id);

        structureService.removeStructure(id);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StructureWeb getStructure(@PathVariable Long id) {
        log.debug("getStructure() - id: {}", id);

        return structureService.findStructureById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Structure> getStructures(@PageableDefault(size = 1) Pageable pageable) {
        log.debug("getStructures() - pageable: {}", pageable);

        Page<Structure> structures = structureService.findAllStructures(pageable);
        for (Structure structure : structures) {
            log.debug("structure: " + structure);
        }

        return structures;
    }
}
