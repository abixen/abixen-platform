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
import com.abixen.platform.common.infrastructure.exception.PlatformRuntimeException;
import com.abixen.platform.service.webcontent.application.converter.WebContentToWebContentDtoConverter;
import com.abixen.platform.service.webcontent.application.dto.WebContentDto;
import com.abixen.platform.service.webcontent.domain.model.AdvancedWebContent;
import com.abixen.platform.service.webcontent.domain.model.WebContent;
import com.abixen.platform.service.webcontent.domain.service.WebContentService;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WebContentAssemblingService {

    private final WebContentService webContentService;
    private final WebContentToWebContentDtoConverter webContentToWebContentDtoConverter;
    private final AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter;

    @Autowired
    public WebContentAssemblingService(WebContentService webContentService,
                                       WebContentToWebContentDtoConverter webContentToWebContentDtoConverter,
                                       AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter) {
        this.webContentService = webContentService;
        this.webContentToWebContentDtoConverter = webContentToWebContentDtoConverter;
        this.auditingModelToSimpleAuditingDtoConverter = auditingModelToSimpleAuditingDtoConverter;
    }

    public WebContentDto findAndAssembleWebContent(Long webContentId) {
        log.debug("findAndAssembleWebContent() - webContentId: {}", webContentId);

        final WebContent webContent = webContentService.find(webContentId);

        final WebContentDto webContentDto;

        switch (webContent.getType()) {
            case SIMPLE:
                webContentDto = webContentToWebContentDtoConverter.convert(webContent);
                break;
            case ADVANCED:
                webContentDto = assembleAdvanceWebContent((AdvancedWebContent) webContent);
                break;
            default:
                throw new UnsupportedOperationException("Web content type: " + webContent.getType() + " is unsupported.");
        }

        return webContentDto;
    }

    private WebContentDto assembleAdvanceWebContent(AdvancedWebContent advancedWebContent) {
        String content = advancedWebContent.getStructure().getTemplate().getContent();
        final String dataForContent = advancedWebContent.getContent();
        final Map<String, String> values = getValuesForVariables(getParsedXml(dataForContent));
        content = fillContentByData(content, values);

        final WebContentDto webContentDto = new WebContentDto();
        webContentDto.setTitle(advancedWebContent.getTitle())
                .setType(advancedWebContent.getType())
                .setContent(content);

        auditingModelToSimpleAuditingDtoConverter.convert(advancedWebContent, webContentDto);

        return webContentDto;
    }

    private String fillContentByData(final String contentToFill, final Map<String, String> values) {
        String localContentToFill = contentToFill;
        for (String variable : findAllVariables(localContentToFill)) {
            final String cleanVariable = variable.replace("${", "").replace("}", "");
            localContentToFill = localContentToFill.replace(variable, values.get(cleanVariable));
        }
        return localContentToFill;
    }

    private List<String> findAllVariables(final String contentWithoutData) {
        final List<String> variables = new ArrayList<>();
        final Matcher result = Pattern.compile("\\$\\{(.*?)}").matcher(contentWithoutData);
        while (result.find()) {
            variables.add(result.group());
        }
        return variables;
    }

    private Map<String, String> getValuesForVariables(final Document parsedXml) {
        final Map<String, String> values = new HashMap<>();
        final NodeList fieldsList = parsedXml.getElementsByTagName("field");

        for (int i = 0; i < fieldsList.getLength(); i++) {
            final Element field = (Element) fieldsList.item(i);
            values.put(field.getAttribute("name"), field.getAttribute("value"));
        }

        return values;
    }

    private Document getParsedXml(final String dataForContent) {
        final DOMParser parser = new DOMParser();
        try {
            parser.parse(new InputSource(new java.io.StringReader(dataForContent)));
            return parser.getDocument();
        } catch (SAXException | IOException e) {
            throw new PlatformRuntimeException("Can't parse structure for advanced web content. Please check configuration.");
        }
    }
}