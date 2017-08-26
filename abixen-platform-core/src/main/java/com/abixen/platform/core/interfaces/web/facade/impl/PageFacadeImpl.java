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

package com.abixen.platform.core.interfaces.web.facade.impl;

import com.abixen.platform.core.application.dto.PageDto;
import com.abixen.platform.core.application.form.PageForm;
import com.abixen.platform.core.application.form.PageSearchForm;
import com.abixen.platform.core.application.service.LayoutService;
import com.abixen.platform.core.application.service.PageService;
import com.abixen.platform.core.domain.model.Page;
import com.abixen.platform.core.interfaces.web.facade.PageFacade;
import com.abixen.platform.core.application.converter.PageToPageDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
public class PageFacadeImpl implements PageFacade {

    private final PageService pageService;
    private final LayoutService layoutService;
    private final PageToPageDtoConverter pageToPageDtoConverter;

    @Autowired
    public PageFacadeImpl(PageService pageService,
                          LayoutService layoutService,
                          PageToPageDtoConverter pageToPageDtoConverter) {
        this.pageService = pageService;
        this.layoutService = layoutService;
        this.pageToPageDtoConverter = pageToPageDtoConverter;
    }

    @Override
    public PageDto find(Long id) {
        Page page = pageService.find(id);

        return pageToPageDtoConverter.convert(page);
    }

    @Override
    public List<PageDto> findAll() {
        List<Page> pages = pageService.findAll();

        pages.forEach(page -> {
            layoutService.convertPageLayoutToJson(page);
        });

        return pageToPageDtoConverter.convertToList(pages);
    }

    @Override
    public org.springframework.data.domain.Page<PageDto> findAll(Pageable pageable, PageSearchForm pageSearchForm) {
        org.springframework.data.domain.Page<Page> pages = pageService.findAll(pageable, pageSearchForm);

        return pageToPageDtoConverter.convertToPage(pages);
    }

    @Override
    public PageForm create(PageForm pageForm) {
        return pageService.create(pageForm);
    }

    @Override
    public PageForm update(PageForm pageForm) {
        return pageService.update(pageForm);
    }

    @Override
    public void delete(Long id) {
        pageService.delete(id);
    }

}