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

import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.core.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/application/pages")
public class ApplicationPageController {

    private final PageService pageService;

    @Autowired
    public ApplicationPageController(PageService pageService) {
        this.pageService = pageService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Page> getPages() {
        log.debug("getPages()");

        List<Page> pages = pageService.findAllPages();

        pages.forEach(page -> {
            log.debug("Page id={}, name={}", page.getId(), page.getName());
            pageService.convertPageLayoutToJson(page);
        });

        return pages;
    }

}