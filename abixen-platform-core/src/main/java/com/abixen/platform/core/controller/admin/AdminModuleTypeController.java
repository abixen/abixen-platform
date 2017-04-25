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

import com.abixen.platform.core.converter.ModuleTypeToModuleTypeDtoConverter;
import com.abixen.platform.core.dto.ModuleTypeDto;
import com.abixen.platform.core.form.ModuleTypeSearchForm;
import com.abixen.platform.core.model.impl.ModuleType;
import com.abixen.platform.core.service.ModuleTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/control-panel/module-types")
public class AdminModuleTypeController {

    private final ModuleTypeService moduleTypeService;
    private final ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter;

    public AdminModuleTypeController(ModuleTypeService moduleTypeService,
                                     ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter) {
        this.moduleTypeService = moduleTypeService;
        this.moduleTypeToModuleTypeDtoConverter = moduleTypeToModuleTypeDtoConverter;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<ModuleTypeDto> getModuleTypes(@PageableDefault(size = 1, page = 0) Pageable pageable, ModuleTypeSearchForm moduleTypeSearchForm) {
        log.debug("getModuleTypes()");

        Page<ModuleType> moduleTypes = moduleTypeService.findModuleTypes(pageable, moduleTypeSearchForm);
        Page<ModuleTypeDto> moduleTypeDtos = moduleTypeToModuleTypeDtoConverter.convertToPage(moduleTypes);
        return moduleTypeDtos;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<ModuleTypeDto> getModuleTypes() {
        log.debug("getModuleTypes()");

        List<ModuleType> moduleTypes = moduleTypeService.findModuleTypes();
        List<ModuleTypeDto> moduleTypeDtos = moduleTypeToModuleTypeDtoConverter.convertToList(moduleTypes);
        return moduleTypeDtos;
    }

    @RequestMapping(value = "/{id}/reload", method = RequestMethod.PUT)
    public void reload(@PathVariable Long id) {
        log.debug("reload() - id: " + id);

        moduleTypeService.reload(id);
    }

    @RequestMapping(value = "/reload-all", method = RequestMethod.PUT)
    public void reloadAll() {
        log.debug("reloadAll()");

        moduleTypeService.reloadAll();
    }

}