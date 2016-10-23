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

import com.abixen.platform.core.controller.common.AbstractPageController;
import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.form.PageForm;
import com.abixen.platform.core.model.web.PageWeb;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.core.service.PageService;
import com.abixen.platform.core.util.ValidationUtil;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;



@RestController
@RequestMapping(value = "/api/admin/pages")
public class AdminPageController extends AbstractPageController {

    private final Logger log = Logger.getLogger(AdminPageController.class.getName());

    private final PageService pageService;

    @Autowired
    public AdminPageController(PageService pageService) {
        super(pageService);
        this.pageService = pageService;
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PageWeb getPage(@PathVariable Long id) {
        log.debug("getPage() - id: " + id);

        return pageService.findPage(id);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updatePage(@PathVariable("id") Long id, @RequestBody @Valid PageForm pageForm, BindingResult bindingResult) {
        log.debug("updatePage() - id: " + id + ", pageForm: " + pageForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(pageForm, formErrors);
        }

        PageForm pageFormResult = pageService.updatePage(pageForm);

        return new FormValidationResultDto(pageFormResult);
    }

}
