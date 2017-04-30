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
import com.abixen.platform.service.webcontent.dto.StructureDto;
import com.abixen.platform.service.webcontent.model.impl.Structure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StructureToStructureDtoConverter extends AbstractConverter<Structure, StructureDto> {

    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;
    private final TemplateToTemplateDtoConverter templateToTemplateDtoConverter;

    @Autowired
    public StructureToStructureDtoConverter(AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter,
                                            TemplateToTemplateDtoConverter templateToTemplateDtoConverter) {
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
        this.templateToTemplateDtoConverter = templateToTemplateDtoConverter;
    }

    @Override
    public StructureDto convert(Structure structure, Map<String, Object> parameters) {
        StructureDto structureDto = new StructureDto();

        structureDto.setId(structure.getId())
                .setContent(structure.getContent())
                .setName(structure.getName())
                .setTemplate(templateToTemplateDtoConverter.convert(structure.getTemplate()));

        auditingModelToAuditingDtoConverter.convert(structure, structureDto);

        return structureDto;
    }
}
