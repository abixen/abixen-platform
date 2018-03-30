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

package com.abixen.platform.core.application.converter;


import com.abixen.platform.common.application.converter.AbstractConverter;
import com.abixen.platform.core.application.dto.LayoutDto;
import com.abixen.platform.core.domain.model.Layout;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
                .setContentAsJson(convertHtmlToJson(layout.getContent()));

        auditingModelToAuditingDtoConverter.convert(layout, layoutDto);

        return layoutDto;
    }

    private String convertHtmlToJson(final String html) {
        final Document doc = Jsoup.parse(html);
        final Elements htmlRows = doc.getElementsByClass("row");
        final List<LayoutRow> layoutRows = new ArrayList<>();

        for (Element row : htmlRows) {
            final Document rowDoc = Jsoup.parse(row.toString());
            final Elements htmlColumns = rowDoc.getElementsByClass("column");
            final List<LayoutColumn> layoutColumns = new ArrayList<>();

            for (Element column : htmlColumns) {
                final String styleClass = column.attr("class");
                layoutColumns.add(new LayoutColumn(styleClass.substring(styleClass.indexOf(" ") + 1)));
            }

            layoutRows.add(new LayoutRow(layoutColumns));
        }

        return "{\"rows\":" + new Gson().toJson(layoutRows) + "}";
    }

}