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


import com.abixen.platform.core.dto.ModuleTypeDto;
import com.abixen.platform.core.model.impl.ModuleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ModuleTypeToModuleTypeDtoConverter extends AbstractConverter<ModuleType, ModuleTypeDto> {

    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;

    @Autowired
    public ModuleTypeToModuleTypeDtoConverter(AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter) {
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
    }

    @Override
    public ModuleTypeDto convert(ModuleType moduleType, Map<String, Object> parameters) {
        ModuleTypeDto moduleTypeDto = new ModuleTypeDto();

        moduleTypeDto
                .setId(moduleType.getId())
                .setName(moduleType.getName())
                .setTitle(moduleType.getTitle())
                .setDescription(moduleType.getDescription())
                .setInitUrl(moduleType.getInitUrl())
                .setServiceId(moduleType.getServiceId());

        auditingModelToAuditingDtoConverter.convert(moduleType, moduleTypeDto);

        return moduleTypeDto;
    }
}