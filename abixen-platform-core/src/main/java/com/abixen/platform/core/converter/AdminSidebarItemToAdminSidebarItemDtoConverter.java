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
import com.abixen.platform.core.dto.AdminSidebarItemDto;
import com.abixen.platform.core.model.impl.AdminSidebarItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class AdminSidebarItemToAdminSidebarItemDtoConverter extends AbstractConverter<AdminSidebarItem, AdminSidebarItemDto> {

    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;

    @Autowired
    public AdminSidebarItemToAdminSidebarItemDtoConverter(AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter) {
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
    }

    @Override
    public AdminSidebarItemDto convert(AdminSidebarItem adminSidebarItem, Map<String, Object> parameters) {
        AdminSidebarItemDto adminSidebarItemDto = new AdminSidebarItemDto();

        adminSidebarItemDto.setId(adminSidebarItem.getId());
        adminSidebarItemDto.setTitle(adminSidebarItem.getTitle());
        adminSidebarItemDto.setAngularJsState(adminSidebarItem.getAngularJsState());
        adminSidebarItemDto.setOrderIndex(adminSidebarItem.getOrderIndex());
        adminSidebarItemDto.setIconClass(adminSidebarItem.getIconClass());

        auditingModelToAuditingDtoConverter.convert(adminSidebarItem, adminSidebarItemDto);

        return adminSidebarItemDto;
    }
}