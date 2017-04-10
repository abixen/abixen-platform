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

import com.abixen.platform.common.dto.FormErrorDto;
import com.abixen.platform.common.dto.FormValidationResultDto;
import com.abixen.platform.common.util.ValidationUtil;
import com.abixen.platform.common.util.WebModelJsonSerialize;
import com.abixen.platform.service.businessintelligence.multivisualisation.form.DataFileForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFile;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFileColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataFileWeb;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataSourceColumnWeb;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DataFileService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/api/service/abixen/business-intelligence/control-panel/multi-visualisation/file-data")
public class DataFileController {

    public static final int DEFAULT_PAGE_SIZE = 20;
    private final DataFileService dataFileService;

    @Autowired
    public DataFileController(DataFileService dataFileService) {
        this.dataFileService = dataFileService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<DataFile> getDataFile(@PageableDefault(size = DEFAULT_PAGE_SIZE, page = 0) Pageable pageable) {
        log.debug("getDatabaseDataSources()");

        Page<DataFile> dataSources = dataFileService.findAllDataFile(pageable);

        return dataSources;
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DataFileWeb getDataFile(@PathVariable Long id) {
        log.debug("getDataSource() - id: " + id);

        return dataFileService.findDataFile(id);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createDataFile(@RequestBody @Valid DataFileForm fileDataForm, BindingResult bindingResult) {
        log.debug("createDataSource() - fileDataSourceForm: " + fileDataForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(fileDataForm, formErrors);
        }

        DataFileForm fileDataFormResult = dataFileService.createDataFile(fileDataForm);

        return new FormValidationResultDto(fileDataFormResult);
    }

    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateDataFile(@PathVariable("id") Long id, @RequestBody @Valid DataFileForm dataFileForm, BindingResult bindingResult) {
        log.debug("updateDataSource() - id: " + id + ", fileDataSourceForm: " + dataFileForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(dataFileForm, formErrors);
        }

        DataFileForm fileDataSourceFormResult = dataFileService.updateDataFile(dataFileForm);

        return new FormValidationResultDto(fileDataSourceFormResult);
    }

    @RequestMapping(value = "/{id}/columns", method = RequestMethod.GET)
    public List<DataSourceColumnWeb> getTableColumns(@PathVariable("id") Long id) {
        log.debug("getTableColumns()");
        return dataFileService.getDataFileColumns(id);
    }

    @RequestMapping(value = "/parse/{readFirstColumnAsColumnName}", method = RequestMethod.POST)
    public FileParserMessage<DataFileColumn> uploadAndParseFile(@PathVariable("readFirstColumnAsColumnName") Boolean readFirstColumnAsColumnName, @RequestParam("file") MultipartFile uploadedFile) {
        return dataFileService.uploadAndParseFile(uploadedFile, readFirstColumnAsColumnName);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteFileData(@PathVariable("id") long id) {
        log.debug("delete() - id: " + id);
        dataFileService.delateFileData(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

}
