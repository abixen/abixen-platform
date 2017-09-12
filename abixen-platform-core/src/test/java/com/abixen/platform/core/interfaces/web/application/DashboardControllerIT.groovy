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

package com.abixen.platform.core.interfaces.web.application

import com.abixen.platform.common.application.dto.FormValidationResultDto
import com.abixen.platform.core.AbstractPlatformIT
import com.abixen.platform.core.application.dto.DashboardDto
import com.abixen.platform.core.application.dto.DashboardModuleDto
import com.abixen.platform.core.application.dto.LayoutDto
import com.abixen.platform.core.application.dto.PageDto
import com.abixen.platform.core.application.form.DashboardForm
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class DashboardControllerIT extends AbstractPlatformIT {

    @LocalServerPort
    private int port

    @Autowired
    private TestRestTemplate template


    void setup() {
    }

    void "should find DashboardDto"() {

        given:
        final Long pageId = 1L

        when:
        final ResponseEntity<DashboardDto> responseEntity = template
                .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
                .getForEntity("http://localhost:${port}/api/page-configurations/${pageId}", DashboardDto.class)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        final PageDto pageDto = responseEntity.getBody().getPage()
        pageDto != null
        pageDto.getId() == 1L
        pageDto.getDescription() == "This is a sample test page"
        pageDto.getTitle() == "Sample test"
        pageDto.getIcon() == "fa fa-lock"

        final LayoutDto layoutDto = pageDto.getLayout()
        layoutDto != null
        layoutDto.getId() == 1L
        layoutDto.getTitle() == "1 (100)"
        layoutDto.getIconFileName() == "layout-icon-1.png"
        layoutDto.getContent() == "<div class=\"row\"><div class=\"column col-md-12\"></div></div>"
        layoutDto.getContentAsJson() == "{\"rows\":[{\"columns\":[{\"styleClass\":\"col-md-12\"}]}]}"

        final List<DashboardModuleDto> dashboardModuleDtos = responseEntity.getBody().getDashboardModuleDtos()
        dashboardModuleDtos != null
        dashboardModuleDtos.size() == 3

        //FIXME - add checking of module type once confirm is needed in the model
        final DashboardModuleDto dashboardModuleDto0 = dashboardModuleDtos.get(0)
        dashboardModuleDto0.getId() == 1L
        dashboardModuleDto0.getTitle() == "Sales ratio FYTD"
        dashboardModuleDto0.getDescription() == "This is Sales ratio FYTD"
        dashboardModuleDto0.getOrderIndex() == 0
        dashboardModuleDto0.getColumnIndex() == 0
        dashboardModuleDto0.getRowIndex() == 0
        dashboardModuleDto0.getType() == "multi-visualisation"
        dashboardModuleDto0.getFrontendId() == null

        final DashboardModuleDto dashboardModuleDto1 = dashboardModuleDtos.get(1)
        dashboardModuleDto1.getId() == 2L
        dashboardModuleDto1.getTitle() == "Global sales of goods during of the day"
        dashboardModuleDto1.getDescription() == "This is Global sales of goods during of the day"
        dashboardModuleDto1.getOrderIndex() == 0
        dashboardModuleDto1.getColumnIndex() == 0
        dashboardModuleDto1.getRowIndex() == 1
        dashboardModuleDto1.getType() == "multi-visualisation"
        dashboardModuleDto1.getFrontendId() == null

        final DashboardModuleDto dashboardModuleDto2 = dashboardModuleDtos.get(2)
        dashboardModuleDto2.getId() == 3L
        dashboardModuleDto2.getTitle() == "Sales value in Poland"
        dashboardModuleDto2.getDescription() == "This is Sales value in Poland"
        dashboardModuleDto2.getOrderIndex() == 0
        dashboardModuleDto2.getColumnIndex() == 1
        dashboardModuleDto2.getRowIndex() == 1
        dashboardModuleDto2.getType() == "multi-visualisation"
        dashboardModuleDto2.getFrontendId() == null
    }

    void "should create dashboard page"() {

        given:
        final LayoutDto layoutDto = new LayoutDto()
                .setId(1L)
        final PageDto pageDto = new PageDto()
                .setDescription("Test description")
                .setIcon("Test icon")
                .setTitle("Test title")
                .setLayout(layoutDto)
        final DashboardForm dashboardForm = new DashboardForm(pageDto)

        when:
        final ResponseEntity<String> responseEntity = template
                .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
                .postForEntity("http://localhost:${port}/api/page-configurations/", dashboardForm, String.class)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        final ObjectMapper mapper = new ObjectMapper();
        final FormValidationResultDto<DashboardForm> formValidationResultDto = mapper.readValue(responseEntity.getBody(),
                new TypeReference<FormValidationResultDto<DashboardForm>>() {
                });

        formValidationResultDto.getFormErrors().size() == 0
        formValidationResultDto.getForm() != null

        final PageDto createdPageDto = formValidationResultDto.getForm().getPage()
        createdPageDto.getId() == 8L
        createdPageDto.getDescription() == pageDto.getDescription()
        createdPageDto.getTitle() == pageDto.getTitle()
        createdPageDto.getIcon() == pageDto.getIcon()

        final LayoutDto createdLayoutDto = createdPageDto.getLayout()
        createdLayoutDto.getId() == 1L
        createdLayoutDto.getTitle() == "1 (100)"
        createdLayoutDto.getIconFileName() == "layout-icon-1.png"
        createdLayoutDto.getContent() == "<div class=\"row\"><div class=\"column col-md-12\"></div></div>"
        createdLayoutDto.getContentAsJson() == "{\"rows\":[{\"columns\":[{\"styleClass\":\"col-md-12\"}]}]}"
    }

}