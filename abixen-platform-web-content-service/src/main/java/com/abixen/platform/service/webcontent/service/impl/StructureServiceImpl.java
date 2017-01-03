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

import com.abixen.platform.core.exception.PlatformServiceRuntimeException;
import com.abixen.platform.service.webcontent.form.StructureForm;
import com.abixen.platform.service.webcontent.model.impl.Structure;
import com.abixen.platform.service.webcontent.repository.StructureRepository;
import com.abixen.platform.service.webcontent.service.StructureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Transactional
@Service
public class StructureServiceImpl implements StructureService {

    @Resource
    private StructureRepository structureRepository;

    @Override
    public Structure createStructure(StructureForm structureForm) {
        log.debug("createStructure() - structureForm: {}", structureForm);
        Structure structure = new Structure();
        structure.setContent(structureForm.getContent());
        return structureRepository.save(structure);
    }

    @Override
    public Structure updateStructure(StructureForm structureForm) {
        log.debug("updateStructure() - structureForm: {}", structureForm);
        Structure structure = findStructureById(structureForm.getId());
        structure.setContent(structureForm.getContent());
        return structureRepository.save(structure);
    }

    @Override
    public void removeStructure(Long structureId) {
        log.debug("removeStructure() - structureId: {}", structureId);
        structureRepository.delete(structureId);
    }

    @Override
    public Structure findStructureById(Long structureId) {
        log.debug("findStructureById() - structureId: {}", structureId);
        Structure structure = structureRepository.findOne(structureId);
        if (structure == null) {
            throw new PlatformServiceRuntimeException(String.format("Structure with id=%d not found", structureId));
        }
        return structure;
    }

    @Override
    public Page<Structure> findAllStructures(Pageable pageable) {
        log.debug("findAllStructures() - pageable: {}", pageable);
        return structureRepository.findAll(pageable);
    }
}