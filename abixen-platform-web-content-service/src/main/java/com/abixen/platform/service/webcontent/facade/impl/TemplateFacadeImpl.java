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

package com.abixen.platform.service.webcontent.facade.impl;

import com.abixen.platform.service.webcontent.converter.TemplateToTemplateDtoConverter;
import com.abixen.platform.service.webcontent.dto.TemplateDto;
import com.abixen.platform.service.webcontent.facade.TemplateFacade;
import com.abixen.platform.service.webcontent.form.TemplateForm;
import com.abixen.platform.service.webcontent.model.impl.Template;
import com.abixen.platform.service.webcontent.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class TemplateFacadeImpl implements TemplateFacade {

    private final TemplateToTemplateDtoConverter templateToTemplateDtoConverter;
    private final TemplateService templateService;

    @Autowired
    public TemplateFacadeImpl(TemplateToTemplateDtoConverter templateToTemplateDtoConverter,
                              TemplateService templateService) {
        this.templateToTemplateDtoConverter = templateToTemplateDtoConverter;
        this.templateService = templateService;
    }

    @Override
    public TemplateDto createTemplate(TemplateForm templateForm) {
        Template createdTemplate = templateService.createTemplate(templateForm);
        TemplateDto createdTemplateDto = templateToTemplateDtoConverter.convert(createdTemplate);
        return createdTemplateDto;
    }

    @Override
    public TemplateDto updateTemplate(TemplateForm templateForm) {
        Template createdTemplate = templateService.updateTemplate(templateForm);
        TemplateDto createdTemplateDto = templateToTemplateDtoConverter.convert(createdTemplate);
        return createdTemplateDto;
    }

    @Override
    public void deleteTemplate(Long templateId) {
        templateService.deleteTemplate(templateId);
    }

    @Override
    public TemplateDto findTemplate(Long templateId) {
        Template template = templateService.findTemplate(templateId);
        TemplateDto templateDto = templateToTemplateDtoConverter.convert(template);
        return templateDto;
    }

    @Override
    public Page<TemplateDto> findAllTemplates(Pageable pageable) {
        Page<Template> templates = templateService.findAllTemplates(pageable);
        Page<TemplateDto> templateDtos = templateToTemplateDtoConverter.convertToPage(templates);
        return templateDtos;
    }

    @Override
    public List<TemplateDto> findAllTemplates() {
        List<Template> templates = templateService.findAllTemplates();
        List<TemplateDto> templateDtos = templateToTemplateDtoConverter.convertToList(templates);
        return templateDtos;
    }

    @Override
    public List<String> getTemplateVariables(Long templateId) {
        return templateService.getTemplateVariables(templateId);
    }
}