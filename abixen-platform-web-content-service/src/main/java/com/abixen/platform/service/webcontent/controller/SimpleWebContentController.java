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

import com.abixen.platform.common.dto.FormErrorDto;
import com.abixen.platform.common.dto.FormValidationResultDto;
import com.abixen.platform.common.util.ValidationUtil;
import com.abixen.platform.common.util.WebModelJsonSerialize;
import com.abixen.platform.service.webcontent.form.SimpleWebContentForm;
import com.abixen.platform.service.webcontent.model.web.SimpleWebContentWeb;
import com.abixen.platform.service.webcontent.service.SimpleWebContentService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/service/abixen/web-content/control-panel/simple-web-contents")
public class SimpleWebContentController {

    private final SimpleWebContentService simpleWebContentService;

    @Autowired
    public SimpleWebContentController(SimpleWebContentService simpleWebContentService) {
        this.simpleWebContentService = simpleWebContentService;
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SimpleWebContentWeb getSimpleWebContent(@PathVariable Long id) {
        log.debug("getSimpleWebContent() - id: {}", id);

        return simpleWebContentService.findSimpleWebContentById(id);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createSimpleWebContent(@RequestBody @Valid SimpleWebContentForm simpleWebContentForm, BindingResult bindingResult) {
        log.debug("createSimpleWebContentForm() - simpleWebContentForm: {}", simpleWebContentForm);
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(simpleWebContentForm, formErrors);
        }

        simpleWebContentService.createSimpleWebContent(simpleWebContentForm);

        return new FormValidationResultDto(simpleWebContentForm);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateSimpleWebContent(@PathVariable("id") Long id, @RequestBody @Valid SimpleWebContentForm simpleWebContentForm, BindingResult bindingResult) {
        log.debug("updateSimpleWebContent() - id: {}, simpleWebContentForm: {}", id, simpleWebContentForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(simpleWebContentForm, formErrors);
        }

        simpleWebContentService.updateSimpleWebContent(simpleWebContentForm);

        return new FormValidationResultDto(simpleWebContentForm);
    }

}