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
package com.abixen.platform.service.webcontent.converter;

import com.abixen.platform.common.converter.AbstractConverter;
import com.abixen.platform.service.webcontent.dto.TemplateDto;
import com.abixen.platform.service.webcontent.model.impl.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TemplateToTemplateDtoConverter extends AbstractConverter<Template, TemplateDto> {

    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;

    @Autowired
    public TemplateToTemplateDtoConverter(AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter) {
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
    }
    @Override
    public TemplateDto convert(Template template, Map<String, Object> parameters) {
        TemplateDto templateDto = new TemplateDto();

        templateDto.setId(template.getId())
                .setName(template.getName())
                .setContent(template.getContent());

        auditingModelToAuditingDtoConverter.convert(template, templateDto);

        return templateDto;
    }
}
