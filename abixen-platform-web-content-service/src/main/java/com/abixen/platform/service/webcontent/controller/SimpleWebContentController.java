/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
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
import com.abixen.platform.service.webcontent.model.impl.SimpleWebContent;
import com.abixen.platform.service.webcontent.model.web.SimpleWebContentWeb;
import com.abixen.platform.service.webcontent.service.SimpleWebContentService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/service/abixen/web-content/control-panel/simple-web-contents")
public class SimpleWebContentController {

    private final SimpleWebContentService webContentService;

    @Autowired
    public SimpleWebContentController(SimpleWebContentService webContentService) {
        this.webContentService = webContentService;
    }


    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public FormValidationResultDto createSimpleWebContent(@RequestBody @Valid SimpleWebContentForm simpleWebContentForm, BindingResult bindingResult) {
        log.debug("createSimpleWebContentForm() - simpleWebContentForm: {}", simpleWebContentForm);
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(simpleWebContentForm, formErrors);
        }

        webContentService.createSimpleWebContent(simpleWebContentForm);

        return new FormValidationResultDto(simpleWebContentForm);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateSimpleWebContent(@PathVariable("id") Long id, @RequestBody @Valid SimpleWebContentForm simpleWebContentForm, BindingResult bindingResult) {
        log.debug("updateTemplate() - id: {}, templateForm: {}", id, simpleWebContentForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(simpleWebContentForm, formErrors);
        }

        webContentService.updateSimpleWebContent(simpleWebContentForm);

        return new FormValidationResultDto(simpleWebContentForm);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeSimpleWebContent(@PathVariable Long id) {
        log.debug("simpleWebContent() - id: {}", id);

        webContentService.removeSimpleWebContent(id);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SimpleWebContentWeb getTemplate(@PathVariable Long id) {
        log.debug("getSimpleWebContent() - id: {}", id);

        return webContentService.findSimpleWebContentById(id);
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<SimpleWebContent> getSimpleWebContent(@PageableDefault(size = 1) Pageable pageable) {
        log.debug("getSimpleWebContent() - pageable: {}", pageable);

        Page<SimpleWebContent> simpleWebContents = webContentService.findAllSimpleWebContents(pageable);
        for (SimpleWebContent simpleWebContent : simpleWebContents) {
            log.debug("template: " + simpleWebContent);
        }

        return simpleWebContents;
    }

}
