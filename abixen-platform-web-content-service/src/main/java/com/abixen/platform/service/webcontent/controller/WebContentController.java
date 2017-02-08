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

import com.abixen.platform.service.webcontent.model.impl.WebContent;
import com.abixen.platform.service.webcontent.service.WebContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/service/abixen/web-content/admin/web-contents")
public class WebContentController {

    private final WebContentService webContentService;

    @Autowired
    public WebContentController(WebContentService webContentService) {
        this.webContentService = webContentService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<WebContent> getWebContents(@PageableDefault(size = 1) Pageable pageable) {
        log.debug("getWebContents()- pageable: {}", pageable);

        Page<WebContent> webContents = webContentService.getWebContents(pageable);
        for (WebContent webContent : webContents) {
            log.debug("webContent: " + webContent);
        }

        return webContents;
    }
}
