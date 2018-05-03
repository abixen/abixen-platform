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

package com.abixen.platform.core.application.converter

import com.abixen.platform.core.application.dto.LayoutDto
import com.abixen.platform.core.application.dto.PageDto
import com.abixen.platform.core.domain.model.Layout
import com.abixen.platform.core.domain.model.Page
import spock.lang.Specification

class PageToPageDtoConverterTest extends Specification {

    private AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter
    private LayoutToLayoutDtoConverter layoutToLayoutDtoConverter
    private PageToPageDtoConverter pageToPageDtoConverter

    void setup() {
        auditingModelToAuditingDtoConverter = Mock()
        layoutToLayoutDtoConverter = Mock()
        pageToPageDtoConverter = new PageToPageDtoConverter(layoutToLayoutDtoConverter, auditingModelToAuditingDtoConverter)
    }


    void "should convert Page entity to PageDto"() {
        given:

        final Layout layout = [] as Layout
        final LayoutDto layoutDto = new LayoutDto()

        final Page page = Page.builder()
                .title()
                .layout(layout)
                .description("description")
                .icon("icon")
                .build()
        page.id = 1

        layoutToLayoutDtoConverter.convert(layout) >> layoutDto

        when:
        final PageDto pageDto = pageToPageDtoConverter.convert(page)

        then:
        pageDto != null
        pageDto.id == page.id
        pageDto.title == page.title
        pageDto.icon == page.icon
        pageDto.description == page.description
        pageDto.layout == layoutDto

        1 * layoutToLayoutDtoConverter.convert(layout) >> layoutDto
        1 * auditingModelToAuditingDtoConverter.convert(_, _)
        0 * _
    }

}