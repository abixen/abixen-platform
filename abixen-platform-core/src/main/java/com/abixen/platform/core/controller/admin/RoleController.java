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

package com.abixen.platform.core.controller.admin;

import com.abixen.platform.common.exception.PlatformRuntimeException;
import com.abixen.platform.core.converter.PermissionToPermissionDtoConverter;
import com.abixen.platform.core.converter.RoleToRoleDtoConverter;
import com.abixen.platform.common.dto.FormErrorDto;
import com.abixen.platform.common.dto.FormValidationResultDto;
import com.abixen.platform.core.dto.PermissionDto;
import com.abixen.platform.core.dto.RoleDto;
import com.abixen.platform.core.form.RoleForm;
import com.abixen.platform.core.form.RolePermissionsForm;
import com.abixen.platform.core.form.RoleSearchForm;
import com.abixen.platform.core.model.impl.Permission;
import com.abixen.platform.core.model.impl.Role;
import com.abixen.platform.core.service.PermissionService;
import com.abixen.platform.core.service.RoleService;
import com.abixen.platform.common.util.ValidationUtil;
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

@Slf4j
@RestController
@RequestMapping(value = "/api/control-panel/roles")
public class RoleController {

    private final RoleService roleService;
    private final PermissionService permissionService;
    private final RoleToRoleDtoConverter roleToRoleDtoConverter;
    private final PermissionToPermissionDtoConverter permissionToPermissionDtoConverter;

    @Autowired
    public RoleController(RoleService roleService,
                          PermissionService permissionService,
                          RoleToRoleDtoConverter roleToRoleDtoConverter,
                          PermissionToPermissionDtoConverter permissionToPermissionDtoConverter) {
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.roleToRoleDtoConverter = roleToRoleDtoConverter;
        this.permissionToPermissionDtoConverter = permissionToPermissionDtoConverter;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<RoleDto> getRoles(@PageableDefault(size = 1, page = 0) Pageable pageable, RoleSearchForm roleSearchForm) {
        log.debug("getRoles()");

        Page<Role> roles = roleService.findAllRoles(pageable, roleSearchForm);
        Page<RoleDto> roleDtos = roleToRoleDtoConverter.convertToPage(roles);

        return roleDtos;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RoleDto getRole(@PathVariable Long id) {
        log.debug("getRole() - id: " + id);

        Role role = roleService.findRole(id);
        RoleDto roleDto = roleToRoleDtoConverter.convert(role);

        return roleDto;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createRole(@RequestBody @Valid RoleForm roleForm, BindingResult bindingResult) {
        log.debug("save() - roleForm: " + roleForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(roleForm, formErrors);
        }

        Role role = roleService.buildRole(roleForm);
        roleService.createRole(role);

        return new FormValidationResultDto(roleForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateRole(@PathVariable("id") Long id, @RequestBody @Valid RoleForm roleForm, BindingResult bindingResult) {
        log.debug("update() - id: " + id + ", roleForm: " + roleForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(roleForm, formErrors);
        }

        return new FormValidationResultDto(roleService.updateRole(roleForm));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteRole(@PathVariable("id") long id) {
        log.debug("delete() - id: " + id);
        try {
            roleService.deleteRole(id);
        } catch (Throwable e) {
            e.printStackTrace();
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new PlatformRuntimeException("The role you want to remove is assigned to users.");
            } else {
                throw e;
            }
        }
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/permissions", method = RequestMethod.GET)
    public RolePermissionsForm getRolePermissions(@PathVariable Long id) {
        log.debug("getRolePermissionsForm() - id: " + id);

        Role role = roleService.findRole(id);
        List<Permission> allPermissions = permissionService.findAllPermissions();

        RoleDto roleDto = roleToRoleDtoConverter.convert(role);
        List<PermissionDto> allPermissionDtos = permissionToPermissionDtoConverter.convertToList(allPermissions);
        RolePermissionsForm rolePermissionsForm = new RolePermissionsForm(roleDto, allPermissionDtos);

        return rolePermissionsForm;
    }

    @RequestMapping(value = "/{id}/permissions", method = RequestMethod.PUT)
    public FormValidationResultDto updateRolePermissions(@PathVariable("id") Long id, @RequestBody @Valid RolePermissionsForm rolePermissionsForm, BindingResult bindingResult) {
        log.debug("updateRolePermissions() - id: " + id + ", rolePermissionsForm: " + rolePermissionsForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(rolePermissionsForm, formErrors);
        }

        Role role = roleService.buildRolePermissions(rolePermissionsForm);
        roleService.updateRole(role);

        return new FormValidationResultDto(rolePermissionsForm);
    }
}