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

package com.abixen.platform.core.interfaces.web.admin;

import com.abixen.platform.common.application.dto.FormErrorDto;
import com.abixen.platform.common.application.dto.FormValidationResultDto;
import com.abixen.platform.common.infrastructure.util.ValidationUtil;
import com.abixen.platform.core.application.dto.PageDto;
import com.abixen.platform.core.application.form.PageForm;
import com.abixen.platform.core.application.form.PageSearchForm;
import com.abixen.platform.core.application.service.PageManagementService;
import com.abixen.platform.core.interfaces.web.common.AbstractPageController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping(value = "/api/control-panel/pages")
public class AdminPageController extends AbstractPageController {

    private final PageManagementService pageManagementService;

    @Autowired
    public AdminPageController(PageManagementService pageManagementService) {
        super(pageManagementService);
        this.pageManagementService = pageManagementService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PageDto find(@PathVariable Long id) {
        log.debug("find() - id: {}", id);

        return pageManagementService.findPage(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public org.springframework.data.domain.Page<PageDto> findAll(@PageableDefault Pageable pageable, PageSearchForm pageSearchForm) {
        log.debug("findAll()");

        return pageManagementService.findAllPages(pageable, pageSearchForm);
    }

    @PreAuthorize("hasPermission(null, 'com.abixen.platform.core.domain.model.impl.Page', 'PAGE_ADD')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto<PageForm> create(@RequestBody @Valid PageForm pageForm, BindingResult bindingResult) {
        log.debug("create() - pageForm: {}", pageForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(pageForm, formErrors);
        }

        final PageForm pageFormResult = pageManagementService.createPage(pageForm);

        return new FormValidationResultDto<>(pageFormResult);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto<PageForm> update(@PathVariable("id") Long id, @RequestBody @Valid PageForm pageForm, BindingResult bindingResult) {
        log.debug("update() - id: {}, pageForm: {}", id, pageForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(pageForm, formErrors);
        }

        final PageForm pageFormResult = pageManagementService.updatePage(pageForm);

        return new FormValidationResultDto<>(pageFormResult);
    }

}