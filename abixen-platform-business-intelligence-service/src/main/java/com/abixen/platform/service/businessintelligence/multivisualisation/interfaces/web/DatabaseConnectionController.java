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
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DatabaseConnectionDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.DatabaseConnectionForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DatabaseConnectionManagementService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/api/service/abixen/business-intelligence/control-panel/multi-visualisation/database-connections")
public class DatabaseConnectionController {

    private final DatabaseConnectionManagementService databaseConnectionManagementService;

    @Autowired
    public DatabaseConnectionController(DatabaseConnectionManagementService databaseConnectionManagementService) {
        this.databaseConnectionManagementService = databaseConnectionManagementService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<DatabaseConnectionDto> findAllDatabaseConnections(@PageableDefault(size = 1, page = 0) Pageable pageable) {
        log.debug("findAllDatabaseConnections() - pageable: {}", pageable);

        return databaseConnectionManagementService.findAllDatabaseConnections(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DatabaseConnectionDto findDatabaseConnection(@PathVariable Long id) {
        log.debug("findDatabaseConnection() - id: {}", id);

        return databaseConnectionManagementService.findDatabaseConnection(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto<DatabaseConnectionForm> createDatabaseConnection(@RequestBody @Valid DatabaseConnectionForm databaseConnectionForm, BindingResult bindingResult) {
        log.debug("createDatabaseConnection() - databaseConnectionForm: {}", databaseConnectionForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(databaseConnectionForm, formErrors);
        }

        final DatabaseConnectionForm createdDatabaseConnectionForm = databaseConnectionManagementService.createDatabaseConnection(databaseConnectionForm);

        return new FormValidationResultDto<>(createdDatabaseConnectionForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto<DatabaseConnectionForm> updateDatabaseConnection(@PathVariable("id") Long id, @RequestBody @Valid DatabaseConnectionForm databaseConnectionForm, BindingResult bindingResult) {
        log.debug("updateDatabaseConnection() - id: {}, databaseConnectionForm: {}", id, databaseConnectionForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(databaseConnectionForm, formErrors);
        }

        final DatabaseConnectionForm updatedDatabaseConnectionForm = databaseConnectionManagementService.updateDatabaseConnection(databaseConnectionForm);

        return new FormValidationResultDto<>(updatedDatabaseConnectionForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteDatabaseConnection(@PathVariable("id") long id) {
        log.debug("deleteDatabaseConnection() - id: {}", id);

        databaseConnectionManagementService.deleteDatabaseConnection(id);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public FormValidationResultDto<DatabaseConnectionForm> testDatabaseConnection(@RequestBody @Valid DatabaseConnectionForm databaseConnectionForm, BindingResult bindingResult) {
        log.debug("testDatabaseConnection() - databaseConnectionForm: {}", databaseConnectionForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(databaseConnectionForm, formErrors);
        }

        databaseConnectionManagementService.testDatabaseConnection(databaseConnectionForm);

        return new FormValidationResultDto<>(databaseConnectionForm);
    }

    @RequestMapping(value = "/{id}/tables", method = RequestMethod.GET)
    public List<String> findTables(@PathVariable("id") Long id) {
        log.debug("findTables() - id: {}", id);

        return databaseConnectionManagementService.findTablesInDatabase(id);
    }

    @RequestMapping(value = "/{id}/tables/{tableName}/columns", method = RequestMethod.GET)
    public List<DataSourceColumnDto> findTableColumns(@PathVariable("id") Long id, @PathVariable("tableName") String tableName) {
        log.debug("findTableColumns() - id: {}, table: {}", id, tableName);

        return databaseConnectionManagementService.findTableColumnsFromDatabase(id, tableName);
    }

}