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

package com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.web;

import com.abixen.platform.common.application.dto.FormErrorDto;
import com.abixen.platform.common.application.dto.FormValidationResultDto;
import com.abixen.platform.common.infrastructure.util.ValidationUtil;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataFileDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DataFileForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.message.FileParserMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DataFileManagementService;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/service/abixen/business-intelligence/control-panel/multi-visualisation/file-data")
public class DataFileController {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private final DataFileManagementService dataFileManagementService;

    @Autowired
    public DataFileController(DataFileManagementService dataFileManagementService) {
        this.dataFileManagementService = dataFileManagementService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<DataFileDto> findAllDataFile(@PageableDefault(size = DEFAULT_PAGE_SIZE, page = 0) Pageable pageable) {
        log.debug("findAllDataFile() - pageable: {}", pageable);

        final Page<DataFileDto> dataSources = dataFileManagementService.findAllDataFiles(pageable);

        return dataSources;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DataFileDto findDataFile(@PathVariable Long id) {
        log.debug("findDataFile() - id: {}", id);

        return dataFileManagementService.findDataFile(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto<DataFileForm> createDataFile(@RequestBody @Valid DataFileForm fileDataForm, BindingResult bindingResult) {
        log.debug("createDataFile() - fileDataForm: {}, bindingResult: {}", fileDataForm, bindingResult);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(fileDataForm, formErrors);
        }

        final DataFileForm createdFileDataForm = dataFileManagementService.createDataFile(fileDataForm);

        return new FormValidationResultDto<>(createdFileDataForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto<DataFileForm> updateDataFile(@PathVariable("id") Long id, @RequestBody @Valid DataFileForm dataFileForm, BindingResult bindingResult) {
        log.debug("updateDataFile() - id: {}, dataFileForm: {}, bindingResult: {}", id, dataFileForm, bindingResult);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(dataFileForm, formErrors);
        }

        final DataFileForm updatedFileDataForm = dataFileManagementService.updateDataFile(dataFileForm);

        return new FormValidationResultDto<>(updatedFileDataForm);
    }

    @RequestMapping(value = "/{id}/columns", method = RequestMethod.GET)
    public List<DataSourceColumnDto> findTableColumns(@PathVariable("id") Long id) {
        log.debug("findTableColumns() - id: {}", id);

        return dataFileManagementService.findAllDataFileColumns(id);
    }

    @RequestMapping(value = "/parse/{readFirstColumnAsColumnName}", method = RequestMethod.POST)
    public FileParserMessage<DataFileColumnDto> uploadAndParseFile(@PathVariable("readFirstColumnAsColumnName") Boolean readFirstColumnAsColumnName, @RequestParam("file") MultipartFile uploadedFile) {
        log.debug("uploadAndParseFile() - readFirstColumnAsColumnName: {}, uploadedFile: {}", readFirstColumnAsColumnName, uploadedFile);

        return dataFileManagementService.parse(uploadedFile, readFirstColumnAsColumnName);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteFileData(@PathVariable("id") long id) {
        log.debug("deleteFileData() - id: {}", id);

        dataFileManagementService.deleteDataFile(id);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

}