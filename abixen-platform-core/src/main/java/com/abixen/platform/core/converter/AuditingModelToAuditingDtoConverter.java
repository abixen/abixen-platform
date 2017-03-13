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


import com.abixen.platform.core.dto.AuditingDto;
import com.abixen.platform.core.dto.UserDto;
import com.abixen.platform.core.model.impl.AuditingModel;
import org.springframework.stereotype.Component;


@Component
public class AuditingModelToAuditingDtoConverter {

    public AuditingDto convert(AuditingModel auditingModel, AuditingDto auditingDto) {

        UserDto createdBy = null;
        if (auditingModel.getCreatedBy() != null) {
            createdBy = new UserDto();
            createdBy.setId(auditingModel.getCreatedBy().getId());
            createdBy.setUsername(auditingModel.getCreatedBy().getUsername());
        }

        UserDto lastModifiedBy = null;
        if (auditingModel.getLastModifiedBy() != null) {
            lastModifiedBy = new UserDto();
            lastModifiedBy.setId(auditingModel.getLastModifiedBy().getId());
            lastModifiedBy.setUsername(auditingModel.getLastModifiedBy().getUsername());
        }

        auditingDto
                .setCreatedBy(createdBy)
                .setCreatedDate(auditingModel.getCreatedDate())
                .setLastModifiedBy(lastModifiedBy)
                .setLastModifiedDate(auditingModel.getLastModifiedDate());

        return auditingDto;
    }
}