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

package com.abixen.platform.service.webcontent.domain.service

import com.abixen.platform.service.webcontent.domain.model.Template
import com.abixen.platform.service.webcontent.domain.repository.TemplateRepository
import spock.lang.Specification


class TemplateServiceTest extends Specification {

    private TemplateService templateService;
    private TemplateRepository templateRepository

    void setup() {
        templateRepository = Mock()
        templateService = new TemplateService(templateRepository)
    }

    void "should return template's variables"() {
        given:
        final String templateContent = 'Hello ${someName1}! \n' +
                'This is something else: ${someName2}\n' +
                'And this is rich editor\'s text: ${someName3}\n' +
                'Born date: ${someName4}';
        final Long templateId = 1L

        final Template template = new Template()
        template.setId(templateId)
        template.setName('someName')
        template.setContent(templateContent);

        when:
        List<String> variables = templateService.getTemplateVariables(templateId)

        then:
        variables.size() == 4
        variables.contains('someName1')
        variables.contains('someName2')
        variables.contains('someName3')
        variables.contains('someName4')

        1 * templateRepository.findOne(templateId) >> template
        0 * _
    }
}