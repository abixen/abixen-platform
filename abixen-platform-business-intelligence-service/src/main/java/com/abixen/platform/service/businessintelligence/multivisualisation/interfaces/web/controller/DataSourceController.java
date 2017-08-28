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

package com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.web.controller;


import com.abixen.platform.common.dto.FormErrorDto;
import com.abixen.platform.common.dto.FormValidationResultDto;
import com.abixen.platform.common.exception.PlatformRuntimeException;
import com.abixen.platform.common.util.ValidationUtil;
import com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.web.facade.DataSourceFacade;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DataSourceForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping(value = "/api/service/abixen/business-intelligence/control-panel/multi-visualisation/data-sources")
public class DataSourceController {

    private final DataSourceFacade dataSourceFacade;

    @Autowired
    public DataSourceController(DataSourceFacade dataSourceFacade) {
        this.dataSourceFacade = dataSourceFacade;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<DataSourceDto> findAllDataSources(@RequestParam(value = "dataSourceType", required = false) DataSourceType dataSourceType, @PageableDefault(size = 1, page = 0) Pageable pageable) {
        return dataSourceFacade.findAllDataSources(pageable, dataSourceType);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DataSourceDto findDataSource(@PathVariable Long id) {
        return dataSourceFacade.findDataSource(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createDataSource(@RequestBody @Valid DataSourceForm dataSourceForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(dataSourceForm, formErrors);
        }
        dataSourceFacade.createDataSource(dataSourceForm);

        return new FormValidationResultDto(dataSourceForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateDataSource(@PathVariable("id") Long id, @RequestBody @Valid DataSourceForm dataSourceForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(dataSourceForm, formErrors);
        }
        try {
            dataSourceFacade.updateDataSource(dataSourceForm);
        } catch (Throwable e) {
            log.error(e.getMessage());
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new PlatformRuntimeException("Data source can not be updated. If you want to change available columns then you need to detach they from module firstly.");
            } else {
                throw e;
            }
        }
        return new FormValidationResultDto(dataSourceForm);
    }

    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    public  List<Map<String, DataValueDto>> getPreviewData(@RequestBody @Valid DataSourceForm dataSourceForm) {
        return dataSourceFacade.getPreviewData(dataSourceForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteDataSource(@PathVariable("id") long id) {
        log.debug("delete() - id: " + id);
        dataSourceFacade.deleteDataSource(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
}
