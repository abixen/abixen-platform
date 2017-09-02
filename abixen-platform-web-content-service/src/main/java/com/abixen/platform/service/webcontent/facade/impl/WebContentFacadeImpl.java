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

package com.abixen.platform.service.webcontent.facade.impl;

import com.abixen.platform.common.application.converter.AuditingModelToSimpleAuditingDtoConverter;
import com.abixen.platform.common.exception.PlatformRuntimeException;
import com.abixen.platform.service.webcontent.converter.WebContentToWebContentDtoConverter;
import com.abixen.platform.service.webcontent.dto.WebContentDto;
import com.abixen.platform.service.webcontent.facade.WebContentFacade;
import com.abixen.platform.service.webcontent.form.WebContentForm;
import com.abixen.platform.service.webcontent.model.impl.AdvancedWebContent;
import com.abixen.platform.service.webcontent.model.impl.WebContent;
import com.abixen.platform.service.webcontent.service.WebContentService;
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
public class WebContentFacadeImpl implements WebContentFacade {

    private final WebContentService webContentService;
    private final WebContentToWebContentDtoConverter webContentToWebContentDtoConverter;
    private final AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter;

    @Autowired
    public WebContentFacadeImpl(WebContentService webContentService,
                                WebContentToWebContentDtoConverter webContentToWebContentDtoConverter,
                                AuditingModelToSimpleAuditingDtoConverter auditingModelToSimpleAuditingDtoConverter) {
        this.webContentService = webContentService;
        this.webContentToWebContentDtoConverter = webContentToWebContentDtoConverter;
        this.auditingModelToSimpleAuditingDtoConverter = auditingModelToSimpleAuditingDtoConverter;
    }

    @Override
    public WebContentDto createWebContent(WebContentForm webContentForm) {
        WebContent createdWebContent = webContentService.createWebContent(webContentForm);
        WebContentDto createdWebContentDto = webContentToWebContentDtoConverter.convert(createdWebContent);
        return createdWebContentDto;
    }

    @Override
    public WebContentDto updateWebContent(WebContentForm webContentForm) {
        WebContent updatedWebContent = webContentService.updateWebContent(webContentForm);
        WebContentDto updatedWebContentDto = webContentToWebContentDtoConverter.convert(updatedWebContent);
        return updatedWebContentDto;
    }

    @Override
    public WebContentDto findWebContent(Long webContentId) {
        WebContent webContent = webContentService.findWebContent(webContentId);
        WebContentDto webContentDto = webContentToWebContentDtoConverter.convert(webContent);
        return webContentDto;
    }

    @Override
    public Page<WebContentDto> findWebContents(Pageable pageable) {
        Page<WebContent> webContents = webContentService.findWebContents(pageable);
        Page<WebContentDto> webContentDtos = webContentToWebContentDtoConverter.convertToPage(webContents);
        return webContentDtos;
    }

    @Override
    public void deleteWebContent(Long webContentId) {
        webContentService.deleteWebContent(webContentId);
    }

    @Override
    public WebContentDto findAndAssembleWebContent(Long webContentId) {
        WebContent webContent = webContentService.findWebContent(webContentId);

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

        auditingModelToSimpleAuditingDtoConverter.convert(advancedWebContent, webContentDto);

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