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

package com.abixen.platform.core.converter;


import com.abixen.platform.common.converter.AbstractConverter;
import com.abixen.platform.core.dto.ModuleDto;
import com.abixen.platform.core.model.impl.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class ModuleToModuleDtoConverter extends AbstractConverter<Module, ModuleDto> {

    private final ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter;
    private final PageToPageDtoConverter pageToPageDtoConverter;
    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;

    @Autowired
    public ModuleToModuleDtoConverter(ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter,
                                      PageToPageDtoConverter pageToPageDtoConverter,
                                      AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter) {
        this.moduleTypeToModuleTypeDtoConverter = moduleTypeToModuleTypeDtoConverter;
        this.pageToPageDtoConverter = pageToPageDtoConverter;
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
    }

    @Override
    public ModuleDto convert(Module module, Map<String, Object> parameters) {
        if (module == null) {
            return null;
        }

        ModuleDto moduleDto = new ModuleDto();

        moduleDto
                .setId(module.getId())
                .setTitle(module.getTitle())
                .setDescription(module.getDescription())
                .setModuleType(moduleTypeToModuleTypeDtoConverter.convert(module.getModuleType()))
                .setPage(pageToPageDtoConverter.convert(module.getPage()))
                .setRowIndex(module.getRowIndex())
                .setColumnIndex(module.getColumnIndex())
                .setOrderIndex(module.getOrderIndex());


        auditingModelToAuditingDtoConverter.convert(module, moduleDto);

        return moduleDto;
    }
}