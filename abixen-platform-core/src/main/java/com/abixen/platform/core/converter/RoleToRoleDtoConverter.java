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
import com.abixen.platform.core.dto.RoleDto;
import com.abixen.platform.core.model.impl.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RoleToRoleDtoConverter extends AbstractConverter<Role, RoleDto> {

    private final PermissionToPermissionDtoConverter permissionToPermissionDtoConverter;
    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;

    @Autowired
    public RoleToRoleDtoConverter(PermissionToPermissionDtoConverter permissionToPermissionDtoConverter,
                                  AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter) {
        this.permissionToPermissionDtoConverter = permissionToPermissionDtoConverter;
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
    }

    @Override
    public RoleDto convert(Role role, Map<String, Object> parameters) {
        RoleDto roleDto = new RoleDto();

        roleDto
                .setId(role.getId())
                .setRoleType(role.getRoleType())
                .setName(role.getName())
                .setPermissions(permissionToPermissionDtoConverter.convertToSet(role.getPermissions()));

        auditingModelToAuditingDtoConverter.convert(role, roleDto);
        return roleDto;
    }
}