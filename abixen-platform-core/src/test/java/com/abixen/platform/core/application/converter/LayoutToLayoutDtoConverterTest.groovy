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
import com.abixen.platform.core.domain.model.Layout
import spock.lang.Specification
import spock.lang.Unroll

class LayoutToLayoutDtoConverterTest extends Specification {

    private AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter
    private LayoutToLayoutDtoConverter layoutToLayoutDtoConverter

    private final static String WRONG_HTML_CONTENT = "wrongHtmlContent"
    private final static String JSON_FOR_WRONG_CONTENT = "{\"rows\":[]}"
    private final static String CORRECT_HTML_CONTENT = "<div class=\"row\">\n" +
            "\t<div class=\"column col-md-12\"></div>\n" +
            "</div>\n" +
            "<div class=\"row\">\n" +
            "\t<div class=\"column col-md-4\"></div>\n" +
            "\t<div class=\"column col-md-4\"></div>\n" +
            "\t<div class=\"column col-md-4\"></div>\n</div>"
    private final static String JSON_FOR_CORRECT_CONTENT = "{\"rows\":[{\"columns\"" +
            ":[{\"styleClass\":\"col-md-12\"}]},{\"columns\":[{\"styleClass\":\"col-md-4\"}," +
            "{\"styleClass\":\"col-md-4\"},{\"styleClass\":\"col-md-4\"}]}]}"

    void setup() {
        auditingModelToAuditingDtoConverter = Mock()
        layoutToLayoutDtoConverter = new LayoutToLayoutDtoConverter(auditingModelToAuditingDtoConverter)
    }


    @Unroll
    void "should convert Layout entity to LayoutDto with #contentType html content"() {
        given:
        final Layout layout = Layout.builder()
                .title("title")
                .content(htmlContent)
                .iconFileName("iconFileName")
                .build()
        layout.id = 1L

        when:
        final LayoutDto layoutDto = layoutToLayoutDtoConverter.convert(layout)

        then:
        layoutDto != null
        layoutDto.id == layoutDto.id
        layoutDto.title == layout.title
        layoutDto.content == layout.content
        layoutDto.iconFileName == layout.iconFileName
        layoutDto.contentAsJson == jsonContent

        1 * auditingModelToAuditingDtoConverter.convert(_, _)
        0 * _

        where:
        contentType | htmlContent          || jsonContent
        "wrong"     | WRONG_HTML_CONTENT   || JSON_FOR_WRONG_CONTENT
        "correct"   | CORRECT_HTML_CONTENT || JSON_FOR_CORRECT_CONTENT
    }

}