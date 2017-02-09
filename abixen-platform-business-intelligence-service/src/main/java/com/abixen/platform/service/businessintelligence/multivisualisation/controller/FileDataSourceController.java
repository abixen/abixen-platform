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

package com.abixen.platform.service.businessintelligence.multivisualisation.controller;

import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.util.ValidationUtil;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.service.businessintelligence.multivisualisation.form.FileDataSourceForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.file.FileDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.FileDataSourceWeb;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.FileDataSourceService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/service/abixen/business-intelligence/admin/multi-visualisation/file-data-sources")
public class FileDataSourceController {

    private final FileDataSourceService fileDataSourceService;

    @Autowired
    public FileDataSourceController(FileDataSourceService fileDataSourceService) {
        this.fileDataSourceService = fileDataSourceService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<FileDataSource> getDataSources(@PageableDefault(size = 1, page = 0) Pageable pageable) {
        log.debug("getDataSources()");

        Page<FileDataSource> dataSources = fileDataSourceService.findAllDataSources(pageable);

        return dataSources;
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public FileDataSourceWeb getDataSource(@PathVariable Long id) {
        log.debug("getDataSource() - id: " + id);

        return fileDataSourceService.findDataSource(id);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createDataSource(@RequestBody @Valid FileDataSourceForm fileDataSourceForm, BindingResult bindingResult) {
        log.debug("createDataSource() - fileDataSourceForm: " + fileDataSourceForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(fileDataSourceForm, formErrors);
        }

        FileDataSourceForm fileDataSourceFormResult = fileDataSourceService.createDataSource(fileDataSourceForm);

        return new FormValidationResultDto(fileDataSourceFormResult);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateDataSource(@PathVariable("id") Long id, @RequestBody @Valid FileDataSourceForm fileDataSourceForm, BindingResult bindingResult) {
        log.debug("updateDataSource() - id: " + id + ", fileDataSourceForm: " + fileDataSourceForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(fileDataSourceForm, formErrors);
        }

        FileDataSourceForm fileDataSourceFormResult = fileDataSourceService.updateDataSource(fileDataSourceForm);

        return new FormValidationResultDto(fileDataSourceFormResult);
    }



}
