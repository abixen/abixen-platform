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

import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.util.ValidationUtil;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.service.webcontent.form.TemplateForm;
import com.abixen.platform.service.webcontent.model.impl.Template;
import com.abixen.platform.service.webcontent.model.web.TemplateWeb;
import com.abixen.platform.service.webcontent.service.TemplateService;
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
@RequestMapping(value = "/admin/web-content-service/templates")
public class TemplateController {

    private final TemplateService templateService;

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createTemplate(@RequestBody @Valid TemplateForm templateForm, BindingResult bindingResult) {
        log.debug("createTemplate() - templateForm: {}", templateForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(templateForm, formErrors);
        }

        templateService.createTemplate(templateForm);

        return new FormValidationResultDto(templateForm);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateTemplate(@PathVariable("id") Long id, @RequestBody @Valid TemplateForm templateForm, BindingResult bindingResult) {
        log.debug("updateTemplate() - id: {}, templateForm: {}", id, templateForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(templateForm, formErrors);
        }

        templateService.updateTemplate(templateForm);

        return new FormValidationResultDto(templateForm);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeTemplate(@PathVariable Long id) {
        log.debug("removeTemplate() - id: {}", id);

        templateService.removeTemplate(id);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TemplateWeb getTemplate(@PathVariable Long id) {
        log.debug("getTemplate() - id: {}", id);

        return templateService.findTemplateById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Template> getTemplates(@PageableDefault(size = 1) Pageable pageable) {
        log.debug("getTemplates() - pageable: {}", pageable);

        Page<Template> templates = templateService.findAllTemplates(pageable);
        for (Template template : templates) {
            log.debug("template: " + template);
        }

        return templates;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Template> getTemplates() {
        log.debug("getTemplates()");

        List<Template> templates = templateService.findAllTemplates();

        return templates;
    }
}
