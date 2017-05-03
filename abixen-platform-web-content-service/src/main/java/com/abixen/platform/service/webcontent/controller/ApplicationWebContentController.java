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

import com.abixen.platform.service.webcontent.converter.WebContentToWebContentDtoConverter;
import com.abixen.platform.service.webcontent.dto.WebContentDto;
import com.abixen.platform.service.webcontent.service.WebContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/service/abixen/web-content/application/web-contents")
public class ApplicationWebContentController extends AbstractWebContentController {

    private final WebContentService webContentService;
    private final WebContentToWebContentDtoConverter webContentToWebContentDtoConverter;

    @Autowired
    public ApplicationWebContentController(WebContentService webContentService,
                                           WebContentToWebContentDtoConverter webContentToWebContentDtoConverter) {
        super(webContentService, webContentToWebContentDtoConverter);
        this.webContentService = webContentService;
        this.webContentToWebContentDtoConverter = webContentToWebContentDtoConverter;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public WebContentDto getWebContent(@PathVariable Long id) {
        log.debug("getSimpleWebContent() - id: {}", id);

        return webContentService.findAndAssembleWebContent(id);
    }
}