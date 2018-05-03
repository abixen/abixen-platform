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

package com.abixen.platform.service.webcontent.interfaces.web;

import com.abixen.platform.common.application.dto.FormErrorDto;
import com.abixen.platform.common.application.dto.FormValidationResultDto;
import com.abixen.platform.common.infrastructure.util.ValidationUtil;
import com.abixen.platform.service.webcontent.application.dto.WebContentDto;
import com.abixen.platform.service.webcontent.application.form.WebContentForm;
import com.abixen.platform.service.webcontent.application.service.WebContentManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/service/abixen/web-content/control-panel/web-contents")
public class ControlPanelWebContentController extends AbstractWebContentController {

    private final WebContentManagementService webContentManagementService;

    @Autowired
    public ControlPanelWebContentController(WebContentManagementService webContentManagementService) {
        super(webContentManagementService);
        this.webContentManagementService = webContentManagementService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public WebContentDto findWebContent(@PathVariable Long id) {
        log.debug("getWebContent() - id: {}", id);

        return webContentManagementService.findWebContent(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto<WebContentForm> createWebContent(@RequestBody @Valid WebContentForm simpleWebContentForm, BindingResult bindingResult) {
        log.debug("createSimpleWebContentForm() - simpleWebContentForm: {}", simpleWebContentForm);
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(simpleWebContentForm, formErrors);
        }

        webContentManagementService.createWebContent(simpleWebContentForm);

        return new FormValidationResultDto<>(simpleWebContentForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto<WebContentForm> updateWebContent(@PathVariable("id") Long id, @RequestBody @Valid WebContentForm simpleWebContentForm, BindingResult bindingResult) {
        log.debug("updateSimpleWebContent() - id: {}, simpleWebContentForm: {}", id, simpleWebContentForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(simpleWebContentForm, formErrors);
        }

        webContentManagementService.updateWebContent(simpleWebContentForm);

        return new FormValidationResultDto<>(simpleWebContentForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteWebContent(@PathVariable("id") Long id) {
        webContentManagementService.deleteWebContent(id);
    }
}