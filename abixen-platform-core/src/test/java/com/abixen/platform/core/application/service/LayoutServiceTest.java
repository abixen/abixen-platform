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

package com.abixen.platform.core.application.service;

import com.abixen.platform.core.infrastructure.configuration.PlatformConfiguration;
import com.abixen.platform.core.domain.model.impl.Layout;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformConfiguration.class)
public class LayoutServiceTest {

    @Autowired
    LayoutService layoutService;

    private Layout layout;

    private  final Layout sampleLayout = new Layout();;

    private static final String HTML = "<div class=\"row\">\n" +
            "\t<div class=\"column col-md-12\"></div>\n" +
            "</div>\n" +
            "<div class=\"row\">\n" +
            "\t<div class=\"column col-md-4\"></div>\n" +
            "\t<div class=\"column col-md-4\"></div>\n" +
            "\t<div class=\"column col-md-4\"></div>\n" +
            "</div>";

    private static final String UPDATE_HTML= "<div class=\"row\">\n" +
            "<div class=\"column col-md-12\"></div>\n" +
            "</div>\n" +
            "<div class=\"row\">\n" +
            " <div class=\"column col-md-4\"></div>\n" +
            "</div>";


    @Test
    public void htmlLayoutToJson() {

        String result = layoutService.htmlLayoutToJson(HTML);

        assertEquals(result, "{\"rows\":[{\"columns\":[{\"styleClass\":\"col-md-12\"}]},{\"columns\":[{\"styleClass\":\"col-md-4\"},{\"styleClass\":\"col-md-4\"},{\"styleClass\":\"col-md-4\"}]}]}");

    }

    @Test
    @Before
    public void createLayout() {

        sampleLayout.setTitle("Test Layout");
        sampleLayout.setIconFileName("Test Icon File Name");
        sampleLayout.setContent(HTML);

        layout = layoutService.createLayout(sampleLayout);

        assertEquals(layout.getContent(),HTML);

        assertEquals(layout.getIconFileName(), sampleLayout.getIconFileName());

        assertEquals(layout.getTitle(), sampleLayout.getTitle());
    }

    @Test
    public void updateLayout() {

        layout.setContent(UPDATE_HTML);

        Layout updatedLayout = layoutService.updateLayout(layout);

        assertEquals(updatedLayout.getContent(),UPDATE_HTML);

        assertEquals(layout.getId(), updatedLayout.getId());

        assertEquals(layout.getTitle(), updatedLayout.getTitle());

        assertEquals(layout.getIconFileName(), updatedLayout.getIconFileName());
    }

}
