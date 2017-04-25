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
import com.abixen.platform.core.dto.LayoutDto;
import com.abixen.platform.core.model.impl.Layout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LayoutToLayoutDtoConverter extends AbstractConverter<Layout, LayoutDto> {

    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;

    @Autowired
    public LayoutToLayoutDtoConverter(AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter) {
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
    }

    @Override
    public LayoutDto convert(Layout layout, Map<String, Object> parameters) {
        LayoutDto layoutDto = new LayoutDto();

        layoutDto
                .setId(layout.getId())
                .setTitle(layout.getTitle())
                .setContent(layout.getContent())
                .setIconFileName(layout.getIconFileName())
                .setContentAsJson(layout.getContentAsJson());

        auditingModelToAuditingDtoConverter.convert(layout, layoutDto);

        return layoutDto;
    }
}