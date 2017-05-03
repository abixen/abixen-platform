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
import com.abixen.platform.service.webcontent.converter.TemplateToTemplateDtoConverter;
import com.abixen.platform.service.webcontent.dto.TemplateDto;
import com.abixen.platform.service.webcontent.form.TemplateForm;
import com.abixen.platform.service.webcontent.model.impl.Template;
import com.abixen.platform.service.webcontent.service.TemplateService;
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
@RequestMapping(value = "/api/service/abixen/web-content/control-panel/templates")
public class TemplateController {

    private final TemplateService templateService;
    private final TemplateToTemplateDtoConverter templateToTemplateDtoConverter;

    @Autowired
    public TemplateController(TemplateService templateService,
                              TemplateToTemplateDtoConverter templateToTemplateDtoConverter) {
        this.templateService = templateService;
        this.templateToTemplateDtoConverter = templateToTemplateDtoConverter;
    }

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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeTemplate(@PathVariable Long id) {
        log.debug("removeTemplate() - id: {}", id);

        templateService.removeTemplate(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TemplateDto getTemplate(@PathVariable Long id) {
        log.debug("getTemplate() - id: {}", id);

        Template template = templateService.findTemplateById(id);
        return templateToTemplateDtoConverter.convert(template);
    }

    @RequestMapping(value = "/{id}/variables", method = RequestMethod.GET)
    public List<String> getTemplateVariables(@PathVariable Long id) {
        log.debug("getTemplateVariables() - id: {}", id);

        List<String> variables = templateService.getTemplateVariables(id);

        return variables;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<TemplateDto> getTemplates(@PageableDefault(size = 1) Pageable pageable) {
        log.debug("getTemplates() - pageable: {}", pageable);

        Page<Template> templates = templateService.findAllTemplates(pageable);
        Page<TemplateDto> templatesDtos = templateToTemplateDtoConverter.convertToPage(templates);

        return templatesDtos;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<TemplateDto> getTemplates() {
        log.debug("getTemplates()");

        List<Template> templates = templateService.findAllTemplates();
        List<TemplateDto> templatesDtos = templateToTemplateDtoConverter.convertToList(templates);

        return templatesDtos;
    }
}