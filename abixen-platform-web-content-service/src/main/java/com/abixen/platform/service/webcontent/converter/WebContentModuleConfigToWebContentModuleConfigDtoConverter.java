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
import com.abixen.platform.service.webcontent.dto.WebContentModuleConfigDto;
import com.abixen.platform.service.webcontent.model.impl.WebContentModuleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WebContentModuleConfigToWebContentModuleConfigDtoConverter extends AbstractConverter<WebContentModuleConfig, WebContentModuleConfigDto> {

    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;
    private final WebContentToWebContentDtoConverter webContentToWebContentDtoConverter;

    @Autowired
    public WebContentModuleConfigToWebContentModuleConfigDtoConverter(AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter,
                                                                      WebContentToWebContentDtoConverter webContentToWebContentDtoConverter) {
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
        this.webContentToWebContentDtoConverter = webContentToWebContentDtoConverter;
    }

    @Override
    public WebContentModuleConfigDto convert(WebContentModuleConfig webContentModuleConfig, Map<String, Object> parameters) {
        WebContentModuleConfigDto webContentModuleConfigDto = new WebContentModuleConfigDto();

        webContentModuleConfigDto.setId(webContentModuleConfig.getId())
                .setModuleId(webContentModuleConfig.getModuleId())
                .setContentId(webContentModuleConfig.getWebContent().getId());

        auditingModelToAuditingDtoConverter.convert(webContentModuleConfig, webContentModuleConfigDto);

        return webContentModuleConfigDto;
    }
}