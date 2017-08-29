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

package com.abixen.platform.core.interfaces.web.application;

import com.abixen.platform.core.application.dto.LayoutDto;
import com.abixen.platform.core.application.service.LayoutManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/api/application/layouts")
public class ApplicationLayoutController {

    private final LayoutManagementService layoutManagementService;


    @Autowired
    public ApplicationLayoutController(LayoutManagementService layoutManagementService) {
        this.layoutManagementService = layoutManagementService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<LayoutDto> findAllDashboardLayouts() {
        log.debug("findAllDashboardLayouts()");

        final List<LayoutDto> layouts = layoutManagementService.findAllLayouts();

        for (LayoutDto layout : layouts) {
            layout.setContent(layout.getContentAsJson());
        }

        return layouts;
    }

}