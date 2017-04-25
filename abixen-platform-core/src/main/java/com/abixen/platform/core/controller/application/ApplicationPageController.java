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

import com.abixen.platform.core.controller.common.AbstractPageController;
import com.abixen.platform.core.converter.PageToPageDtoConverter;
import com.abixen.platform.core.dto.PageDto;
import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.core.service.LayoutService;
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
public class ApplicationPageController extends AbstractPageController {

    private final PageService pageService;
    private final LayoutService layoutService;
    private final PageToPageDtoConverter pageToPageDtoConverter;

    @Autowired
    public ApplicationPageController(PageService pageService,
                                     LayoutService layoutService,
                                     PageToPageDtoConverter pageToPageDtoConverter) {
        super(pageService);
        this.pageService = pageService;
        this.layoutService = layoutService;
        this.pageToPageDtoConverter = pageToPageDtoConverter;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<PageDto> getPages() {
        log.debug("getPages()");

        List<Page> pages = pageService.findAllPages();

        pages.forEach(page -> {
            layoutService.convertPageLayoutToJson(page);
        });

        return pageToPageDtoConverter.convertToList(pages);
    }

}