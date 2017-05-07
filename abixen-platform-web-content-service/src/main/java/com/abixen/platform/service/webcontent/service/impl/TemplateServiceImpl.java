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

package com.abixen.platform.service.webcontent.service.impl;

import com.abixen.platform.common.exception.PlatformRuntimeException;
import com.abixen.platform.common.exception.PlatformServiceRuntimeException;
import com.abixen.platform.service.webcontent.form.TemplateForm;
import com.abixen.platform.service.webcontent.model.impl.Template;
import com.abixen.platform.service.webcontent.repository.TemplateRepository;
import com.abixen.platform.service.webcontent.service.TemplateService;
import com.abixen.platform.service.webcontent.util.ParserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Autowired
    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public Template createTemplate(TemplateForm templateForm) {
        log.debug("createTemplate() - templateForm: {}", templateForm);
        Template template = new Template();
        template.setName(templateForm.getName());
        template.setContent(templateForm.getContent());
        return templateRepository.save(template);
    }

    @Override
    public Template updateTemplate(TemplateForm templateForm) {
        log.debug("updateTemplate() - templateForm: {}", templateForm);
        Template template = findTemplate(templateForm.getId());

        if (!template.getContent().equals(templateForm.getContent())) {
            boolean templateUsed = templateRepository.isTemplateUsed(template);
            if (templateUsed) {
                throw new PlatformRuntimeException("You can not edit a content because the template is assigned to at least a one structure.");
            }
        }

        template.setName(templateForm.getName());
        template.setContent(templateForm.getContent());
        return templateRepository.save(template);
    }

    @Override
    public void deleteTemplate(Long templateId) {
        log.debug("deleteTemplate() - templateId: {}", templateId);
        templateRepository.delete(templateId);
    }

    @Override
    public Template findTemplate(Long templateId) {
        log.debug("findTemplateById() - templateId: {}", templateId);
        return templateRepository.findOne(templateId);
    }

    @Override
    public Page<Template> findAllTemplates(Pageable pageable) {
        log.debug("findAllTemplates() - pageable: {}", pageable);
        return templateRepository.findAll(pageable);
    }

    @Override
    public List<Template> findAllTemplates() {
        log.debug("findAllTemplates()");
        return templateRepository.findAll();
    }

    @Override
    public List<String> getTemplateVariables(Long templateId) {
        log.debug("findTemplateById() - templateId: {}", templateId);
        Template template = templateRepository.findOne(templateId);
        if (template == null) {
            throw new PlatformServiceRuntimeException(String.format("Template with id=%d not found", templateId));
        }
        Set<String> templateVariablesSet = ParserUtil.evaluateEL(template.getContent());
        List<String> templateVariables = new ArrayList<String>(templateVariablesSet);
        return templateVariables;
    }
}