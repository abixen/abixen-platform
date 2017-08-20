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

package com.abixen.platform.core.interfaces.web.admin;

import com.abixen.platform.core.interfaces.web.common.AbstractPageController;
import com.abixen.platform.core.interfaces.converter.PageToPageDtoConverter;
import com.abixen.platform.common.dto.FormErrorDto;
import com.abixen.platform.common.dto.FormValidationResultDto;
import com.abixen.platform.core.application.dto.PageDto;
import com.abixen.platform.core.application.form.PageForm;
import com.abixen.platform.core.application.form.PageSearchForm;
import com.abixen.platform.core.domain.model.Page;
import com.abixen.platform.core.application.service.PageService;
import com.abixen.platform.common.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/api/control-panel/pages")
public class AdminPageController extends AbstractPageController {

    private static final int PAGEABLE_DEFAULT_PAGE_SIZE = 100;

    private final PageService pageService;
    private final PageToPageDtoConverter pageToPageDtoConverter;

    @Autowired
    public AdminPageController(PageService pageService,
                               PageToPageDtoConverter pageToPageDtoConverter) {
        super(pageService);
        this.pageService = pageService;
        this.pageToPageDtoConverter = pageToPageDtoConverter;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PageDto getPage(@PathVariable Long id) {
        log.debug("getPage() - id: " + id);

        Page page = pageService.find(id);
        return pageToPageDtoConverter.convert(page);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updatePage(@PathVariable("id") Long id, @RequestBody @Valid PageForm pageForm, BindingResult bindingResult) {
        log.debug("update() - id: " + id + ", pageForm: " + pageForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(pageForm, formErrors);
        }

        PageForm pageFormResult = pageService.update(pageForm);

        return new FormValidationResultDto(pageFormResult);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public org.springframework.data.domain.Page<PageDto> getPages(@PageableDefault(size = PAGEABLE_DEFAULT_PAGE_SIZE) Pageable pageable, PageSearchForm pageSearchForm) {
        log.debug("getPages()");

        org.springframework.data.domain.Page<Page> pages = pageService.findAll(pageable, pageSearchForm);


        return pageToPageDtoConverter.convertToPage(pages);
    }


    @PreAuthorize("hasPermission(null, 'com.abixen.platform.core.domain.model.impl.Page', 'PAGE_ADD')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createPage(@RequestBody @Valid PageForm pageForm, BindingResult bindingResult) {
        log.debug("create() - pageForm: " + pageForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(pageForm, formErrors);
        }

        PageForm pageFormResult = pageService.create(pageForm);

        return new FormValidationResultDto(pageFormResult);
    }

}