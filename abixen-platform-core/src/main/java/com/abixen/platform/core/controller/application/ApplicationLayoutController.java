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

package com.abixen.platform.core.controller.application;

import com.abixen.platform.core.converter.LayoutToLayoutDtoConverter;
import com.abixen.platform.core.dto.LayoutDto;
import com.abixen.platform.core.model.impl.Layout;
import com.abixen.platform.core.service.LayoutService;
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

    private final LayoutService layoutService;
    private final LayoutToLayoutDtoConverter layoutToLayoutDtoConverter;

    @Autowired
    public ApplicationLayoutController(LayoutService layoutService,
                                       LayoutToLayoutDtoConverter layoutToLayoutDtoConverter) {
        this.layoutService = layoutService;
        this.layoutToLayoutDtoConverter = layoutToLayoutDtoConverter;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<LayoutDto> getDashboardLayouts() {
        log.debug("getLayouts()");

        List<Layout> layouts = layoutService.findAllLayouts();
        List<LayoutDto> layoutDtos = layoutToLayoutDtoConverter.convertToList(layouts);
        log.debug("getLayouts() count" + (layouts != null ? layouts.size() : 0));
        for (LayoutDto layout : layoutDtos) {
            log.debug("layout: " + layout);

            String html = layout.getContent();
            layout.setContent(layoutService.htmlLayoutToJson(html));
        }
        return layoutDtos;
    }
}
