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

import com.abixen.platform.common.application.converter.AuditingModelToSimpleAuditingDtoConverter;
import com.abixen.platform.service.webcontent.application.converter.WebContentToWebContentDtoConverter;
import com.abixen.platform.service.webcontent.application.dto.WebContentDto;
import com.abixen.platform.service.webcontent.application.form.AdvancedWebContentForm;
import com.abixen.platform.service.webcontent.application.form.SimpleWebContentForm;
import com.abixen.platform.service.webcontent.application.form.WebContentForm;
import com.abixen.platform.service.webcontent.domain.model.AdvancedWebContent;
import com.abixen.platform.service.webcontent.domain.model.SimpleWebContent;
import com.abixen.platform.service.webcontent.domain.model.Structure;
import com.abixen.platform.service.webcontent.domain.model.WebContent;
import com.abixen.platform.service.webcontent.domain.service.StructureService;
import com.abixen.platform.service.webcontent.domain.service.WebContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional
@Service
public class WebContentManagementService {

    private final WebContentService webContentService;
    private final StructureService structureService;
    private final WebContentToWebContentDtoConverter webContentToWebContentDtoConverter;
    private final AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter;

    @Autowired
    public WebContentManagementService(WebContentService webContentService,
                                       StructureService structureService,
                                       WebContentToWebContentDtoConverter webContentToWebContentDtoConverter,
                                       AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter) {
        this.webContentService = webContentService;
        this.structureService = structureService;
        this.webContentToWebContentDtoConverter = webContentToWebContentDtoConverter;
        this.auditingModelToSimpleAuditingDtoConverter = auditingModelToSimpleAuditingDtoConverter;
    }

    public WebContentDto findWebContent(final Long webContentId) {
        log.debug("findWebContent() - webContentId: {}", webContentId);

        final WebContent webContent = webContentService.find(webContentId);

        return webContentToWebContentDtoConverter.convert(webContent);
    }

    public Page<WebContentDto> findAllWebContents(final Pageable pageable) {
        log.debug("findAllWebContents() - pageable: {}", pageable);

        final Page<WebContent> webContents = webContentService.findAll(pageable);

        return webContentToWebContentDtoConverter.convertToPage(webContents);
    }

    public WebContentDto createWebContent(final WebContentForm webContentForm) {
        log.debug("createWebContent() - webContentForm: {}", webContentForm);

        final WebContent webContent;
        switch (webContentForm.getType()) {
            case SIMPLE:
                final SimpleWebContentForm simpleWebContentForm = (SimpleWebContentForm) webContentForm;
                webContent = SimpleWebContent.builder()
                        .content(simpleWebContentForm.getContent())
                        .title(simpleWebContentForm.getTitle())
                        .build();
                break;

            case ADVANCED:
                final AdvancedWebContentForm advancedWebContentForm = (AdvancedWebContentForm) webContentForm;
                final Structure structure = structureService.find(advancedWebContentForm.getStructure().getId());
                webContent = AdvancedWebContent.builder()
                        .content(advancedWebContentForm.getContent())
                        .title(advancedWebContentForm.getTitle())
                        .structure(structure)
                        .build();
                break;
            default:
                throw new UnsupportedOperationException("Web content type: " + webContentForm.getType() + " is unsupported.");
        }

        final WebContent createdWebContent = webContentService.create(webContent);

        return webContentToWebContentDtoConverter.convert(createdWebContent);
    }

    public WebContentDto updateWebContent(final WebContentForm webContentForm) {
        log.debug("updateWebContent() - webContentForm: {}", webContentForm);

        final WebContent webContent = webContentService.find(webContentForm.getId());
        switch (webContentForm.getType()) {
            case SIMPLE:
                final SimpleWebContentForm simpleWebContentForm = (SimpleWebContentForm) webContentForm;
                webContent.changeContent(simpleWebContentForm.getContent());
                webContent.changeTitle(simpleWebContentForm.getTitle());
                break;

            case ADVANCED:
                final AdvancedWebContentForm advancedWebContentForm = (AdvancedWebContentForm) webContentForm;
                final Structure structure = structureService.find(advancedWebContentForm.getStructure().getId());
                webContent.changeContent(advancedWebContentForm.getContent());
                webContent.changeTitle(advancedWebContentForm.getTitle());
                ((AdvancedWebContent) webContent).changeStructure(structure);
                break;
            default:
                throw new UnsupportedOperationException("Web content type: " + webContentForm.getType() + " is unsupported.");
        }

        final WebContent updatedWebContent = webContentService.update(webContent);

        return webContentToWebContentDtoConverter.convert(updatedWebContent);
    }

    public void deleteWebContent(Long webContentId) {
        log.debug("deleteWebContent() - webContentId: {}", webContentId);

        webContentService.delete(webContentId);
    }
}