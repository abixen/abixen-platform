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
import com.abixen.platform.core.application.dto.RoleDto;
import com.abixen.platform.core.application.form.RoleForm;
import com.abixen.platform.core.application.form.RolePermissionsForm;
import com.abixen.platform.core.application.form.RoleSearchForm;
import com.abixen.platform.core.application.service.RoleManagementService;
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
@RequestMapping(value = "/api/control-panel/roles")
public class RoleController {

    private final RoleManagementService roleManagementService;

    @Autowired
    public RoleController(RoleManagementService roleManagementService) {
        this.roleManagementService = roleManagementService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<RoleDto> findAll(@PageableDefault(size = 1, page = 0) Pageable pageable, RoleSearchForm roleSearchForm) {
        log.debug("findAll() - roleSearchForm: {}", roleSearchForm);

        return roleManagementService.findAllRoles(pageable, roleSearchForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RoleDto find(@PathVariable Long id) {
        log.debug("find() - id: {}", id);

        return roleManagementService.findRole(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto<RoleForm> create(@RequestBody @Valid RoleForm roleForm, BindingResult bindingResult) {
        log.debug("create() - roleForm: {}", roleForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(roleForm, formErrors);
        }

        final RoleForm createdRoleForm = roleManagementService.createRole(roleForm);

        return new FormValidationResultDto<>(createdRoleForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto<RoleForm> update(@PathVariable("id") Long id, @RequestBody @Valid RoleForm roleForm, BindingResult bindingResult) {
        log.debug("update() - id: {}, roleForm: {}", id, roleForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(roleForm, formErrors);
        }

        final RoleForm updatedRoleForm = roleManagementService.updateRole(roleForm);

        return new FormValidationResultDto<>(updatedRoleForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
        log.debug("delete() - id: {}", id);

        roleManagementService.deleteRole(id);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/permissions", method = RequestMethod.GET)
    public RolePermissionsForm findPermissions(@PathVariable Long id) {
        log.debug("findPermissions() - id: {}", id);

        return roleManagementService.findRolePermissions(id);
    }

    @RequestMapping(value = "/{id}/permissions", method = RequestMethod.PUT)
    public FormValidationResultDto<RolePermissionsForm> updatePermissions(@PathVariable("id") Long id, @RequestBody @Valid RolePermissionsForm rolePermissionsForm, BindingResult bindingResult) {
        log.debug("updatePermissions() - id: {}, rolePermissionsForm: {}", id, rolePermissionsForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(rolePermissionsForm, formErrors);
        }

        final RolePermissionsForm updatedRolePermissionsForm = roleManagementService.updateRolePermissions(rolePermissionsForm);

        return new FormValidationResultDto<>(updatedRolePermissionsForm);
    }

}