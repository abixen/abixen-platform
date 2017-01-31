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

package com.abixen.platform.service.businessintelligence.multivisualization.controller;

import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.util.ValidationUtil;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.service.businessintelligence.multivisualization.form.DatabaseDataSourceForm;
import com.abixen.platform.service.businessintelligence.multivisualization.model.impl.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualization.model.web.DataValueWeb;
import com.abixen.platform.service.businessintelligence.multivisualization.model.web.DatabaseDataSourceWeb;
import com.abixen.platform.service.businessintelligence.multivisualization.service.DatabaseDataSourceService;
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
import java.util.Map;


@Slf4j
@RestController
@RequestMapping(value = "/admin/businessintelligence/abixen/multi-visualization/database-data-sources")
public class DatabaseDataSourceController {

    private final DatabaseDataSourceService databaseDataSourceService;

    @Autowired
    public DatabaseDataSourceController(DatabaseDataSourceService databaseDataSourceService) {
        this.databaseDataSourceService = databaseDataSourceService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<DatabaseDataSource> getDataSources(@PageableDefault(size = 1, page = 0) Pageable pageable) {
        log.debug("getDataSources()");

        Page<DatabaseDataSource> dataSources = databaseDataSourceService.findAllDataSources(pageable);

        return dataSources;
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DatabaseDataSourceWeb getDataSource(@PathVariable Long id) {
        log.debug("getDataSource() - id: " + id);

        return databaseDataSourceService.findDataSource(id);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createDataSource(@RequestBody @Valid DatabaseDataSourceForm databaseDataSourceForm, BindingResult bindingResult) {
        log.debug("createDataSource() - databaseDataSourceForm: " + databaseDataSourceForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(databaseDataSourceForm, formErrors);
        }

        DatabaseDataSourceForm databaseDataSourceFormResult = databaseDataSourceService.createDataSource(databaseDataSourceForm);

        return new FormValidationResultDto(databaseDataSourceFormResult);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateDataSource(@PathVariable("id") Long id, @RequestBody @Valid DatabaseDataSourceForm databaseDataSourceForm, BindingResult bindingResult) {
        log.debug("updateDataSource() - id: " + id + ", databaseDataSourceForm: " + databaseDataSourceForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(databaseDataSourceForm, formErrors);
        }

        DatabaseDataSourceForm databaseDataSourceFormResult = databaseDataSourceService.updateDataSource(databaseDataSourceForm);

        return new FormValidationResultDto(databaseDataSourceFormResult);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    public  List<Map<String, DataValueWeb>> getPreviewData(@RequestBody @Valid DatabaseDataSourceForm databaseDataSourceForm) {
        log.debug("createDataSource() - databaseDataSourceForm: " + databaseDataSourceForm);

        List<Map<String, DataValueWeb>> databaseDataSourcePreviewData = databaseDataSourceService.getPreviewData(databaseDataSourceForm);

        return databaseDataSourcePreviewData;
    }

}
