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

package com.abixen.platform.common.application.converter;


import com.abixen.platform.common.application.dto.SimpleAuditingDto;
import com.abixen.platform.common.application.dto.SimpleUserDto;
import com.abixen.platform.common.infrastructure.integration.UserIntegrationClient;
import com.abixen.platform.common.domain.model.audit.SimpleAuditingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AuditingModelToSimpleAuditingDtoConverter {

    private final UserIntegrationClient userIntegrationClient;

    @Autowired
    public AuditingModelToSimpleAuditingDtoConverter(UserIntegrationClient userIntegrationClient) {
        this.userIntegrationClient = userIntegrationClient;
    }

    public SimpleAuditingDto convert(SimpleAuditingModel auditingModel, SimpleAuditingDto auditingDto) {
        SimpleUserDto createdByDto = null;
        SimpleUserDto lastModifiedByDto = null;

        if (auditingModel.getCreatedById() != null) {
            createdByDto = userIntegrationClient.getUserById(auditingModel.getCreatedById());

        }
        if (auditingModel.getLastModifiedById() != null) {
            lastModifiedByDto = userIntegrationClient.getUserById(auditingModel.getLastModifiedById());
        }

        auditingDto
                .setCreatedBy(createdByDto)
                .setCreatedDate(auditingModel.getCreatedDate())
                .setLastModifiedBy(lastModifiedByDto)
                .setLastModifiedDate(auditingModel.getLastModifiedDate());

        return auditingDto;
    }

}