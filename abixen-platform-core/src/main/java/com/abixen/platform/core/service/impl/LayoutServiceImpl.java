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

package com.abixen.platform.core.service.impl;

import com.abixen.platform.core.model.impl.Layout;
import com.abixen.platform.core.repository.LayoutRepository;
import com.abixen.platform.core.service.LayoutService;
import com.abixen.platform.core.util.LayoutColumnUtil;
import com.abixen.platform.core.util.LayoutRowUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

//import com.google.gson.Gson;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;


@Service
public class LayoutServiceImpl implements LayoutService {

    private static Logger log = Logger.getLogger(LayoutServiceImpl.class.getName());

    @Resource
    private LayoutRepository layoutRepository;

    @Override
    public Layout createLayout(Layout layout) {
        log.debug("createLayout() - layout: " + layout);
        return layoutRepository.save((Layout) layout);
    }

    @Override
    public Layout updateLayout(Layout layout) {
        log.debug("updateLayout() - layout: " + layout);
        return layoutRepository.save((Layout) layout);
    }

    @Override
    public void deleteLayout(Long id) {
        log.debug("deleteLayout() - id: " + id);
        layoutRepository.delete(id);
    }

    @Override
    public String htmlLayoutToJson(String htmlString) {

        log.debug("htmlLayoutToJson() - htmlString: " + htmlString);

        Document doc = Jsoup.parse(htmlString);
        Elements htmlRows = doc.getElementsByClass("row");
        List<LayoutRowUtil> rowUtilList = new ArrayList<>();

        for (Element row : htmlRows) {

            Document rowDoc = Jsoup.parse(row.toString());
            Elements htmlColumns = rowDoc.getElementsByClass("column");
            List<LayoutColumnUtil> columnUtilList = new ArrayList<>();

            for (Element column : htmlColumns) {
                String styleClass = column.attr("class");
                columnUtilList.add(new LayoutColumnUtil(styleClass.substring(styleClass.indexOf(" ") + 1)));
            }

            rowUtilList.add(new LayoutRowUtil(columnUtilList));
        }

        return "{\"rows\":" + new Gson().toJson(rowUtilList) + "}";
    }

    @Override
    public Page<Layout> findAllLayouts(Pageable pageable) {
        log.debug("findAllLayouts() - pageable: " + pageable);
        return layoutRepository.findAll(pageable);
    }

    @Override
    public Layout findLayout(Long id) {
        log.debug("findLayout() - id: " + id);
        return layoutRepository.findOne(id);
    }
}
