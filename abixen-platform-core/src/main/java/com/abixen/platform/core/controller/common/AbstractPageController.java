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

package com.abixen.platform.core.controller.common;

import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.form.PageForm;
import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.core.service.PageService;
import com.abixen.platform.core.util.ValidationUtil;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;


public abstract class AbstractPageController {

    private final Logger log = Logger.getLogger(AbstractPageController.class.getName());

    private static final int PAGEABLE_DEFAULT_PAGE_SIZE = 100;

    private final PageService pageService;

    public AbstractPageController(PageService pageService) {
        this.pageService = pageService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public org.springframework.data.domain.Page<Page> getPages(@PageableDefault(size = PAGEABLE_DEFAULT_PAGE_SIZE) Pageable pageable) {
        log.debug("getPages()");

        org.springframework.data.domain.Page<Page> pages = pageService.findAllPages(pageable);
        for (Page page : pages) {
            log.debug("page: " + page);
        }

        return pages;
    }


    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createPage(@RequestBody @Valid PageForm pageForm, BindingResult bindingResult) {
        log.debug("createPage() - pageForm: " + pageForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(pageForm, formErrors);
        }

        PageForm pageFormResult = pageService.createPage(pageForm);

        return new FormValidationResultDto(pageFormResult);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deletePage(@PathVariable("id") long id) {
        log.debug("delete() - id: " + id);
        pageService.deletePage(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

}
