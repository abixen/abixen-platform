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

package com.abixen.platform.service.businessintelligence.multivisualisation.converter;


import com.abixen.platform.common.dto.AuditingDto;
import com.abixen.platform.common.dto.UserDto;
import com.abixen.platform.common.model.audit.AuditingModel;
import com.abixen.platform.service.businessintelligence.integration.UserIntegrationClient;
import com.abixen.platform.service.businessintelligence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AuditingModelToAuditingDtoConverter {

    private final UserIntegrationClient userIntegrationClient;

    @Autowired
    public AuditingModelToAuditingDtoConverter(UserIntegrationClient userIntegrationClient) {
        this.userIntegrationClient = userIntegrationClient;
    }

    public AuditingDto convert(AuditingModel auditingModel, AuditingDto auditingDto) {
        UserDto createdByDto = null;
        UserDto lastModifiedByDto = null;

        if (auditingModel.getCreatedById() != null) {
            User createdBy = userIntegrationClient.getUserById(auditingModel.getCreatedById());
            createdByDto = new UserDto();
            createdByDto.setId(createdBy.getId());
            createdByDto.setUsername(createdBy.getUsername());

        }
        if (auditingModel.getLastModifiedById() != null) {
            User lastModifiedBy = userIntegrationClient.getUserById(auditingModel.getLastModifiedById());
            lastModifiedByDto = new UserDto();
            lastModifiedByDto.setId(lastModifiedBy.getId());
            lastModifiedByDto.setUsername(lastModifiedBy.getUsername());
        }


        auditingDto
                .setCreatedBy(createdByDto)
                .setCreatedDate(auditingModel.getCreatedDate())
                .setLastModifiedBy(lastModifiedByDto)
                .setLastModifiedDate(auditingModel.getLastModifiedDate());

        return auditingDto;
    }
}