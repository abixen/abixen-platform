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
package com.abixen.platform.service.webcontent.service.impl;

import com.abixen.platform.service.webcontent.configuration.PlatformWebContentServiceConfiguration;
import com.abixen.platform.service.webcontent.form.TemplateForm;
import com.abixen.platform.service.webcontent.model.impl.Template;
import com.abixen.platform.service.webcontent.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformWebContentServiceConfiguration.class)
@Transactional
public class TemplateServiceImplTest {

    @Autowired
    TemplateService templateService;

    Template template;

    public String templateContent = "Hello ${someName1}! \n" +
            "This is something else: ${someName2}\n" +
            "And this is rich editor's text: ${someName3}\n" +
            "Born date: ${someName4}";

    @Before
    public void setUp() throws Exception {
        TemplateForm templateForm = new TemplateForm();
        templateForm.setName("Test Template");
        templateForm.setContent(templateContent);
        template = templateService.createTemplate(templateForm);
    }

    @After
    public void tearDown() throws Exception {
        templateService.removeTemplate(template.getId());
    }

    @Test
    public void getTemplateVariables() throws Exception {
        List<String> variables = templateService.getTemplateVariables(template.getId());
        assertTrue(variables.contains("someName1"));
        assertTrue(variables.contains("someName2"));
        assertTrue(variables.contains("someName3"));
        assertTrue(variables.contains("someName4"));
    }

}