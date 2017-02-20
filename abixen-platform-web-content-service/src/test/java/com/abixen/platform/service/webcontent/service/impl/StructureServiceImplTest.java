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

import com.abixen.platform.core.exception.PlatformServiceRuntimeException;
import com.abixen.platform.service.webcontent.configuration.PlatformWebContentServiceConfiguration;
import com.abixen.platform.service.webcontent.form.StructureForm;
import com.abixen.platform.service.webcontent.form.TemplateForm;
import com.abixen.platform.service.webcontent.model.impl.Structure;
import com.abixen.platform.service.webcontent.model.impl.Template;
import com.abixen.platform.service.webcontent.service.StructureService;
import com.abixen.platform.service.webcontent.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformWebContentServiceConfiguration.class)
@Transactional
public class StructureServiceImplTest {

    @Autowired
    StructureService structureService;

    @Autowired
    TemplateService templateService;

    public static final String structureContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<fields>\n" +
            "   <field name=\"someName1\" type=\"text\"/>\n" +
            "   <field name=\"someName2\" type=\"textArea\"/>\n" +
            "   <field name=\"someName3\" type=\"richTextArea\"/>\n" +
            "   <field name=\"someName4\" type=\"date\"/>\n" +
            "</fields>";

    public static final String faiureStructureContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<fields>\n" +
            "   <field name=\"someName2\" type=\"text\"/>\n" +
            "   <field name=\"someName2\" type=\"textArea\"/>\n" +
            "   <field name=\"someName3\" type=\"richTextArea\"/>\n" +
            "   <field name=\"someName4\" type=\"date\"/>\n" +
            "</fields>";

    public String templateContent ="Hello ${someName1}! \n" +
            "This is something else: ${someName2}\n" +
            "And this is rich editor's text: ${someName3}\n" +
            "Born date: ${someName4}";


    @Test
    public void createStructure()  {
        StructureForm structureForm = new StructureForm();
        structureForm.setName("Test Structure");
        structureForm.setContent(structureContent);
        TemplateForm templateForm = new TemplateForm();
        templateForm.setName("Test Template");
        templateForm.setContent(templateContent);
        Template template =templateService.createTemplate(templateForm);
        structureForm.setTemplate(template);
        Structure structure =structureService.createStructure(structureForm);
        assertNotNull(structure.getId());
    }

    @Test
    public void updateStructure() {
        Structure structure =structureService.findStructureById(new Long(1));
        StructureForm structureForm = new StructureForm(structure);
        structureForm.setName("Test Structure1");
        Structure updatedStructure =structureService.updateStructure(structureForm);
        assertEquals(structure.getName(),"Test Structure1");
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void createStructureTemplateValidateFailure()  {
        expectedEx.expect(PlatformServiceRuntimeException.class);
        expectedEx.expectMessage("Structure Content is missing following Template Contents: [someName1]");
        StructureForm structureForm = new StructureForm();
        structureForm.setName("Test Structure");
        structureForm.setContent(faiureStructureContent);
        TemplateForm templateForm = new TemplateForm();
        templateForm.setName("Test Template");
        templateForm.setContent(templateContent);
        Template template =templateService.createTemplate(templateForm);
        structureForm.setTemplate(template);
        Structure structure =structureService.createStructure(structureForm);
    }

}