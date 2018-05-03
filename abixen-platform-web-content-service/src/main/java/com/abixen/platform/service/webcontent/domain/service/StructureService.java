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

package com.abixen.platform.service.webcontent.domain.service;

import com.abixen.platform.common.infrastructure.exception.PlatformRuntimeException;
import com.abixen.platform.common.infrastructure.exception.PlatformServiceRuntimeException;
import com.abixen.platform.service.webcontent.domain.model.Structure;
import com.abixen.platform.service.webcontent.domain.repository.StructureRepository;
import com.abixen.platform.service.webcontent.domain.util.ParserUtil;
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
public class StructureService {

    private final StructureRepository structureRepository;

    @Autowired
    public StructureService(StructureRepository structureRepository) {
        this.structureRepository = structureRepository;
    }


    public Structure find(final Long id) {
        log.debug("find() - id: {}", id);

        return structureRepository.findOne(id);
    }

    public Page<Structure> findAll(final Pageable pageable) {
        log.debug("findAll() - pageable: {}", pageable);

        return structureRepository.findAll(pageable);
    }

    public List<Structure> findAll() {
        log.debug("findAll()");

        return structureRepository.findAll();
    }

    public Structure create(final Structure structure) {
        log.debug("create() - structure: {}", structure);
        validateStructure(structure);

        return structureRepository.save(structure);
    }

    public Structure update(final Structure structure) {
        log.debug("update() - structure: {}", structure);

        if (!structure.getContent().equals(structure.getContent()) || !structure.getTemplate().getId().equals(structure.getTemplate().getId())) {
            boolean templateUsed = structureRepository.isStructureUsed(structure);
            if (templateUsed) {
                throw new PlatformRuntimeException("You can not edit this structure because the structure is assigned to at least a one advanced web content.");
            }
        }

        validateStructure(structure);

        return structureRepository.save(structure);
    }

    public void delete(final Long id) {
        log.debug("delete() - id: {}", id);
        structureRepository.delete(id);
    }

    private void validateStructure(final Structure structure) {
        final String templateContents = structure.getTemplate().getContent();
        final String structureContents = structure.getContent();
        final Set<String> templateKeys = ParserUtil.evaluateEL(templateContents);
        final Set<String> attributeValues;
        try {
            attributeValues = ParserUtil.parseAttributes(structureContents);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new PlatformServiceRuntimeException(String.format("Structure Content parsing error with content: ", structureContents + "Error details : " + e.getMessage()));
        }
        final StringBuilder templateKeysNotFoundError = new StringBuilder();
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
}