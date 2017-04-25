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
import com.abixen.platform.core.dto.ResourceDto;
import com.abixen.platform.core.model.impl.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class ResourceToResourceDtoConverter extends AbstractConverter<Resource, ResourceDto> {

    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;

    @Autowired
    public ResourceToResourceDtoConverter(AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter) {
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
    }

    @Override
    public ResourceDto convert(Resource resource, Map<String, Object> parameters) {
        ResourceDto resourceDto = new ResourceDto();

        resourceDto.setId(resource.getId());
        resourceDto.setRelativeUrl(resource.getRelativeUrl());
        resourceDto.setResourcePageLocation(resource.getResourcePageLocation());
        resourceDto.setResourcePage(resource.getResourcePage());
        resourceDto.setResourceType(resource.getResourceType());

        auditingModelToAuditingDtoConverter.convert(resource, resourceDto);

        return resourceDto;
    }
}