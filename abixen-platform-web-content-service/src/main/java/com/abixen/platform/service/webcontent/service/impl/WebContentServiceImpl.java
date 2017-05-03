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
import com.abixen.platform.service.webcontent.converter.AuditingModelToAuditingDtoConverter;
import com.abixen.platform.service.webcontent.converter.WebContentToWebContentDtoConverter;
import com.abixen.platform.service.webcontent.dto.WebContentDto;
import com.abixen.platform.service.webcontent.form.AdvancedWebContentForm;
import com.abixen.platform.service.webcontent.form.SearchWebContentForm;
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
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Transactional
@Service
public class WebContentServiceImpl implements WebContentService {

    private final WebContentRepository webContentRepository;
    private final StructureService structureService;
    private final WebContentToWebContentDtoConverter webContentToWebContentDtoConverter;
    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;

    @Autowired
    public WebContentServiceImpl(WebContentRepository webContentRepository,
                                 StructureService structureService,
                                 WebContentToWebContentDtoConverter webContentToWebContentDtoConverter,
                                 AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter) {
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
    public WebContent updateWebContent(WebContentForm advancedWebContentForm) {
        log.debug("updateAdvancedWebContent() - advancedWebContentForm: {}", advancedWebContentForm);
        WebContent webContentForUpdate = webContentRepository.findOne(advancedWebContentForm.getId());
        WebContent webContent = buildWebContent(advancedWebContentForm, webContentForUpdate);
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
                Structure structure = structureService.findStructureById(advancedWebContentForm.getStructure().getId());
                advancedWebContentBuilder.structure(structure);
                webContent = advancedWebContentBuilder.build();
                break;
            default:
        }
        return webContent;
    }

    @Override
    public Page<WebContent> getWebContents(Pageable pageable) {
        log.debug("getWebContents() - pageable: {}", pageable);
        return webContentRepository.findAll(pageable);
    }

    @Override
    public Page<WebContent> getWebContents(Pageable pageable, SearchWebContentForm searchWebContentForm) {
        log.debug("getWebContents() - pageable: {}", pageable);
        return webContentRepository.findAll(pageable);
    }

    @Override
    public void deleteWebContent(Long id) {
        log.debug("deleteWebContent() - id={}", id);
        webContentRepository.delete(id);
    }

    @Override
    public WebContentDto findAndAssembleWebContent(Long id) {
        WebContent webContent = findWebContent(id);

        WebContentDto webContentDto = null;

        switch (webContent.getType()) {
            case SIMPLE:
                webContentDto = webContentToWebContentDtoConverter.convert(webContent);
                break;
            case ADVANCED:
                webContentDto = assembleAdvanceWebContent((AdvancedWebContent) webContent);
                break;
            default:
        }

        return webContentDto;
    }

    private WebContentDto assembleAdvanceWebContent(AdvancedWebContent advancedWebContent) {
        String content = advancedWebContent.getStructure().getTemplate().getContent();
        String dataForContent = advancedWebContent.getContent();
        Map<String, String> values = getValuesForVariables(getParsedXml(dataForContent));
        content = fillContentByData(content, values);

        WebContentDto webContentDto = new WebContentDto();
        webContentDto.setTitle(advancedWebContent.getTitle())
                .setType(advancedWebContent.getType())
                .setContent(content);

        auditingModelToAuditingDtoConverter.convert(advancedWebContent, webContentDto);

        return webContentDto;
    }

    private String fillContentByData(String contentToFill, Map<String, String> values) {
        for (String variable : findAllVariables(contentToFill)) {
            String cleanVariable = variable.replace("${", "").replace("}", "");
            contentToFill = contentToFill.replace(variable, values.get(cleanVariable));
        }
        return contentToFill;
    }

    private List<String> findAllVariables(String contentWithoutData) {
        List<String> variables = new ArrayList<>();
        Matcher result = Pattern.compile("\\$\\{(.*?)}").matcher(contentWithoutData);
        while (result.find()) {
            variables.add(result.group());
        }
        return variables;
    }

    private Map<String, String> getValuesForVariables(Document parsedXml) {
        Map<String, String> values = new HashMap<>();
        NodeList fieldsList = parsedXml.getElementsByTagName("field");

        for (int i = 0; i < fieldsList.getLength(); i++) {
            Element field = (Element) fieldsList.item(i);
            values.put(field.getAttribute("name"), field.getAttribute("value"));
        }

        return values;
    }

    private Document getParsedXml(String dataForContent) {
        DOMParser parser = new DOMParser();
        try {
            parser.parse(new InputSource(new java.io.StringReader(dataForContent)));
            return parser.getDocument();
        } catch (SAXException | IOException e) {
            throw new PlatformRuntimeException("Can't parse structure for advanced web content. Please check configuration.");
        }
    }
}