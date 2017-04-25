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

import com.abixen.platform.core.converter.ResourceToResourceDtoConverter;
import com.abixen.platform.core.dto.ResourceDto;
import com.abixen.platform.core.model.impl.Resource;
import com.abixen.platform.core.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/control-panel/module-types")
public class AdminResourceController {

    private final ResourceService resourceService;
    private final ResourceToResourceDtoConverter resourceToResourceDtoConverter;

    @Autowired
    public AdminResourceController(ResourceService resourceService,
                                   ResourceToResourceDtoConverter resourceToResourceDtoConverter) {
        this.resourceService = resourceService;
        this.resourceToResourceDtoConverter = resourceToResourceDtoConverter;
    }

    @RequestMapping(value = "/{moduleId}/resources", method = RequestMethod.GET)
    public Page<ResourceDto> getResources(@PathVariable("moduleId") Long moduleId, @PageableDefault(size = 1, page = 0) Pageable pageable) {
        log.debug("getResources({})", moduleId);
        Page<Resource> resourcePage = resourceService.findAllResources(moduleId, pageable);
        return resourceToResourceDtoConverter.convertToPage(resourcePage);
    }
}