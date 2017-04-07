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
import com.abixen.platform.core.dto.PageDto;
import com.abixen.platform.core.model.impl.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class PageToPageDtoConverter extends AbstractConverter<Page, PageDto> {

    private final LayoutToLayoutDtoConverter layoutToLayoutDtoConverter;
    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;

    @Autowired
    public PageToPageDtoConverter(LayoutToLayoutDtoConverter layoutToLayoutDtoConverter,
                                  AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter) {
        this.layoutToLayoutDtoConverter = layoutToLayoutDtoConverter;
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
    }

    @Override
    public PageDto convert(Page page, Map<String, Object> parameters) {
        PageDto pageDto = new PageDto();

        pageDto
                .setId(page.getId())
                .setTitle(page.getTitle())
                .setIcon(page.getIcon())
                .setDescription(page.getDescription())
                .setLayout(layoutToLayoutDtoConverter.convert(page.getLayout()));


        auditingModelToAuditingDtoConverter.convert(page, pageDto);

        return pageDto;
    }
}