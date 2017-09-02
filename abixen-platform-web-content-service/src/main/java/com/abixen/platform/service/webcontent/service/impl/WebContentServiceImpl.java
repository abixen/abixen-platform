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

import com.abixen.platform.common.converter.AuditingModelToSimpleAuditingDtoConverter;
import com.abixen.platform.service.webcontent.converter.WebContentToWebContentDtoConverter;
import com.abixen.platform.service.webcontent.form.AdvancedWebContentForm;
import com.abixen.platform.service.webcontent.form.SimpleWebContentForm;
import com.abixen.platform.service.webcontent.form.WebContentForm;
import com.abixen.platform.service.webcontent.model.impl.AdvancedWebContent;
import com.abixen.platform.service.webcontent.model.impl.SimpleWebContent;
import com.abixen.platform.service.webcontent.model.impl.Structure;
import com.abixen.platform.service.webcontent.model.impl.WebContent;
import com.abixen.platform.service.webcontent.repository.WebContentRepository;
import com.abixen.platform.service.webcontent.service.StructureService;
import com.abixen.platform.service.webcontent.service.WebContentService;
import com.abixen.platform.service.webcontent.util.AdvancedWebContentBuilder;
import com.abixen.platform.service.webcontent.util.SimpleWebContentBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class WebContentServiceImpl implements WebContentService {

    private final WebContentRepository webContentRepository;
    private final StructureService structureService;
    private final WebContentToWebContentDtoConverter webContentToWebContentDtoConverter;
    private final AuditingModelToSimpleAuditingDtoConverter auditingModelToAuditingDtoConverter;

    @Autowired
    public WebContentServiceImpl(WebContentRepository webContentRepository,
                                 StructureService structureService,
                                 WebContentToWebContentDtoConverter webContentToWebContentDtoConverter,
                                 AuditingModelToSimpleAuditingDtoConverter auditingModelToAuditingDtoConverter) {
        this.webContentRepository = webContentRepository;
        this.structureService = structureService;
        this.webContentToWebContentDtoConverter = webContentToWebContentDtoConverter;
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
    }

    @Override
    public WebContent createWebContent(WebContentForm webContentForm) {
        log.debug("createWebContent() - webContentForm: {}", webContentForm);

        WebContent webContent = buildWebContent(webContentForm, null);

        return webContentRepository.save(webContent);
    }

    @Override
    public WebContent updateWebContent(WebContentForm webContentForm) {
        log.debug("updateAdvancedWebContent() - webContentForm: {}", webContentForm);
        WebContent webContentForUpdate = webContentRepository.findOne(webContentForm.getId());
        WebContent webContent = buildWebContent(webContentForm, webContentForUpdate);
        return webContentRepository.save(webContent);
    }

    @Override
    public WebContent findWebContent(Long id) {
        return webContentRepository.findOne(id);
    }

    private WebContent buildWebContent(WebContentForm webContentForm, WebContent webContent) {
        switch (webContentForm.getType()) {
            case SIMPLE:
                SimpleWebContentForm simpleWebContentForm = (SimpleWebContentForm) webContentForm;
                SimpleWebContentBuilder simpleWebContentBuilder;
                if (webContent == null) {
                    simpleWebContentBuilder = new SimpleWebContentBuilder();
                } else {
                    simpleWebContentBuilder = new SimpleWebContentBuilder((SimpleWebContent) webContent);
                }
                simpleWebContentBuilder.content(simpleWebContentForm.getContent());
                simpleWebContentBuilder.title(simpleWebContentForm.getTitle());
                webContent = simpleWebContentBuilder.build();
                break;

            case ADVANCED:
                AdvancedWebContentForm advancedWebContentForm = (AdvancedWebContentForm) webContentForm;
                AdvancedWebContentBuilder advancedWebContentBuilder;
                if (webContent == null) {
                    advancedWebContentBuilder = new AdvancedWebContentBuilder();
                } else {
                    advancedWebContentBuilder = new AdvancedWebContentBuilder((AdvancedWebContent) webContent);
                }
                advancedWebContentBuilder.content(advancedWebContentForm.getContent());
                advancedWebContentBuilder.title(advancedWebContentForm.getTitle());
                Structure structure = structureService.findStructure(advancedWebContentForm.getStructure().getId());
                advancedWebContentBuilder.structure(structure);
                webContent = advancedWebContentBuilder.build();
                break;
            default:
        }
        return webContent;
    }

    @Override
    public Page<WebContent> findWebContents(Pageable pageable) {
        log.debug("findWebContents() - pageable: {}", pageable);
        return webContentRepository.findAll(pageable);
    }

    @Override
    public void deleteWebContent(Long webContentId) {
        log.debug("deleteWebContent() - webContentId={}", webContentId);
        webContentRepository.delete(webContentId);
    }
}