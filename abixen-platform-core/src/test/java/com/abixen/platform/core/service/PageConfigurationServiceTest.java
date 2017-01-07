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
import com.abixen.platform.core.dto.PageModelDto;
import com.abixen.platform.core.form.PageForm;
import com.abixen.platform.core.model.impl.Layout;
import com.abixen.platform.core.model.impl.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformConfiguration.class)
@Slf4j
public class PageConfigurationServiceTest {

    @Autowired
    private PageConfigurationService pageConfigurationService;

    @Autowired
    private PageService pageService;

    private PageForm samplePageForm;
    private Page samplePage;
    private PageModelDto dto;

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

    }

    @Test
    public void getPageModel() {
        log.debug("getPageModel() id:" + samplePage.getId());

        dto = pageConfigurationService.getPageConfiguration(samplePage.getId());

        assertNotNull(dto);

        assertNotNull(dto.getPage().getId());

        assertTrue(dto.getPage().getId() > 0L);

        assertEquals(samplePageForm.getTitle(), dto.getPage().getTitle());

        assertEquals(samplePageForm.getDescription(), dto.getPage().getDescription());

        assertEquals(samplePageForm.getLayout().getId(), dto.getPage().getLayout().getId());

    }

}