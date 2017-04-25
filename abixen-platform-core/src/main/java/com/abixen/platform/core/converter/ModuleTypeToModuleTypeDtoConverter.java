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
import com.abixen.platform.core.dto.ModuleTypeDto;
import com.abixen.platform.core.model.impl.ModuleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ModuleTypeToModuleTypeDtoConverter extends AbstractConverter<ModuleType, ModuleTypeDto> {

    private final ResourceToResourceDtoConverter resourceToResourceDtoConverter;
    private final AdminSidebarItemToAdminSidebarItemDtoConverter adminSidebarItemToAdminSidebarItemDtoConverter;
    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;

    @Autowired
    public ModuleTypeToModuleTypeDtoConverter(ResourceToResourceDtoConverter resourceToResourceDtoConverter,
                                              AdminSidebarItemToAdminSidebarItemDtoConverter adminSidebarItemToAdminSidebarItemDtoConverter,
                                              AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter) {
        this.resourceToResourceDtoConverter = resourceToResourceDtoConverter;
        this.adminSidebarItemToAdminSidebarItemDtoConverter = adminSidebarItemToAdminSidebarItemDtoConverter;
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
    }

    @Override
    public ModuleTypeDto convert(ModuleType moduleType, Map<String, Object> parameters) {
        ModuleTypeDto moduleTypeDto = new ModuleTypeDto();

        moduleTypeDto.setId(moduleType.getId());
        moduleTypeDto.setName(moduleType.getName());
        moduleTypeDto.setTitle(moduleType.getTitle());
        moduleTypeDto.setAngularJsNameAdmin(moduleType.getAngularJsNameAdmin());
        moduleTypeDto.setAngularJsNameApplication(moduleType.getAngularJsNameApplication());
        moduleTypeDto.setDescription(moduleType.getDescription());
        moduleTypeDto.setInitUrl(moduleType.getInitUrl());
        moduleTypeDto.setServiceId(moduleType.getServiceId());
        moduleTypeDto.setResources(resourceToResourceDtoConverter.convertToList(moduleType.getResources()));
        moduleTypeDto.setAdminSidebarItems(adminSidebarItemToAdminSidebarItemDtoConverter.convertToList(moduleType.getAdminSidebarItems()));

        auditingModelToAuditingDtoConverter.convert(moduleType, moduleTypeDto);

        return moduleTypeDto;
    }
}