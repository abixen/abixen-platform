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

import com.abixen.platform.core.model.impl.ModuleType;
import com.abixen.platform.core.service.ModuleTypeService;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/admin/module-types")
public class ModuleTypeController {

    private static Logger log = Logger.getLogger(ModuleTypeController.class.getName());

    @Autowired
    private ModuleTypeService moduleTypeService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<ModuleType> getModuleTypes(@PageableDefault(size = 1, page = 0) Pageable pageable) {
        log.debug("getModuleTypes()");

        return moduleTypeService.findAllModuleTypes(pageable);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}/reload", method = RequestMethod.PUT)
    public void reload(@PathVariable Long id) {
        log.debug("reload() - id: " + id);

        moduleTypeService.reload(id);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/reload-all", method = RequestMethod.PUT)
    public void reloadAll() {
        log.debug("reloadAll()");

        moduleTypeService.reloadAll();
    }

}