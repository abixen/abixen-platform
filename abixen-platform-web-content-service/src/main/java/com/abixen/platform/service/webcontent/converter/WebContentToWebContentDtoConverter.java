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
import com.abixen.platform.service.webcontent.dto.AdvancedWebContentDto;
import com.abixen.platform.service.webcontent.dto.SimpleWebContentDto;
import com.abixen.platform.service.webcontent.dto.StructureDto;
import com.abixen.platform.service.webcontent.dto.WebContentDto;
import com.abixen.platform.service.webcontent.model.impl.AdvancedWebContent;
import com.abixen.platform.service.webcontent.model.impl.WebContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WebContentToWebContentDtoConverter extends AbstractConverter<WebContent, WebContentDto> {

    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;
    private final StructureToStructureDtoConverter structureToStructureDtoConverter;

    @Autowired
    public WebContentToWebContentDtoConverter(AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter,
                                              StructureToStructureDtoConverter structureToStructureDtoConverter) {
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
        this.structureToStructureDtoConverter = structureToStructureDtoConverter;
    }

    @Override
    public WebContentDto convert(WebContent webContent, Map<String, Object> parameters) {
        WebContentDto webContentDto = null;

        switch (webContent.getType()) {
            case SIMPLE:
                webContentDto = new SimpleWebContentDto();

                webContentDto
                        .setId(webContent.getId())
                        .setTitle(webContent.getTitle())
                        .setContent(webContent.getContent())
                        .setType(webContent.getType());

                break;
            case ADVANCED:
                webContentDto = new AdvancedWebContentDto();

                webContentDto
                        .setId(webContent.getId())
                        .setTitle(webContent.getTitle())
                        .setContent(webContent.getContent())
                        .setType(webContent.getType());
                StructureDto structure = structureToStructureDtoConverter.convert(((AdvancedWebContent) webContent).getStructure());
                ((AdvancedWebContentDto) webContentDto).setStructure(structure);
                break;
            default:
        }

        auditingModelToAuditingDtoConverter.convert(webContent, webContentDto);

        return webContentDto;
    }
}