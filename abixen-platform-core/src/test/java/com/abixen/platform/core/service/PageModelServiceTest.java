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
import com.abixen.platform.core.dto.DashboardModuleDto;
import com.abixen.platform.core.dto.PageModelDto;
import com.abixen.platform.core.form.PageForm;
import com.abixen.platform.core.model.impl.Layout;
import com.abixen.platform.core.model.impl.ModuleType;
import com.abixen.platform.core.model.impl.Page;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformConfiguration.class)
public class PageModelServiceTest {

    static Logger log = Logger.getLogger(PageModelServiceTest.class);

    @Autowired
    private PageModelService pageModelService;

    @Autowired
    private PageService pageService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ModuleTypeService moduleTypeService;

    private PageForm samplePageForm;
    private Page samplePage;
    private PageModelDto dto;
    DashboardModuleDto dashboardModuleDto;

    @Before
    public void generatePageForm() {
        samplePageForm = new PageForm();
        samplePageForm.setTitle("Sample page");
        samplePageForm.setDescription("Sample page to validate PageModel");
        Layout layoutWeb = new Layout();
        layoutWeb.setId(7L);
        samplePageForm.setLayout(layoutWeb);

        samplePage = pageService.buildPage(samplePageForm);
        samplePage = pageService.createPage(samplePage);

        dto = new PageModelDto();
        dto.setPage(samplePage);

        ModuleType moduleType = moduleTypeService.findModuleType(1L);

        dashboardModuleDto = new DashboardModuleDto();
        dashboardModuleDto.setDescription("Module description original");
        dashboardModuleDto.setTitle("Title original");
        dashboardModuleDto.setColumnIndex(0);
        dashboardModuleDto.setFrontendId(1L);
        dashboardModuleDto.setOrderIndex(0);
        dashboardModuleDto.setRowIndex(0);
        dashboardModuleDto.setType("DashboardModuleDto Type");
        dashboardModuleDto.setModuleType(moduleType);
    }

    @Test
    public void getPageModel() {
        log.debug("getPageModel() id:" + samplePage.getId());

        dto = pageModelService.getPageModel(samplePage.getId());

        assertNotNull(dto);

        assertNotNull(dto.getPage().getId());

        assertTrue(dto.getPage().getId() > 0L);

        assertEquals(samplePageForm.getTitle(), dto.getPage().getTitle());

        assertEquals(samplePageForm.getDescription(), dto.getPage().getDescription());

        assertEquals(samplePageForm.getLayout().getId(), dto.getPage().getLayout().getId());

    }

    @Test()
    public void updatePageModel() {
        log.debug("updatePageModel()");
        PageModelDto dtoUpdated;
        PageModelDto dtoRes;

        dtoUpdated = pageModelService.getPageModel(samplePage.getId());

        Page updatedPage = (Page) dtoUpdated.getPage();

        updatedPage.setDescription("This is an updated description");
        updatedPage.setTitle("Updated Title");
        updatedPage.setName("Updated Name");

        List<DashboardModuleDto> dashboardModuleDtos = new ArrayList<>();
        dashboardModuleDtos.add(dashboardModuleDto);

        dtoRes = new PageModelDto(updatedPage, dashboardModuleDtos);

        dtoUpdated = pageModelService.updatePageModel(dtoRes);

        assertNotNull(dtoUpdated);

        assertEquals(dto.getPage().getId(), dtoUpdated.getPage().getId());

        assertNotEquals(dto.getPage().getDescription(), dtoUpdated.getPage().getDescription());

        assertNotEquals(dto.getPage().getTitle(), dtoUpdated.getPage().getTitle());

        assertNotEquals(dto.getPage().getName(), dtoUpdated.getPage().getName());
    }
}