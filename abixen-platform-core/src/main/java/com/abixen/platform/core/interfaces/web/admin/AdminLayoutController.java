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
import com.abixen.platform.core.application.dto.LayoutDto;
import com.abixen.platform.core.application.form.LayoutForm;
import com.abixen.platform.core.application.form.LayoutSearchForm;
import com.abixen.platform.core.application.service.LayoutManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/api/control-panel/layouts")
public class AdminLayoutController {

    private final LayoutManagementService layoutManagementService;


    @Autowired
    public AdminLayoutController(LayoutManagementService layoutManagementService) {
        this.layoutManagementService = layoutManagementService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<LayoutDto> findAll(@PageableDefault(size = 1) Pageable pageable, LayoutSearchForm layoutSearchForm) {
        log.debug("findAll() - pageable: {}, layoutSearchForm: {}", pageable, layoutSearchForm);

        return layoutManagementService.findAllLayouts(pageable, layoutSearchForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public LayoutDto find(@PathVariable Long id) {
        log.debug("find() - id: {}", id);

        return layoutManagementService.findLayout(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto<LayoutForm> create(@RequestBody @Valid LayoutForm layoutForm, BindingResult bindingResult) {
        log.debug("create() - layoutForm: {}", layoutForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(layoutForm, formErrors);
        }

        final LayoutForm createdLayoutForm = layoutManagementService.createLayout(layoutForm);

        return new FormValidationResultDto<>(createdLayoutForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    FormValidationResultDto<LayoutForm> update(@PathVariable Long id, @RequestBody @Valid LayoutForm layoutForm, BindingResult bindingResult) {
        log.debug("update() - id: {}, layoutForm: {}", id, layoutForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(layoutForm, formErrors);
        }

        final LayoutForm updatedLayoutForm = layoutManagementService.updateLayout(layoutForm);

        return new FormValidationResultDto<>(updatedLayoutForm);
    }

    @RequestMapping(value = "/{id}/icon", method = RequestMethod.POST)
    public LayoutDto updateIcon(@PathVariable Long id, @RequestParam("iconFile") MultipartFile iconFile) throws IOException {
        log.debug("updateIcon() - id: {}", id);

        return layoutManagementService.changeLayoutIcon(id, iconFile);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
        log.debug("delete() - id: {}", id);

        layoutManagementService.deleteLayout(id);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

}