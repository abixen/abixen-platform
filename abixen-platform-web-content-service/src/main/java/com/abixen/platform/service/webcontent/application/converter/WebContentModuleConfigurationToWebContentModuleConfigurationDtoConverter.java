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
package com.abixen.platform.service.webcontent.application.converter;

import com.abixen.platform.common.application.converter.AbstractConverter;
import com.abixen.platform.common.application.converter.AuditingModelToSimpleAuditingDtoConverter;
import com.abixen.platform.service.webcontent.application.dto.WebContentModuleConfigurationDto;
import com.abixen.platform.service.webcontent.domain.model.WebContentModuleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WebContentModuleConfigurationToWebContentModuleConfigurationDtoConverter extends AbstractConverter<WebContentModuleConfig, WebContentModuleConfigurationDto> {

    private final AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter;

    @Autowired
    public WebContentModuleConfigurationToWebContentModuleConfigurationDtoConverter(AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter) {
        this.auditingModelToSimpleAuditingDtoConverter = auditingModelToSimpleAuditingDtoConverter;
    }

    @Override
    public WebContentModuleConfigurationDto convert(WebContentModuleConfig webContentModuleConfig, Map<String, Object> parameters) {
        if (webContentModuleConfig == null) {
            return null;
        }

        WebContentModuleConfigurationDto webContentModuleConfigurationDto = new WebContentModuleConfigurationDto();

        webContentModuleConfigurationDto.setId(webContentModuleConfig.getId())
                .setModuleId(webContentModuleConfig.getModuleId())
                .setContentId(webContentModuleConfig.getWebContent().getId());

        auditingModelToSimpleAuditingDtoConverter.convert(webContentModuleConfig, webContentModuleConfigurationDto);

        return webContentModuleConfigurationDto;
    }

}