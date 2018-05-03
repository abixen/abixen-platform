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

package com.abixen.platform.service.webcontent.domain.service;

import com.abixen.platform.common.infrastructure.exception.PlatformRuntimeException;
import com.abixen.platform.common.infrastructure.exception.PlatformServiceRuntimeException;
import com.abixen.platform.service.webcontent.application.form.TemplateForm;
import com.abixen.platform.service.webcontent.domain.model.Template;
import com.abixen.platform.service.webcontent.domain.repository.TemplateRepository;
import com.abixen.platform.service.webcontent.domain.util.ParserUtil;
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
public class TemplateService {

    private final TemplateRepository templateRepository;

    @Autowired
    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public Template create(Template template) {
        log.debug("create() - template: {}", template);

        return templateRepository.save(template);
    }

    public Template updateTemplate(TemplateForm templateForm) {
        log.debug("updateTemplate() - templateForm: {}", templateForm);

        final Template template = findTemplate(templateForm.getId());

        if (!template.getContent().equals(templateForm.getContent())) {
            final boolean templateUsed = templateRepository.isTemplateUsed(template);
            if (templateUsed) {
                throw new PlatformRuntimeException("You can not edit this content because the template is assigned to at least a one structure.");
            }
        }

        template.changeName(templateForm.getName());
        template.changeContent(templateForm.getContent());

        return templateRepository.save(template);
    }

    public void deleteTemplate(Long templateId) {
        log.debug("deleteTemplate() - templateId: {}", templateId);

        templateRepository.delete(templateId);
    }

    public Template findTemplate(Long templateId) {
        log.debug("findTemplateById() - templateId: {}", templateId);

        return templateRepository.findOne(templateId);
    }

    public Page<Template> findAllTemplates(Pageable pageable) {
        log.debug("findAllTemplates() - pageable: {}", pageable);

        return templateRepository.findAll(pageable);
    }

    public List<Template> findAllTemplates() {
        log.debug("findAllTemplates()");

        return templateRepository.findAll();
    }

    public List<String> getTemplateVariables(Long templateId) {
        log.debug("findTemplateById() - templateId: {}", templateId);

        final Template template = templateRepository.findOne(templateId);
        if (template == null) {
            throw new PlatformServiceRuntimeException(String.format("Template with id=%d not found", templateId));
        }
        final Set<String> templateVariablesSet = ParserUtil.evaluateEL(template.getContent());

        return new ArrayList<>(templateVariablesSet);
    }
}