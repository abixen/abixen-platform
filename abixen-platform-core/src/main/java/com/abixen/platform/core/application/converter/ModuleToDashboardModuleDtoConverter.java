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

package com.abixen.platform.core.application.converter;


import com.abixen.platform.common.application.converter.AbstractConverter;
import com.abixen.platform.core.application.dto.DashboardModuleDto;
import com.abixen.platform.core.domain.model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class ModuleToDashboardModuleDtoConverter extends AbstractConverter<Module, DashboardModuleDto> {

    private final ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter;

    @Autowired
    public ModuleToDashboardModuleDtoConverter(ModuleTypeToModuleTypeDtoConverter moduleTypeToModuleTypeDtoConverter) {
        this.moduleTypeToModuleTypeDtoConverter = moduleTypeToModuleTypeDtoConverter;
    }


    @Override
    public DashboardModuleDto convert(Module module, Map<String, Object> parameters) {
        DashboardModuleDto dashboardModuleDto = new DashboardModuleDto();

        //FIXME - check why do we need both type and moduleType
        dashboardModuleDto
                .setId(module.getId())
                .setTitle(module.getTitle())
                .setDescription(module.getDescription())
                .setType(module.getModuleType().getName())
                .setModuleType(moduleTypeToModuleTypeDtoConverter.convert(module.getModuleType()))
                .setRowIndex(module.getRowIndex())
                .setColumnIndex(module.getColumnIndex())
                .setOrderIndex(module.getOrderIndex());

        return dashboardModuleDto;
    }

}