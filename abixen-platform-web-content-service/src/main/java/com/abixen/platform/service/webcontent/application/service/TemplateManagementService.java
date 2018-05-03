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

package com.abixen.platform.service.webcontent.application.service;

import com.abixen.platform.service.webcontent.application.converter.TemplateToTemplateDtoConverter;
import com.abixen.platform.service.webcontent.application.dto.TemplateDto;
import com.abixen.platform.service.webcontent.application.form.TemplateForm;
import com.abixen.platform.service.webcontent.domain.model.Template;
import com.abixen.platform.service.webcontent.domain.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class TemplateManagementService {

    private final TemplateToTemplateDtoConverter templateToTemplateDtoConverter;
    private final TemplateService templateService;

    @Autowired
    public TemplateManagementService(TemplateToTemplateDtoConverter templateToTemplateDtoConverter,
                                     TemplateService templateService) {
        this.templateToTemplateDtoConverter = templateToTemplateDtoConverter;
        this.templateService = templateService;
    }

    public TemplateDto createTemplate(TemplateForm templateForm) {
        final Template template = Template.builder()
                .name(templateForm.getName())
                .content(templateForm.getContent())
                .build();
        final Template createdTemplate = templateService.create(template);

        return templateToTemplateDtoConverter.convert(createdTemplate);
    }

    public TemplateDto updateTemplate(TemplateForm templateForm) {
        Template createdTemplate = templateService.updateTemplate(templateForm);
        TemplateDto createdTemplateDto = templateToTemplateDtoConverter.convert(createdTemplate);
        return createdTemplateDto;
    }

    public void deleteTemplate(Long templateId) {
        templateService.deleteTemplate(templateId);
    }

    public TemplateDto findTemplate(Long templateId) {
        Template template = templateService.findTemplate(templateId);
        TemplateDto templateDto = templateToTemplateDtoConverter.convert(template);
        return templateDto;
    }

    public Page<TemplateDto> findAllTemplates(Pageable pageable) {
        Page<Template> templates = templateService.findAllTemplates(pageable);
        Page<TemplateDto> templateDtos = templateToTemplateDtoConverter.convertToPage(templates);
        return templateDtos;
    }

    public List<TemplateDto> findAllTemplates() {
        List<Template> templates = templateService.findAllTemplates();
        List<TemplateDto> templateDtos = templateToTemplateDtoConverter.convertToList(templates);
        return templateDtos;
    }

    public List<String> getTemplateVariables(Long templateId) {
        return templateService.getTemplateVariables(templateId);
    }
}