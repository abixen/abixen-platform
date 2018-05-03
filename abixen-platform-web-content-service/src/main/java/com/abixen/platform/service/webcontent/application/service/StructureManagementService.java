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

import com.abixen.platform.service.webcontent.application.converter.StructureToStructureDtoConverter;
import com.abixen.platform.service.webcontent.application.dto.StructureDto;
import com.abixen.platform.service.webcontent.application.form.StructureForm;
import com.abixen.platform.service.webcontent.domain.model.Structure;
import com.abixen.platform.service.webcontent.domain.model.Template;
import com.abixen.platform.service.webcontent.domain.service.StructureService;
import com.abixen.platform.service.webcontent.domain.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class StructureManagementService {

    private final StructureService structureService;
    private final TemplateService templateService;
    private final StructureToStructureDtoConverter structureToStructureDtoConverter;

    @Autowired
    public StructureManagementService(StructureService structureService,
                                      TemplateService templateService,
                                      StructureToStructureDtoConverter structureToStructureDtoConverter) {
        this.structureService = structureService;
        this.templateService = templateService;
        this.structureToStructureDtoConverter = structureToStructureDtoConverter;
    }

    public StructureDto createStructure(StructureForm structureForm) {
        final Template template = templateService.findTemplate(structureForm.getTemplate().getId());
        final Structure structure = Structure.builder()
                .name(structureForm.getName())
                .content(structureForm.getContent())
                .template(template)
                .build();

        final Structure createdStructure = structureService.create(structure);

        return structureToStructureDtoConverter.convert(createdStructure);
    }

    public StructureDto updateStructure(StructureForm structureForm) {
        final Template template = templateService.findTemplate(structureForm.getTemplate().getId());
        final Structure structure = structureService.find(structureForm.getId());
        structure.changeName(structureForm.getName());
        structure.changeContent(structureForm.getContent());
        structure.changeTemplate(template);

        final Structure updateStructure = structureService.update(structure);
        return structureToStructureDtoConverter.convert(updateStructure);
    }

    public void deleteStructure(final Long structureId) {
        structureService.delete(structureId);
    }

    public StructureDto findStructure(final Long structureId) {
        Structure structure = structureService.find(structureId);

        return structureToStructureDtoConverter.convert(structure);
    }

    public Page<StructureDto> findAllStructures(final Pageable pageable) {
        Page<Structure> structures = structureService.findAll(pageable);

        return structureToStructureDtoConverter.convertToPage(structures);
    }

    public List<StructureDto> findAllStructures() {
        List<Structure> structures = structureService.findAll();

        return structureToStructureDtoConverter.convertToList(structures);
    }
}