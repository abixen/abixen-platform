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

import com.abixen.platform.common.infrastructure.exception.PlatformRuntimeException;
import com.abixen.platform.common.infrastructure.exception.PlatformServiceRuntimeException;
import com.abixen.platform.service.webcontent.form.StructureForm;
import com.abixen.platform.service.webcontent.model.impl.Structure;
import com.abixen.platform.service.webcontent.model.impl.Template;
import com.abixen.platform.service.webcontent.repository.StructureRepository;
import com.abixen.platform.service.webcontent.service.StructureService;
import com.abixen.platform.service.webcontent.service.TemplateService;
import com.abixen.platform.service.webcontent.util.ParserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class StructureServiceImpl implements StructureService {

    private final TemplateService templateService;
    private final StructureRepository structureRepository;

    @Autowired
    public StructureServiceImpl(TemplateService templateService,
                                StructureRepository structureRepository) {
        this.templateService = templateService;
        this.structureRepository = structureRepository;
    }


    @Override
    public Structure createStructure(StructureForm structureForm) {
        log.debug("createStructure() - structureForm: {}", structureForm);
        Structure structure = new Structure();
        structure.setName(structureForm.getName());
        structure.setContent(structureForm.getContent());
        Template template = templateService.findTemplate(structureForm.getTemplate().getId());
        validateStructureTemplate(structure, template);
        structure.setTemplate(template);
        return structureRepository.save(structure);
    }

    @Override
    public Structure updateStructure(StructureForm structureForm) {
        log.debug("updateStructure() - structureForm: {}", structureForm);
        Structure structure = findStructure(structureForm.getId());

        if (!structure.getContent().equals(structureForm.getContent()) || !structure.getTemplate().getId().equals(structureForm.getTemplate().getId())) {
            boolean templateUsed = structureRepository.isStructureUsed(structure);
            if (templateUsed) {
                throw new PlatformRuntimeException("You can not edit this structure because the structure is assigned to at least a one advanced web content.");
            }
        }

        structure.setName(structureForm.getName());
        structure.setContent(structureForm.getContent());
        Template template = templateService.findTemplate(structureForm.getTemplate().getId());
        validateStructureTemplate(structure, template);
        structure.setTemplate(template);
        return structureRepository.save(structure);
    }

    private void validateStructureTemplate(Structure structure, Template template) {
        String templateContents = template.getContent();
        String structureContents = structure.getContent();
        Set<String> templateKeys = ParserUtil.evaluateEL(templateContents);
        Set<String> attributeValues;
        try {
            attributeValues = ParserUtil.parseAttributes(structureContents);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new PlatformServiceRuntimeException(String.format("Structure Content parsing error with content: ", structureContents + "Error details : " + e.getMessage()));
        }
        StringBuilder templateKeysNotFoundError = new StringBuilder();
        if (templateKeys != null) {
            for (String templateKey : templateKeys) {
                if (StringUtils.isNotBlank(templateKey)) {
                    if (!attributeValues.contains(templateKey)) {
                        templateKeysNotFoundError.append("[" + templateKey + "]");
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(templateKeysNotFoundError.toString())) {
            throw new PlatformServiceRuntimeException(String.format("Structure Content is missing following Template Contents: " + templateKeysNotFoundError));
        }
    }

    @Override
    public void deleteStructure(Long structureId) {
        log.debug("deleteStructure() - structureId: {}", structureId);
        structureRepository.delete(structureId);
    }

    @Override
    public Structure findStructure(Long structureId) {
        log.debug("findStructureById() - structureId: {}", structureId);
        return structureRepository.findOne(structureId);
    }

    @Override
    public Page<Structure> findAllStructures(Pageable pageable) {
        log.debug("findAllStructures() - pageable: {}", pageable);
        return structureRepository.findAll(pageable);
    }

    @Override
    public List<Structure> findAllStructures() {
        return structureRepository.findAll();
    }
}