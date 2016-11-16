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

package com.abixen.platform.core.service;

import com.abixen.platform.core.configuration.PlatformConfiguration;
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

    @Test
    public void htmlLayoutToJson() {
        String htmlString = "<div class=\"row\">\n" +
                "\t<div class=\"column col-md-12\"></div>\n" +
                "</div>\n" +
                "<div class=\"row\">\n" +
                "\t<div class=\"column col-md-4\"></div>\n" +
                "\t<div class=\"column col-md-4\"></div>\n" +
                "\t<div class=\"column col-md-4\"></div>\n" +
                "</div>";

        String result = layoutService.htmlLayoutToJson(htmlString);

        assertEquals(result, "{\"rows\":[{\"columns\":[{\"styleClass\":\"col-md-12\"}]},{\"columns\":[{\"styleClass\":\"col-md-4\"},{\"styleClass\":\"col-md-4\"},{\"styleClass\":\"col-md-4\"}]}]}");

    }
}
