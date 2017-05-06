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

import com.abixen.platform.service.webcontent.converter.StructureToStructureDtoConverter;
import com.abixen.platform.service.webcontent.dto.StructureDto;
import com.abixen.platform.service.webcontent.facade.StructureFacade;
import com.abixen.platform.service.webcontent.form.StructureForm;
import com.abixen.platform.service.webcontent.model.impl.Structure;
import com.abixen.platform.service.webcontent.service.StructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class StructureFacadeImpl implements StructureFacade {

    private final StructureService structureService;
    private final StructureToStructureDtoConverter structureToStructureDtoConverter;

    @Autowired
    public StructureFacadeImpl(StructureService structureService,
                               StructureToStructureDtoConverter structureToStructureDtoConverter) {
        this.structureService = structureService;
        this.structureToStructureDtoConverter = structureToStructureDtoConverter;
    }

    @Override
    public StructureDto createStructure(StructureForm structureForm) {
        Structure savedStructure = structureService.createStructure(structureForm);
        StructureDto savedStructureDto = structureToStructureDtoConverter.convert(savedStructure);
        return savedStructureDto;
    }

    @Override
    public StructureDto updateStructure(StructureForm structureForm) {
        Structure savedStructure = structureService.updateStructure(structureForm);
        StructureDto savedStructureDto = structureToStructureDtoConverter.convert(savedStructure);
        return savedStructureDto;
    }

    @Override
    public void deleteStructure(Long structureId) {
        structureService.deleteStructure(structureId);
    }

    @Override
    public StructureDto findStructure(Long structureId) {
        Structure structure = structureService.findStructure(structureId);
        StructureDto structureDto = structureToStructureDtoConverter.convert(structure);
        return structureDto;
    }

    @Override
    public Page<StructureDto> findAllStructures(Pageable pageable) {
        Page<Structure> structures = structureService.findAllStructures(pageable);
        Page<StructureDto> structureDtos = structureToStructureDtoConverter.convertToPage(structures);
        return structureDtos;
    }

    @Override
    public List<StructureDto> findAllStructures() {
        List<Structure> structures = structureService.findAllStructures();
        List<StructureDto> structureDtos = structureToStructureDtoConverter.convertToList(structures);
        return structureDtos;
    }
}