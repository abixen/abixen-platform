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

import com.abixen.platform.service.webcontent.application.converter.WebContentModuleConfigurationToWebContentModuleConfigurationDtoConverter;
import com.abixen.platform.service.webcontent.application.dto.WebContentModuleConfigurationDto;
import com.abixen.platform.service.webcontent.application.form.WebContentModuleConfigForm;
import com.abixen.platform.service.webcontent.domain.model.WebContent;
import com.abixen.platform.service.webcontent.domain.model.WebContentModuleConfig;
import com.abixen.platform.service.webcontent.domain.service.WebContentModuleConfigurationService;
import com.abixen.platform.service.webcontent.domain.service.WebContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class WebContentModuleConfigurationManagementService {

    private final WebContentModuleConfigurationService webContentModuleConfigurationService;
    private final WebContentService webContentService;
    private final WebContentModuleConfigurationToWebContentModuleConfigurationDtoConverter converter;

    @Autowired
    public WebContentModuleConfigurationManagementService(WebContentModuleConfigurationService webContentModuleConfigurationService,
                                                          WebContentService webContentService,
                                                          WebContentModuleConfigurationToWebContentModuleConfigurationDtoConverter converter) {
        this.webContentModuleConfigurationService = webContentModuleConfigurationService;
        this.webContentService = webContentService;
        this.converter = converter;
    }

    public WebContentModuleConfigurationDto findWebContentModuleConfiguration(final Long moduleId) {
        final WebContentModuleConfig webContentModuleConfig = webContentModuleConfigurationService.findByModuleId(moduleId);

        return converter.convert(webContentModuleConfig);
    }

    public WebContentModuleConfigurationDto createWebContentModuleConfiguration(final WebContentModuleConfigForm webContentModuleConfigForm) {
        final WebContent webContent = webContentService.find(webContentModuleConfigForm.getContentId());
        final WebContentModuleConfig webContentModuleConfig = WebContentModuleConfig.builder()
                .moduleId(webContentModuleConfigForm.getModuleId())
                .webContent(webContent)
                .build();

        final WebContentModuleConfig createdWebContentModuleConfig = webContentModuleConfigurationService.create(webContentModuleConfig);

        return converter.convert(createdWebContentModuleConfig);
    }

    public WebContentModuleConfigurationDto updateWebContentModuleConfiguration(WebContentModuleConfigForm webContentModuleConfigForm) {
        final WebContentModuleConfig webContentModuleConfig = webContentModuleConfigurationService.findByModuleId(webContentModuleConfigForm.getModuleId());
        final WebContent webContent = webContentService.find(webContentModuleConfigForm.getContentId());
        webContentModuleConfig.changeWebContent(webContent);
        final WebContentModuleConfig updatedWebContentModuleConfig = webContentModuleConfigurationService.update(webContentModuleConfig);

        return converter.convert(updatedWebContentModuleConfig);
    }
}