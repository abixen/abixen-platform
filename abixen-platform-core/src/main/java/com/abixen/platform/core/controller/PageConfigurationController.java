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

package com.abixen.platform.core.controller;

import com.abixen.platform.common.dto.FormErrorDto;
import com.abixen.platform.common.dto.FormValidationResultDto;
import com.abixen.platform.core.dto.PageModelDto;
import com.abixen.platform.core.form.PageConfigurationForm;
import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.service.PageConfigurationService;
import com.abixen.platform.common.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/api/page-configurations")
public class PageConfigurationController {

    private final PageConfigurationService pageConfigurationService;

    @Autowired
    public PageConfigurationController(PageConfigurationService pageConfigurationService) {
        this.pageConfigurationService = pageConfigurationService;
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_VIEW + "')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PageModelDto getPageConfiguration(@PathVariable Long id) {
        return pageConfigurationService.getPageConfiguration(id);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_ADD + "')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createPage(@RequestBody @Valid PageConfigurationForm pageConfigurationForm, BindingResult bindingResult) {
        log.debug("updatePageModel() - pageConfigurationForm: {}", pageConfigurationForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(pageConfigurationForm, formErrors);
        }

        PageConfigurationForm updatedPageConfigurationForm = pageConfigurationService.createPageConfiguration(pageConfigurationForm);

        return new FormValidationResultDto(updatedPageConfigurationForm);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_EDIT + "')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updatePage(@PathVariable("id") Long id, @RequestBody @Valid PageConfigurationForm pageConfigurationForm, BindingResult bindingResult) {
        log.debug("updatePageModel() - id: {}, pageConfigurationForm: {}", id, pageConfigurationForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(pageConfigurationForm, formErrors);
        }

        PageConfigurationForm updatedPageConfigurationForm = pageConfigurationService.updatePageConfiguration(pageConfigurationForm);

        return new FormValidationResultDto(updatedPageConfigurationForm);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.PAGE + "', '" + PermissionName.Values.PAGE_CONFIGURATION + "')")
    @RequestMapping(value = "/{id}/configure", method = RequestMethod.PUT)
    public FormValidationResultDto configurePage(@PathVariable("id") Long id, @RequestBody @Valid PageConfigurationForm pageConfigurationForm, BindingResult bindingResult) {
        log.debug("configurePage() - id: {}, pageConfigurationForm: {}", id, pageConfigurationForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(pageConfigurationForm, formErrors);
        }

        PageConfigurationForm updatedPageConfigurationForm = pageConfigurationService.configurePageConfiguration(pageConfigurationForm);

        return new FormValidationResultDto(updatedPageConfigurationForm);
    }
}